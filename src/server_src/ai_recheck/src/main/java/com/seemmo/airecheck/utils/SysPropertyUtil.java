package com.seemmo.airecheck.utils;

/**
 * @author ALEI
 * @date 2019/4/13
 * @describe ""
 */
public class SysPropertyUtil {
    public static String getINSTALLDIR(){
//        return System.getProperty("INSTALLDIR","/home/seemmo");
        return System.getProperty("INSTALLDIR","E:\\temp\\home\\seemmo\\");
    }
}
