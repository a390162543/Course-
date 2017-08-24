package cn.zhsite.extra;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Util {

    public static LocalDate getCurrentDate(){
        return LocalDate.now();
    }

    public static LocalDateTime getCurrentDateTime(){
        return LocalDateTime.now();
    }

    public static String formatInt(Integer integer,int length){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumIntegerDigits(length);
        numberFormat.setMaximumIntegerDigits(length);
        return  numberFormat.format(integer);
    }
}
