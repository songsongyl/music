package com.music.demo.login.service;

public interface ISendMailService {

//    String sendMail(String to, String subject,String emailCode,String uid);
  void  sendVerificationMail(String to,String subject, String code);
  boolean verifyCode(String uid, String email, String code);
  String generateAndStoreCode(String uid, String email);
  void activateEmail(String uid);
}
