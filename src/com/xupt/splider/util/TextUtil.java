package com.xupt.splider.util;

/**
 * Created by 梁峻磊 on 2017/8/27.
 */
public class TextUtil {
    public static Boolean isEmpty(String str){
        if (str == null || str.trim().length() == 0){
            return true;
        }
        return false;
    }
}
