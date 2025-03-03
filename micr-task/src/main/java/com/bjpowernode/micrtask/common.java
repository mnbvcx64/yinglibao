package com.bjpowernode.micrtask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class common {

    public static String dateformat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
