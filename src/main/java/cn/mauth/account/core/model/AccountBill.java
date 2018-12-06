package cn.mauth.account.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 帐单
 */
public class AccountBill implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 记账编号
     */
    private Long billId;

    /**
     * 记账日期
     */
    private String accountDate;

    /**
     * 账户更新序列号
     */
    private Long accountSeq;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 借贷标识
     */
    private String drcrFlag;

    /**
     * 记账金额
     */
    private Long amount;

    /**
     * 账户编号
     */
    private String accountId;

    /**
     * 账户余额
     */
    private Long accountAmount;

    /**
     * 记录时间
     */
    private Date recordTime;

    /**
     * 对账状态
     */
    private String reconStatus;

    /**
     * 借/贷方客户编号
     */
    private String custId;

    /**
     * 获取记账编号
     *
     * @return bill_id - 记账编号
     */
    public Long getBillId() {
        return billId;
    }

    /**
     * 设置记账编号
     *
     * @param billId 记账编号
     */
    public void setBillId(Long billId) {
        this.billId = billId;
    }

    /**
     * 获取记账日期
     *
     * @return account_date - 记账日期
     */
    public String getAccountDate() {
        return accountDate;
    }

    /**
     * 设置记账日期
     *
     * @param accountDate 记账日期
     */
    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    /**
     * 获取账户更新序列号
     *
     * @return account_seq - 账户更新序列号
     */
    public Long getAccountSeq() {
        return accountSeq;
    }

    /**
     * 设置账户更新序列号
     *
     * @param accountSeq 账户更新序列号
     */
    public void setAccountSeq(Long accountSeq) {
        this.accountSeq = accountSeq;
    }

    /**
     * 获取订单编号
     *
     * @return order_id - 订单编号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单编号
     *
     * @param orderId 订单编号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取借贷标识
     *
     * @return dr_cr_flag - 借贷标识
     */
    public String getDrCrFlag() {
        return drcrFlag;
    }

    /**
     * 设置借贷标识
     *
     * @param drCrFlag 借贷标识
     */
    public void setDrCrFlag(String drCrFlag) {
        this.drcrFlag = drCrFlag;
    }

    /**
     * 获取记账金额
     *
     * @return amount - 记账金额
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * 设置记账金额
     *
     * @param amount 记账金额
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * 获取账户编号
     *
     * @return account_id - 账户编号
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 设置账户编号
     *
     * @param accountId 账户编号
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取账户余额
     *
     * @return account_amount - 账户余额
     */
    public Long getAccountAmount() {
        return accountAmount;
    }

    /**
     * 设置账户余额
     *
     * @param accountAmount 账户余额
     */
    public void setAccountAmount(Long accountAmount) {
        this.accountAmount = accountAmount;
    }

    /**
     * 获取记录时间
     *
     * @return record_time - 记录时间
     */
    public Date getRecordTime() {
        return recordTime;
    }

    /**
     * 设置记录时间
     *
     * @param recordTime 记录时间
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * 获取对账状态
     *
     * @return recon_status - 对账状态
     */
    public String getReconStatus() {
        return reconStatus;
    }

    /**
     * 设置对账状态
     *
     * @param reconStatus 对账状态
     */
    public void setReconStatus(String reconStatus) {
        this.reconStatus = reconStatus;
    }

    /**
     * 获取借/贷方客户编号
     *
     * @return cust_id - 借/贷方客户编号
     */
    public String getCustId() {
        return custId;
    }

    /**
     * 设置借/贷方客户编号
     *
     * @param custId 借/贷方客户编号
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }
}