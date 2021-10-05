package com.example.red_pakege.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static BigDecimal getBigDecimal(String num,int scale){
        return new BigDecimal(num).setScale(scale,BigDecimal.ROUND_HALF_UP);
    }
    public static int checkIsDoublePointTwo(String param) {
        if (param == null) {
            return 0;
        }
        String[] split = param.split("\\.");
        if (split.length <= 1){
            return 0;
        }
        return split[1].length();
    }
}
