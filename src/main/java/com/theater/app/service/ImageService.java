package com.theater.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long id, MultipartFile file);
}
