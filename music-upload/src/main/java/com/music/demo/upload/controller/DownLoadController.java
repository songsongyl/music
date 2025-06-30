package com.music.demo.upload.controller;

import com.music.demo.common.exception.music.MusicException;
import com.music.demo.common.result.HttpResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Tag(name = "文件下载")
@RequestMapping("/api/download")
@RestController
@Slf4j
public class DownLoadController {

    @Value("${mypath}")
    private String path;

    @Operation(summary = "音频下载")
    @GetMapping("/downloadAudio")
    public void downloadAudio(HttpServletResponse response, String name) throws FileNotFoundException {
        if (name == null || name.contains("..")) {
            throw new MusicException("非法文件名！");
        }
        // 下载本地文件
        String fileName = "new" + name; // 保存的文件的默认保存名
        log.debug(response.toString());
        // 读到流中
        InputStream inStream = new FileInputStream(path + name);// 要保存的文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[4096];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            return HttpResult.success(fileName);
    }

    @Operation(summary = "歌词下载")
    @GetMapping("/downloadLyrics")
    public void downloadLyrics(HttpServletResponse response, String name) throws FileNotFoundException {
        if (name == null || name.contains("..")) {
            throw new MusicException("非法文件名！");
        }
        // 下载本地文件
        String fileName = "new" + name; // 保存的文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream(path + name);// 要保存的文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[4096];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Operation(summary = "图片下载")
    @GetMapping("/downloadImg")
    public void downloadImg(HttpServletResponse response, String name) throws FileNotFoundException {
// 1. 校验文件名（防止路径遍历攻击）
        if (name == null || name.contains("..") || name.contains("/") || name.contains("\\")) {
            throw new MusicException("非法文件名！");
        }

        // 2. 拼接文件路径
        String filePath = path + name;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new MusicException("文件不存在");
        }
        // 3. 设置响应头（强制下载 + 支持中文文件名）
        String downloadFileName = "new" + name;
        String encodedFileName = URLEncoder.encode(downloadFileName, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"); // 替换空格编码

        response.reset();
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE); // 通用二进制流
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
        response.setContentLengthLong(file.length()); // 设置文件大小（可选）

        // 4. 读取文件并写入响应流
        try (InputStream inStream = new FileInputStream(file);
             OutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[8192]; // 8KB 缓冲区
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }

    }
}
