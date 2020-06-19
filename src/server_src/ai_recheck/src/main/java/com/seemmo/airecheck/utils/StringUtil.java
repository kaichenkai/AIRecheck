package com.seemmo.airecheck.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {
    /**
     * 下划线转驼峰
     *
     * @param fieldName
     * @return
     */
    public static String underlineToHump(String fieldName) {
        StringBuilder result = new StringBuilder();
        String[] para = fieldName.split("_");
        for (String s : para) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    public static int num(int length, int size) {
        return length % size == 0 ? length / size : length / size + 1;
    }


    public static boolean checkFileSuffix(String fileName, String... suffix) {
        if (suffix.length == 0) {
            return false;
        }
        boolean flag = false;
        for (int i = 0; i < suffix.length; i++) {
            flag = fileName.toLowerCase().endsWith(suffix[i]);
            if (flag) {
                return flag;
            }
        }
        return flag;
    }

    public static String removeSuffix(String fileName, String suffix) {
        int n;
        if ((n = fileName.indexOf(suffix)) != -1) {
            return fileName.substring(0, n);
        }
        return fileName;
    }

    public static String fillHead(String src, String prefix, int length) {
        int len = src.length();
        if (len < length) {
            while (len < length) {
                StringBuilder sb = new StringBuilder();
                sb.append(prefix).append(src);//左补prefix
                src = sb.toString();
                len = src.length();
            }
        }
        return src;
    }

    /**
     * 提供车牌 进行模糊搜索，将其变更为 %a%b% 的格式
     * @param carPlateNumber
     * @return
     */
    public static String replaceCarPlateNumber(String carPlateNumber) {
        if (StringUtils.isEmpty(carPlateNumber)) {
            return "";
        }
        char[] chars = carPlateNumber.toCharArray();
        StringBuilder buf = new StringBuilder();
        buf.append("%");
        for (int i = 0; i < chars.length; ++i) {
            buf.append(chars[i]);
            buf.append("%");
        }
        return buf.toString();
    }


}
