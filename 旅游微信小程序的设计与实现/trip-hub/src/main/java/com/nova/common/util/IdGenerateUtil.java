package com.nova.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class IdGenerateUtil {

    /**
     * 指定前缀生成 id
     * @return
     */
    public static String prefix(String code) {
        String uuid = UUID.randomUUID().toString().substring(0, 5);
        String yyyyMMdd = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        return code + yyyyMMdd + uuid;
    }

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
