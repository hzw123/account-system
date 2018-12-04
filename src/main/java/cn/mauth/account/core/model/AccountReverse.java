package cn.mauth.account.core.model;

import java.io.Serializable;
import java.util.Date;

public class AccountReverse implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 冲正明细编号
     */
    private Long reverseId;

    /**
     * 账户更新序列号
     */
    private Long accountSeq;

    /**
     * 借记科目
     */
    private String drSubjectNo;

    /**
     * 借记客户编号
     */
    private String drCustId;

    /**
     * 贷记科目
     */
    private String crSubjectNo;

    /**
     * 贷记客户编号
     */
    private String crCustId;

    /**
     * 冲正金额
     */
    private Long amount;

    /**
     * 冲正原因
     */
    private Date bookTime;

    /**
     * 获取冲正明细编号
     *
     * @return reverse_id - 冲正明细编号
     */
    public Long getReverseId() {
        return reverseId;
    }

    /**
     * 设置冲正明细编号
     *
     * @param reverseId 冲正明细编号
     */
    public void setReverseId(Long reverseId) {
        this.reverseId = reverseId;
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
     * 获取冲正金额
     *
     * @return amount - 冲正金额
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * 设置冲正金额
     *
     * @param amount 冲正金额
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * 获取冲正原因
     *
     * @return book_time - 冲正原因
     */
    public Date getBookTime() {
        return bookTime;
    }

    /**
     * 设置冲正原因
     *
     * @param bookTime 冲正原因
     */
    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }
}