package cn.mauth.account.core.util;

import java.io.Serializable;

public class Result<T> implements Serializable {
	public static final long serialVersionUID = 42L;

	private static final int SUCCESS_CODE = 200;
	private static final int CREATED_CODE = 201;
	private static final int UNAUTHORIZED_CODE = 401;
	private static final int FORBIDDEN_CODE = 403;
	private static final int NOT_FOUND_CODE = 404;
	private static final int ERROR_CODE = 400;

	public static final Result<String> SUCCESS = Result.of(null);
	public static final Result<String> CREATED = Result.of(CREATED_CODE,"created");
	public static final Result<String> UNAUTHORIZED = Result.of(UNAUTHORIZED_CODE,"Unauthorized");
	public static final Result<String> FORBIDDEN = Result.of(FORBIDDEN_CODE,"Forbidden");
	public static final Result<String> NOT_FOUND = Result.of(NOT_FOUND_CODE,"Not Found");
	public static final Result<String> FAIL = Result.error("error");
	
	private int code;
	private String msg;
	private T data;

	private Result(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

	private Result(T data) {
		this.code = SUCCESS_CODE;
		this.msg="success";
		this.data = data;
	}

	public static<T> Result<T> of(int code, String msg){
		return new Result(code,msg);
	}

	public static<T> Result<T> of(T data){
		return new Result(data);
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static Result<String> success(){
		return SUCCESS;
	}

	public static Result<?>  success(Object data){
		return Result.of(data);
	}

	public static Result<String> error(String msg){
		return new Result<>(ERROR_CODE,msg);
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
