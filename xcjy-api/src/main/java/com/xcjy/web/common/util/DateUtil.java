package com.xcjy.web.common.util;

import com.xcjy.web.common.exception.EducationException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by tupeng on 2017/7/22.
 */
public abstract class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static Date getBirthByIdCard(String idCard) {
        try {
            if (StringUtils.isBlank(idCard) || idCard.length() != 18) {
                return null;
            }
            Integer year = Integer.parseInt(idCard.substring(6, 10));
            Integer month = toInteger(idCard.substring(10, 12));
            Integer day = toInteger(idCard.substring(12, 14));

            LocalDate date = new LocalDate(year, month, day);
            return date.toDate();
        }catch (Exception e) {
            logger.warn("身份证号码异常：{}", idCard);
            throw new EducationException("身份证号码格式不正确");
        }
    }

    public static Integer toInteger(String str){
        if(str.startsWith("0")) {
            return Integer.parseInt(str.substring(1, str.length()));
        }
        return Integer.parseInt(str);
    }

}
