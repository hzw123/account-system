package cn.mauth.account.dubbo.dto;

import java.io.Serializable;

/**
 * 复式记账对象
 * @author liuxin
 * @createtime 2017-11-06
 */
public class AccountBookDTO implements Serializable{

    private static final long serialVersionUID = 5265042131652861529L;

    /**
     * 交易订单号
     */
    private String orderId;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 支付通道
     */
    private String payChannel;

    /**
     * 交易金额
     */
    private Long amount;

    /**
     * 交易服务费
     */
    private Long servFee;

    /**
     * 支付方客户编号
     */
    private String payerCustId;

    /**
     * 收款方客户编号
     */
    private String payeeCustId;

    /**
     * 对账日期 yyyy-MM-dd
     */
    private String accountDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getServFee() {
        return servFee;
    }

    public void setServFee(Long servFee) {
        this.servFee = servFee;
    }

    public String getPayerCustId() {
        return payerCustId;
    }

    public void setPayerCustId(String payerCustId) {
        this.payerCustId = payerCustId;
    }

    public String getPayeeCustId() {
        return payeeCustId;
    }

    public void setPayeeCustId(String payeeCustId) {
        this.payeeCustId = payeeCustId;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }
}
