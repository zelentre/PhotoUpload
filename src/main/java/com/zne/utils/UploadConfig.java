package com.zne.utils;

public class UploadConfig {
	// -------------------------------
	// ----------腾讯云配置-----------
	// -------------------------------
	public static final String BUCKET_NAME;// 存储桶名称
	public static final String ACCESSKEY;
	public static final String SECRETKEY;
	public static final String REGION_NAME;// 存储所在地
	public static final int UPLOAD_THREAD_SIZE = 5;// 上传线程池的大小

	static {
		BUCKET_NAME = "ysch-1255642728";// 存储桶名称
		ACCESSKEY = "AKIDXzGMH1cE3iFd0ggNL8nbSCosrxxmQ064";
		SECRETKEY = "a2LTjyizNekfyLk2DDGGy0snabUgqwuh";
		REGION_NAME = "ap-shanghai";// 存储所在地

	}
}