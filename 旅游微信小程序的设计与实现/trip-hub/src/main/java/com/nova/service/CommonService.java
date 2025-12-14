package com.nova.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    String uploadImage(MultipartFile file);
}
