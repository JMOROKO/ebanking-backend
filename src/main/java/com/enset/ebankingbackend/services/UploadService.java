package com.enset.ebankingbackend.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadService {
    Map<String, String> uploadFile(MultipartFile file, String direName);
}
