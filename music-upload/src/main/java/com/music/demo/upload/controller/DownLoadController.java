package com.music.demo.upload.controller;

import com.music.demo.common.result.HttpResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.io.File;
import java.io.IOException;
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

    @Operation(summary = "文件下载")
    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        log.info("开始处理文件下载请求: {}", fileName);

        try {
            // 验证文件名是否合法
            if (fileName == null || fileName.contains("..")) {
                log.error("非法文件名: {}", fileName);
                return ResponseEntity.badRequest().build();
            }

            // 构建完整文件路径
            Path basePath = Paths.get(path).toAbsolutePath().normalize();
            Path filePath = basePath.resolve(fileName).normalize();

            // 检查路径是否在允许的目录内
            if (!filePath.startsWith(basePath)) {
                log.error("尝试访问目录外的文件: {}", filePath);
                return ResponseEntity.badRequest().build();
            }

            log.info("尝试访问文件路径: {}", filePath);

            File songFile = filePath.toFile();

            // 检查文件存在性和可读性
            if (!songFile.exists()) {
                log.error("文件不存在: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            if (!songFile.isFile()) {
                log.error("不是有效文件: {}", filePath);
                return ResponseEntity.badRequest().build();
            }

            if (!songFile.canRead()) {
                log.error("文件不可读: {}", filePath);
                return ResponseEntity.status(403).build();
            }

            // 确定内容类型
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // 获取文件大小
            long fileSize = songFile.length();
            log.info("文件大小: {} 字节", fileSize);

            // 编码文件名
            String encodedFilename = URLEncoder.encode(songFile.getName(), StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");

            // 创建文件资源
            Resource resource = new FileSystemResource(songFile);

            // 设置响应头
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .contentLength(fileSize)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + encodedFilename + "\"; filename*=UTF-8''" + encodedFilename)
                    .body(resource);

        } catch (IOException e) {
            log.error("文件下载失败: {}", fileName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}