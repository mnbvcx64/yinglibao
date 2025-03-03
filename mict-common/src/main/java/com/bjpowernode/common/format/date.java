package com.bjpowernode.common.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class date {
    public static String dateformat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
