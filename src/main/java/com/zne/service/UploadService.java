package com.zne.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	void addPhoto(MultipartFile mFile);
}
