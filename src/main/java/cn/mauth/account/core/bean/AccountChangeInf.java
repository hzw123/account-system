package cn.mauth.account.core.bean;

import cn.mauth.account.core.model.AccountBook;

import java.io.Serializable;

/**
 * 账户单边记账余额变动信息
 */
public class AccountChangeInf implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 变动账户号
     */
    private String accountId;

    /**
     * 变动订单号
     */
    private String orderId;

    /**
     * 记账规则
     */
    private String ruleId;

    /**
     * 变动金额
     */
    private Long amount;

    /**
     * 借记发生额
     */
    private Long drAmount;

    /**
     * 贷记发生额
     */
    private Long crAmount;

    /**
     * 当前单边账进行借记还是贷记
     */
    private Enum drcrFlag;

    /**
     * 当前单边账的原始复式记账明细
     */
    private AccountBook accountBook;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Enum getDrcrFlag() {
        return drcrFlag;
    }

    public void setDrcrFlag(Enum drcrFlag) {
        this.drcrFlag = drcrFlag;
    }

    public AccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
    }
}
