package cn.mauth.account.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 帐户
 */
public class Account implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 账户编号
     */
    public String accountId;

    /**
     * 账户名
     */
    public String accountName;

    /**
     * 账户更新序列号
     */
    public Long accountSeq;

    /**
     * 科目编号
     */
    public String subjectNo;

    /**
     * 科目类型
     */
    public String subjectType;

    /**
     * 账户状态
     */
    public String accountState;

    /**
     * 账户余额
     */
    public Long cashAmount;

    /**
     * 创建时间
     */
    public Date createTime;

    /**
     * 更新时间
     */
    public Date updateTime;

    /**
     * 借贷标识
     */
    public String drcrFlag;

    /**
     * 借记发生额
     */
    public Long drAmount;

    /**
     * 贷记发生额
     */
    public Long crAmount;

    /**
     * 签名
     */
    public String sign;

    public String custId;

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
     * 获取账户名
     *
     * @return account_name - 账户名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账户名
     *
     * @param accountName 账户名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
     * 获取科目编号
     *
     * @return subject_no - 科目编号
     */
    public String getSubjectNo() {
        return subjectNo;
    }

    /**
     * 设置科目编号
     *
     * @param subjectNo 科目编号
     */
    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo;
    }

    /**
     * 获取科目类型
     *
     * @return subject_type - 科目类型
     */
    public String getSubjectType() {
        return subjectType;
    }

    /**
     * 设置科目类型
     *
     * @param subjectType 科目类型
     */
    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    /**
     * 获取账户状态
     *
     * @return account_state - 账户状态
     */
    public String getAccountState() {
        return accountState;
    }

    /**
     * 设置账户状态
     *
     * @param accountState 账户状态
     */
    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    /**
     * 获取账户余额
     *
     * @return cash_amount - 账户余额
     */
    public Long getCashAmount() {
        return cashAmount;
    }

    /**
     * 设置账户余额
     *
     * @param cashAmount 账户余额
     */
    public void setCashAmount(Long cashAmount) {
        this.cashAmount = cashAmount;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * 获取借记发生额
     *
     * @return dr_amount - 借记发生额
     */
    public Long getDrAmount() {
        return drAmount;
    }

    /**
     * 设置借记发生额
     *
     * @param drAmount 借记发生额
     */
    public void setDrAmount(Long drAmount) {
        this.drAmount = drAmount;
    }

    /**
     * 获取贷记发生额
     *
     * @return cr_amount - 贷记发生额
     */
    public Long getCrAmount() {
        return crAmount;
    }

    /**
     * 设置贷记发生额
     *
     * @param crAmount 贷记发生额
     */
    public void setCrAmount(Long crAmount) {
        this.crAmount = crAmount;
    }

    /**
     * 获取签名
     *
     * @return sign - 签名
     */
    public String getSign() {
        return sign;
    }

    /**
     * 设置签名
     *
     * @param sign 签名
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * @return cust_id
     */
    public String getCustId() {
        return custId;
    }

    /**
     * @param custId
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }
}