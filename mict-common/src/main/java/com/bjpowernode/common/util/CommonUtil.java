package com.bjpowernode.common.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class CommonUtil {
    public static int defaultPageNo(Integer pageNo){
        int pNo = pageNo;
        //if (pageNo == null || pageNo < 1)
        if (pageNo < 1){
            pNo = 1;
        }
        return pNo;
    }
    public static int defaultPageSize(Integer pageSize){
        int pSize = pageSize;
        if (pageSize < 1){
            pSize = 1;
        }
        return pSize;
    }

    public static String desensitization(String phone){
        String result = "***********";
        if (phone!=null && phone.trim().length()==11){
            result=phone.substring(0,3)+"****"+phone.substring(7,11);
        }
        return result;
    }

    public static boolean checkPhone(String phone){
        boolean flag = false;
        if (phone !=null && phone.length()==11){
            flag = Pattern.matches("^1[1-9]\\d{9}$",phone);
        }
        return flag;
    }

    public static boolean compare(BigDecimal one,BigDecimal two){
        if (one ==null || two == null){
            throw new RuntimeException("参数不能为空");
        }
        return one.compareTo(two) >= 0;
    }
}
