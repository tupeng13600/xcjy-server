package com.xcjy.web.common.util;

import com.xcjy.web.common.enums.RoleEnum;
import org.apache.shiro.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tupeng on 2017/8/6.
 */
public abstract class CommonUtil {

    //人事经理
    public static final String PERSONNEL_MANAGER = "PERSONNEL_MANAGER";
    //教研主任
    public static final String TEACHER_DIRECTOR = "TEACHER_DIRECTOR";
    //管理员
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    //教管主任
    public static final String STUDENTMANAGER_BOSS = "STUDENTMANAGER_BOSS";
    //财务
    public static final String FINANCE = "FINANCE";
    //分校长
    public static final String SCHOOLMASTER = "SCHOOLMASTER";
    //人事专员出纳
    public static final String PERSONNEL_CASHIER = "PERSONNEL_CASHIER";
    //总校长
    public static final String SCHOOLMASTER_BOSS = "SCHOOLMASTER_BOSS";
    //咨询师
    public static final String CONSULTANT = "CONSULTANT";
    //学管师
    public static final String STUDENTMANAGER = "STUDENTMANAGER";
    //教师
    public static final String TEACHER = "TEACHER";
    //咨询主任
    public static final String CONSULTANT_MAIN = "CONSULTANT_MAIN";
    //咨询总监
    public static final String CONSULTANT_BOSS = "CONSULTANT_BOSS";

    private static List<RoleEnum> notInSchoolRole = new ArrayList<>();

    static {
        notInSchoolRole.add(RoleEnum.PERSONNEL_MANAGER);
        notInSchoolRole.add(RoleEnum.SUPER_ADMIN);
        notInSchoolRole.add(RoleEnum.FINANCE);
        notInSchoolRole.add(RoleEnum.PERSONNEL_CASHIER);
        notInSchoolRole.add(RoleEnum.SCHOOLMASTER_BOSS);
        notInSchoolRole.add(RoleEnum.CONSULTANT_MAIN);
    }

    public static String getRolIdString(List<RoleEnum> roleEnums) {
        if (CollectionUtils.isEmpty(roleEnums)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        roleEnums.forEach(roleEnum -> builder.append(roleEnum.name()).append(","));
        return builder.toString().substring(0, builder.toString().length() - 1);
    }

    public static Boolean belongToSchool(RoleEnum roleEnum){
        return !notInSchoolRole.contains(roleEnum);
    }

}
