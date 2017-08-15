package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/8/15.
 */
public enum DistributionTypeEnum {

    NO_DISTRIBUTION("未分配"),
    COUNSELOR_DISTRIBUTION("已分配咨询师"),
    STMANAGER_DISTRIBUTION("已分配学管师");

    private String name;

    DistributionTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
