package com.zne.service.impl;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Transfer.TransferState;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import com.zne.service.UploadService;
import com.zne.utils.UploadConfig;

@Service
public class UploadServiceImpl implements UploadService {
	
	private static TransferManager manager;// 数据上传线程池
	private static COSClient cosClient;// 用于删除文件
	private ExecutorService delPool = Executors.newFixedThreadPool(20);// 用于删除已经上传文件的线程池

	static {
		ExecutorService threadPool = Executors.newFixedThreadPool(UploadConfig.UPLOAD_THREAD_SIZE);
		cosClient = new COSClient(new BasicCOSCredentials(UploadConfig.ACCESSKEY, UploadConfig.SECRETKEY),
				new ClientConfig(new Region(UploadConfig.REGION_NAME)));
		manager = new TransferManager(cosClient, threadPool, true);
	}


	public void addPhoto(MultipartFile mFile) {
		if (mFile == null)
			throw new RuntimeException("请选择上传文件");
		if (mFile.isEmpty())
			throw new RuntimeException("不允许上传空文件");
		File target = new File("/root/temp_File/" + System.currentTimeMillis()
				+ mFile.getOriginalFilename().substring(mFile.getOriginalFilename().lastIndexOf(".")));
		try {
			mFile.transferTo(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String key = "name1/name2/" + target.getName();//存放路径  name任意
		try {
			Upload ul = manager.upload(UploadConfig.BUCKET_NAME, key, target);
			delPool.execute(new DelRun(target, ul));
		} catch (Exception e) {
			throw new RuntimeException("上传文件" + target.getName() + "报错！！");
		}
	}

	/**
	 * 删除已经上传的文件所用的runnable类
	 * 
	 */
	private class DelRun implements Runnable {

		private File file;
		private Upload upload;

		public DelRun(File file, Upload upload) {
			this.file = file;
			this.upload = upload;
		}

		public void run() {
			try {
				while (true) {
					Thread.sleep(5000);
					if (upload.getState() == TransferState.Completed) {// 已经完成
						file.delete();
						break;
					} else if (upload.getState() == TransferState.Waiting
							|| upload.getState() == TransferState.InProgress) {// 正在等待或者正在工作
						continue;
					} else {// 上传文件失败
						throw new RuntimeException("上传文件失败！！文件名为：" + file.getName());
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
