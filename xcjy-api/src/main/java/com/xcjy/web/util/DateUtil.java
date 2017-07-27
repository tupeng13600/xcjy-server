package com.xcjy.web.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

import java.util.Date;

/**
 * Created by tupeng on 2017/7/22.
 */
public abstract class DateUtil {

    public static Date getBirthByIdCard(String idCard) {
        if(StringUtils.isBlank(idCard) || idCard.length() != 18) {
            return null;
        }
        Integer year = Integer.parseInt(idCard.substring(6, 10));
        Integer month = toInteger(idCard.substring(10, 12));
        Integer day = toInteger(idCard.substring(12, 14));

        LocalDate date = new LocalDate(year, month, day);
        return date.toDate();
    }

    public static Integer toInteger(String str){
        if(str.startsWith("0")) {
            return Integer.parseInt(str.substring(1, str.length()));
        }
        return Integer.parseInt(str);
    }

}
