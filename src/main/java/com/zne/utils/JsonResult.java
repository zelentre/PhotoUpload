package com.zne.utils;

import java.io.Serializable;


public class JsonResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	
	private int state;
	/** 错误消息  */
	private String message;
	/** 返回正确时候的数据 */
	private Object data;

	public JsonResult() {
	}
	/**
	 * 失败时，返回提示信息
	 * @param error 提示内容 字符串形式
	 */
	public JsonResult(String error){
		state = ERROR;
		this.message = error;
	}
	/**
	 * 成功时返回
	 * @param data 返回数据内容
	 */
	public JsonResult(Object data){
		state = SUCCESS;
		this.data = data;
	}
	
	public JsonResult(int flag ,Object data){
		state = SUCCESS;
		this.data = data;
	}
	
	public JsonResult(Throwable e) {
		state = ERROR;
		message = e.getMessage();
	}
	
	public JsonResult(int state, Throwable e) {
		this.state = state;
		this.message = e.getMessage();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	
}
