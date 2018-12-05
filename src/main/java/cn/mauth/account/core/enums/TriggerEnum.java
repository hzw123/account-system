package cn.mauth.account.core.enums;

public enum TriggerEnum {

    WAITING("WAITING","等待"),

    PAUSED("PAUSED","暂停");

    private String value;
    private String displayName;

    TriggerEnum(String value,String displayName){
        this.value=value;
        this.displayName=displayName;
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
