package com.music.demo.login.service.impl;

import com.music.demo.admin.mapper.AdminMapper;
import com.music.demo.admin.service.AdminService;
import com.music.demo.domain.entity.User;
import com.music.demo.login.service.ISendMailService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ISendServiceImpl implements ISendMailService{

//    private final JavaMailSender mailSender;
    private final AdminService adminService;
//
    @Value("${spring.mail.username}")
    private String from;
//
//
//    public String sendMail(String to, String subject,String emailCode,String uid) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(from);
//        message.setTo(to);
//        message.setSubject(subject);
//        Random random = new Random();
//        int code = random.nextInt(90000 + 10000);
//        message.setText(String.valueOf(code));
//        mailSender.send(message);
//        if(emailCode.equals(code)){
//            User user = adminService.findById(uid);
//            user.setEmailEnable(true);
//        }
//        return String.valueOf(code);
//    }
@Autowired
private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String CODE_PREFIX = "email:code:";
    private static final long CODE_EXPIRE = 5 * 60 * 1000; // 5分钟
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 生成验证码并存储到Redis
     */
    public String generateAndStoreCode(String uid, String email) {
        // 使用SecureRandom生成6位数字验证码
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(900000) + 100000; // 100000~999999

        // 存储到Redis（key格式：email:code:{uid}:{email}）
        String key = CODE_PREFIX + uid + ":" + email;
        redisTemplate.opsForValue().set(key, String.valueOf(code), CODE_EXPIRE, TimeUnit.MILLISECONDS);

        return String.valueOf(code);
    }

    /**
     * 发送验证邮件
     */
    @Override
    public void sendVerificationMail(String to, String subject, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText("您的验证码是：" + code + "，有效期5分钟。");
        mailSender.send(message);
    }

    /**
     * 验证验证码
     */
    @Override
    public boolean verifyCode(String uid, String email, String code) {
        String key = CODE_PREFIX + uid + ":" + email;
        String storedCode = redisTemplate.opsForValue().get(key);

        // 验证通过后删除Redis中的验证码（防止重复使用）
        if (code.equals(storedCode)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    @Override
    public void activateEmail(String uid) {
        User user = adminService.findById(uid);
        if (user != null && user.getEmailEnable() == 0) {
            user.setEmailEnable(1);
            adminMapper.updateById(user);
        }
    }
}