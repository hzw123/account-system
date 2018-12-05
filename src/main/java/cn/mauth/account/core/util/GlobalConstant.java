package cn.mauth.account.core.util;

/**
 * 全局常量
 */
public final class GlobalConstant {

    private GlobalConstant(){}

    public static final String PLATFORM_INSID = "0000001";

    public static final String keyLinkSymbol = "-";

    /** 多数据源key : 账户系统数据源 **/
    public static final String ACCOUNT_SYSTEM_DATA_SOURCE_KEY = "dataSourceAccount";
    /** 多数据源key : 定时任务数据源 **/
    public static final String QUARTZ_DATA_SOURCE_KEY  = "dataSourceQuartz";

}
