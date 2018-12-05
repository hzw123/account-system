package cn.mauth.account.core.util;

import java.io.Serializable;

/**
 * common return
 * @author xuxueli 2015-12-4 16:32:31
 * @param <T>
 */
public class Result<T> implements Serializable {
	public static final long serialVersionUID = 42L;

	private static final int SUCCESS_CODE = 200;
	private static final int ERROR_CODE = 400;
	private static final int FAIL_CODE = 500;
	public static final Result<String> SUCCESS = new Result<>(null);
	public static final Result<String> FAIL = new Result<>(FAIL_CODE, null);
	
	private int code;
	private String msg;
	private T content;
	
	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public Result(T content) {
		this.code = SUCCESS_CODE;
		this.content = content;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}

	public static Result<String> success(){
		return SUCCESS;
	}

	public static Result<String> error(String msg){
		return new Result<>(ERROR_CODE,msg);
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", content=" + content + "]";
	}

}
