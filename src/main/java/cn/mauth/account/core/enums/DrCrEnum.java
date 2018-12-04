package cn.mauth.account.core.enums;

public enum DrCrEnum {

    DR("dr", "借记"),
    CR("cr", "贷记");

    private String value;

    private String displayName;

    DrCrEnum(String value, String displayName){
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
