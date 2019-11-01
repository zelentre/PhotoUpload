package com.zne.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zne.service.UploadService;
import com.zne.utils.JsonResult;

@RestController
@RequestMapping("/uploads")
public class UploadController {

	@Resource(name = "uploadService")
	private UploadService uploadService;

	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	public JsonResult addGoods(MultipartFile mFile) {
		uploadService.addPhoto(mFile);
		return new JsonResult("上传成功");
	}


}
