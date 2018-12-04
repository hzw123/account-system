package cn.mauth.account.dubbo.dto;

import java.io.Serializable;

/**
 * 基础dubbo响应对象
 * @author  liuxin
 * @createtime 2017-11-06
 */
public class BaseRespDTO implements Serializable{
    private static final long serialVersionUID = 5265042131652761519L;

    private boolean retCode;

    private String respDesc;

    public boolean isRetCode() {
        return retCode;
    }

    public void setRetCode(boolean retCode) {
        this.retCode = retCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }
}
