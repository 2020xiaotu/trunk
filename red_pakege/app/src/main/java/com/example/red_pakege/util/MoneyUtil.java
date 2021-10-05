package com.example.red_pakege.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyUtil {

    public static void main(String[] args){

        System.out.println(parMoney2("10"));
    }

    //金额验证
    public static boolean isMoney(String str){
        Pattern pattern=Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])"); // 数字或小数
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }

    public static String parMoney2 (String str){
        if (!TextUtils.isEmpty(str)&&isNumeric(str)){
            BigDecimal money = new BigDecimal(str);
            BigDecimal bigDecimal = money.setScale(2, BigDecimal.ROUND_HALF_UP);
            return String.valueOf(bigDecimal);
        }else {
            return "";
        }
    }
    public static String parMoney3 (String str){
        if (!TextUtils.isEmpty(str)&&isNumeric(str)){
            BigDecimal money = new BigDecimal(str);
            BigDecimal bigDecimal = money.setScale(3, BigDecimal.ROUND_HALF_UP);
            return String.valueOf(bigDecimal);
        }else {
            return "";
        }
    }

    public static boolean isNumeric(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        String regx = "[+-]*\\d+\\.?\\d*[Ee]*[+-]*\\d+";
        Pattern pattern = Pattern.compile(regx);
        boolean isNumber = pattern.matcher(str).matches();
        if (isNumber) {
            return isNumber;
        }
        regx = "^[-\\+]?[.\\d]*$";
        pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }


}
