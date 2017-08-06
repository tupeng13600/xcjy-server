package com.xcjy.web.common.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/8/5.
 */
public enum RoleEnum {
    PERSONNEL_MANAGER("人事经理"),
    TEACHER_DIRECTOR("教研主任"),
    SUPER_ADMIN("系统管理员"),
    STUDENTMANAGER_BOSS("教管主任"),
    FINANCE("财务"),
    SCHOOLMASTER("分校校长"),
    PERSONNEL_CASHIER("人事专员出纳"),
    SCHOOLMASTER_BOSS("总校长"),
    CONSULTANT("咨询师"),
    STUDENTMANAGER("学管师"),
    TEACHER("教师"),
    CONSULTANT_MAIN("咨询总监"),
    CONSULTANT_BOSS("咨询主任");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Set<RoleEnum> getRoleList(List<String> roleIds){
        RoleEnum[] roleEnums = RoleEnum.values();
        return Arrays.stream(roleEnums).
                filter(roleEnum -> roleIds.contains(roleEnum.name()))
                .collect(Collectors.toSet());
    }

    public static List<RoleEnum> getAll(){
        RoleEnum[] roleEnums = RoleEnum.values();
        return Arrays.stream(roleEnums).collect(Collectors.toList());
    }

}
