package com.nova.common.exception;

public abstract class ErrorCode {

    //系统错误代码前缀
    public static final String SYS_PRE = "30";
    //其他错误代码前缀
    public static final String OTHER_PRE = "50";

    /***
     * 获取系统错误代码
     * @param code
     * @return
     */
    public static final String sys(String code) {
        return SYS_PRE + code;
    }

    /***
     * 获取不确定的错误代码
     * @param code
     * @return
     */
    public static final String other(String code) {
        return OTHER_PRE + code;
    }
}
