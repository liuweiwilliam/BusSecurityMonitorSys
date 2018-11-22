package com.lzz.bussecurity.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LzzStringUtils {
	/** 
     * 通过正则表达式的方式获取字符串中指定字符的个数 
     * @param text 查找的字符串 
     * @param text2 指定字符
     * @return 指定字符的个数 
     */  
    public static int pattern(String text, String text2) {  
        // 根据指定的字符构建正则  
        Pattern pattern = Pattern.compile(text2);  
        // 构建字符串和正则的匹配  
        Matcher matcher = pattern.matcher(text);  
        int count = 0;  
        // 循环依次往下匹配  
        while (matcher.find()){ // 如果匹配,则数量+1  
            count++;  
        }  
        return  count;  
    }  
}
