package com.neuhealth.demo.util;

import java.util.Date;
import java.time.Instant;
import java.time.ZoneOffset;

/**
 * 时间操作工具类
 * 提供将Date对象增加8小时的功能
 */
public class TimeAdditionUtil {

    // 私有构造函数防止实例化
    private TimeAdditionUtil() {}

    /**
     * 将Date对象增加8个小时
     * @param date 原始日期对象，可为null
     * @return 增加8小时后的日期对象，输入为null时返回null
     */
    public static Date add8Hours(Date date) {
        if (date == null) {
            return null;
        }

        try {
            // 使用Java 8的Instant进行时间计算
            Instant instant = date.toInstant();
            // 增加8小时（8*60*60*1000毫秒）
            Instant addedInstant = instant.plusMillis(8 * 60 * 60 * 1000);
            // 转换回Date对象
            return Date.from(addedInstant);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间计算失败", e);
        }
    }

    /**
     * 将Date对象增加指定小时数
     * @param date 原始日期对象，可为null
     * @param hours 要增加的小时数
     * @return 增加指定小时后的日期对象，输入为null时返回null
     */
    public static Date addHours(Date date, int hours) {
        if (date == null) {
            return null;
        }

        try {
            Instant instant = date.toInstant();
            Instant addedInstant = instant.plusMillis(hours * 60 * 60 * 1000);
            return Date.from(addedInstant);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间计算失败", e);
        }
    }

    /**
     * 将Date对象增加指定分钟数
     * @param date 原始日期对象，可为null
     * @param minutes 要增加的分钟数
     * @return 增加指定分钟后的日期对象，输入为null时返回null
     */
    public static Date addMinutes(Date date, int minutes) {
        if (date == null) {
            return null;
        }

        try {
            Instant instant = date.toInstant();
            Instant addedInstant = instant.plusMillis(minutes * 60 * 1000);
            return Date.from(addedInstant);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间计算失败", e);
        }
    }

    /**
     * 将Date对象增加指定秒数
     * @param date 原始日期对象，可为null
     * @param seconds 要增加的秒数
     * @return 增加指定秒数后的日期对象，输入为null时返回null
     */
    public static Date addSeconds(Date date, int seconds) {
        if (date == null) {
            return null;
        }

        try {
            Instant instant = date.toInstant();
            Instant addedInstant = instant.plusMillis(seconds * 1000);
            return Date.from(addedInstant);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间计算失败", e);
        }
    }
}