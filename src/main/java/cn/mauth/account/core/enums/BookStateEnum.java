package cn.mauth.account.core.enums;

public enum BookStateEnum {

    INIT("INIT", "初始化"),
    BALANCE_SUCC("BALANCE_SUCC", "余额变动成功"),
    BALANCE_FAIL("BALANCE_FAIL", "余额变动失败"),
    ACCOUNT_SUCC("ACCOUNT_SUCC", "复式记账成功"),
    ACCOUNT_FAIL("ACCOUNT_FAIL", "复式记账失败"),
    REVERSE("REVERSE", "冲正");

    private String value;

    private String displayName;

    BookStateEnum(String value, String displayName){
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
