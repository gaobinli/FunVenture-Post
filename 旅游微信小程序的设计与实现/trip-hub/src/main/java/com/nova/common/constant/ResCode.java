package com.nova.common.constant;

public enum ResCode {

    //参数改造项
    PARAM_VALID("0000","参数校验");

    private final String code;
    private final String value;

    ResCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String code() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * 根据code获取value
     *
     * @param code
     * @return
     */
    public static String getValue(String code) {
        for (ResCode r : ResCode.values()) {
            if (r.code().equals(code)) {
                return r.value;
            }
        }
        return null;
    }
}
