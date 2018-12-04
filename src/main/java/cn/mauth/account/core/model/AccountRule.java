package cn.mauth.account.core.model;

import java.io.Serializable;
import java.util.Date;

public class AccountRule implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 规则编号
     */
    public String ruleId;

    /**
     * 相同交易多个规则的排序编号
     */
    public Integer ruleSeq;

    /**
     * 交易类型
     */
    public String transType;

    /**
     * 支付通道
     */
    public String payChannel;

    /**
     * 付款方科目
     */
    public String payerSubjectNo;

    /**
     * 付款方借贷标识
     */
    public String payerFlag;

    /**
     * 收款方科目
     */
    public String payeeSubjectNo;

    /**
     * 收款方借贷标识
     */
    public String payeeFlag;

    /**
     * 更新时间
     */
    public Date updateTime;

    /**
     * 备注
     */
    public String remark;

    /**
     * 获取规则编号
     *
     * @return rule_id - 规则编号
     */
    public String getRuleId() {
        return ruleId;
    }

    public Integer getRuleSeq() {
        return ruleSeq;
    }

    public void setRuleSeq(Integer ruleSeq) {
        this.ruleSeq = ruleSeq;
    }

    /**
     * 设置规则编号
     *
     * @param ruleId 规则编号
     */
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * 获取交易类型
     *
     * @return trans_type - 交易类型
     */
    public String getTransType() {
        return transType;
    }

    /**
     * 设置交易类型
     *
     * @param transType 交易类型
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * 获取支付通道
     *
     * @return pay_channel - 支付通道
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * 设置支付通道
     *
     * @param payChannel 支付通道
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * 获取付款方科目
     *
     * @return payer_subject_no - 付款方科目
     */
    public String getPayerSubjectNo() {
        return payerSubjectNo;
    }

    /**
     * 设置付款方科目
     *
     * @param payerSubjectNo 付款方科目
     */
    public void setPayerSubjectNo(String payerSubjectNo) {
        this.payerSubjectNo = payerSubjectNo;
    }

    /**
     * 获取付款方借贷标识
     *
     * @return payer_flag - 付款方借贷标识
     */
    public String getPayerFlag() {
        return payerFlag;
    }

    /**
     * 设置付款方借贷标识
     *
     * @param payerFlag 付款方借贷标识
     */
    public void setPayerFlag(String payerFlag) {
        this.payerFlag = payerFlag;
    }

    /**
     * 获取收款方科目
     *
     * @return payee_subject_no - 收款方科目
     */
    public String getPayeeSubjectNo() {
        return payeeSubjectNo;
    }

    /**
     * 设置收款方科目
     *
     * @param payeeSubjectNo 收款方科目
     */
    public void setPayeeSubjectNo(String payeeSubjectNo) {
        this.payeeSubjectNo = payeeSubjectNo;
    }

    /**
     * 获取收款方借贷标识
     *
     * @return payee_flag - 收款方借贷标识
     */
    public String getPayeeFlag() {
        return payeeFlag;
    }

    /**
     * 设置收款方借贷标识
     *
     * @param payeeFlag 收款方借贷标识
     */
    public void setPayeeFlag(String payeeFlag) {
        this.payeeFlag = payeeFlag;
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
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}