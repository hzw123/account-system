package cn.mauth.account.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 *复式明细记账
 */
public class AccountBook implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 记账编号
     */
    private Long bookId;

    /**
     * 记账规则编号
     */
    private String ruleId;

    /**
     * 记账日期
     */
    private String accountDate;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 借记科目
     */
    private String drSubjectNo;

    /**
     * 借记客户编号
     */
    private String drCustId;

    /**
     * 借记客户账户编号
     */
    private String drAccountId;

    /**
     * 借记方变动额
     */
    private Long drAmount;

    /**
     * 借记方余额
     */
    private Long drCashAmount;

    /**
     * 借方账户更新序列号
     */
    private Long drAccountSeq;

    /**
     * 贷记科目
     */
    private String crSubjectNo;

    /**
     * 贷记客户编号
     */
    private String crCustId;

    /**
     * 贷记客户账户编号
     */
    private String crAccountId;

    /**
     * 贷记方变动额
     */
    private Long crAmount;

    /**
     * 贷记客户余额
     */
    private Long crCashAmount;

    /**
     * 贷方账户更新序列号
     */
    private Long crAccountSeq;

    /**
     * 记账金额
     */
    private Long amount;

    /**
     * 记账时间
     */
    private Date bookTime;

    /**
     * 记账状态
     */
    private String bookState;

    /**
     * 支付方借贷标识
     */
    private String payerFlag;

    /**
     * 获取记账编号
     *
     * @return book_id - 记账编号
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * 设置记账编号
     *
     * @param bookId 记账编号
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
     * 获取订单号
     *
     * @return order_id - 订单号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单号
     *
     * @param orderId 订单号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取借记科目
     *
     * @return dr_subject_no - 借记科目
     */
    public String getDrSubjectNo() {
        return drSubjectNo;
    }

    /**
     * 设置借记科目
     *
     * @param drSubjectNo 借记科目
     */
    public void setDrSubjectNo(String drSubjectNo) {
        this.drSubjectNo = drSubjectNo;
    }

    /**
     * 获取借记客户编号
     *
     * @return dr_cust_id - 借记客户编号
     */
    public String getDrCustId() {
        return drCustId;
    }

    /**
     * 设置借记客户编号
     *
     * @param drCustId 借记客户编号
     */
    public void setDrCustId(String drCustId) {
        this.drCustId = drCustId;
    }

    /**
     * 获取贷记科目
     *
     * @return cr_subject_no - 贷记科目
     */
    public String getCrSubjectNo() {
        return crSubjectNo;
    }

    /**
     * 设置贷记科目
     *
     * @param crSubjectNo 贷记科目
     */
    public void setCrSubjectNo(String crSubjectNo) {
        this.crSubjectNo = crSubjectNo;
    }

    /**
     * 获取贷记客户编号
     *
     * @return cr_cust_id - 贷记客户编号
     */
    public String getCrCustId() {
        return crCustId;
    }

    /**
     * 设置贷记客户编号
     *
     * @param crCustId 贷记客户编号
     */
    public void setCrCustId(String crCustId) {
        this.crCustId = crCustId;
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
     * 获取记账时间
     *
     * @return book_time - 记账时间
     */
    public Date getBookTime() {
        return bookTime;
    }

    /**
     * 设置记账时间
     *
     * @param bookTime 记账时间
     */
    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    /**
     * 获取记账状态
     *
     * @return book_state - 记账状态
     */
    public String getBookState() {
        return bookState;
    }

    /**
     * 设置记账状态
     *
     * @param bookState 记账状态
     */
    public void setBookState(String bookState) {
        this.bookState = bookState;
    }

    /**
     * 获取支付方借贷标识
     *
     * @return payer_flag - 支付方借贷标识
     */
    public String getPayerFlag() {
        return payerFlag;
    }

    /**
     * 设置支付方借贷标识
     *
     * @param payerFlag 支付方借贷标识
     */
    public void setPayerFlag(String payerFlag) {
        this.payerFlag = payerFlag;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Long getDrCashAmount() {
        return drCashAmount;
    }

    public void setDrCashAmount(Long drCashAmount) {
        this.drCashAmount = drCashAmount;
    }

    public Long getCrCashAmount() {
        return crCashAmount;
    }

    public void setCrCashAmount(Long crCashAmount) {
        this.crCashAmount = crCashAmount;
    }

    public Long getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(Long drAmount) {
        this.drAmount = drAmount;
    }

    public Long getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(Long crAmount) {
        this.crAmount = crAmount;
    }

    public String getDrAccountId() {
        return drAccountId;
    }

    public void setDrAccountId(String drAccountId) {
        this.drAccountId = drAccountId;
    }

    public String getCrAccountId() {
        return crAccountId;
    }

    public void setCrAccountId(String crAccountId) {
        this.crAccountId = crAccountId;
    }

    public Long getDrAccountSeq() {
        return drAccountSeq;
    }

    public void setDrAccountSeq(Long drAccountSeq) {
        this.drAccountSeq = drAccountSeq;
    }

    public Long getCrAccountSeq() {
        return crAccountSeq;
    }

    public void setCrAccountSeq(Long crAccountSeq) {
        this.crAccountSeq = crAccountSeq;
    }
}