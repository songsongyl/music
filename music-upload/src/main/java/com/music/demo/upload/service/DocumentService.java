package com.music.demo.upload.service;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class DocumentService {
    public String extractText(MultipartFile file) throws IOException {
        // 验证文件是否为空
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传的文件为空");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("无法获取文件名");
        }

        // 生成唯一文件名并保存到最终位置
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        File savedFile = new File("D:/data/test/" + uniqueFileName);

        // 确保保存目录存在
        File uploadDir = savedFile.getParentFile();
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 保存文件到最终位置（而非临时文件）
        file.transferTo(savedFile);


            // 根据文件扩展名选择解析方式
            if (fileName.endsWith(".docx")) {
                return extractDocxText(savedFile);
            } else if (fileName.endsWith(".doc")) {
                return extractDocText(savedFile);
            } else {
                throw new IllegalArgumentException("不支持的文件格式，仅支持 .docx 和 .doc");
            }

    }

    private String extractDocxText(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {

            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return cleanText(extractor.getText());
        }
    }

    private String extractDocText(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             HWPFDocument document = new HWPFDocument(fis)) {

            WordExtractor extractor = new WordExtractor(document);
            return cleanText(extractor.getText());
        }
    }

    private String cleanText(String text) {
        if (text == null) return "";

        // 移除多余的空行
        text = text.replaceAll("\\n{3,}", "\n\n");

        // 移除控制字符，但保留常见的可打印字符
        text = text.replaceAll("[\\p{C}&&[^\\r\\n\\t]]", "");
        // 将\n转换为系统换行符
            return text.replace("\\n", System.lineSeparator()).trim();
    }

}
