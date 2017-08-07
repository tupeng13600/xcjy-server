package com.xcjy.web.common.util;

import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.enums.SexType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by tupeng on 2017/8/7.
 */
public abstract class ExcelParseUtil {

    public static SexType sexParse(String sex) {
        SexType[] sexTypes = SexType.values();
        for (SexType sexType : sexTypes) {
            if (sexType.getName().equals(sex.trim())) {
                return sexType;
            }
        }
        return null;
    }

    public static String schoolParse(String name) {
        return null == CacheFactory.nameSchools.get(name.trim()) ? null : CacheFactory.nameSchools.get(name.trim()).getId();
    }

    public static Date birthdayParse(String idCard) {
        return DateUtil.getBirthByIdCard(idCard);
    }

    public static String parsePhone2EmployeeId(String phone){
        if(StringUtils.isBlank(phone)) {
            return null;
        }
        for(Map.Entry<String, UserModel> entry : CacheFactory.userIdUsers.entrySet()) {
            if(phone.equals(entry.getValue().getPhone())) {
                String roleIds = entry.getValue().getRoleId();
                if(roleIds.contains(RoleEnum.CONSULTANT.name()) || roleIds.contains(RoleEnum.CONSULTANT_BOSS.name()) || roleIds.contains(RoleEnum.CONSULTANT_MAIN.name())) {
                    return entry.getValue().getEntityId();
                }
            }
        }
        throw new EducationException("用户中无该手机号码：" + phone);
    }

}
