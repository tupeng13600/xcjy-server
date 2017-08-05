package com.xcjy.web.mapper;

import com.xcjy.web.bean.AplnBackMoney;
import com.xcjy.web.common.enums.ApplicationStatusType;
import org.apache.ibatis.annotations.Param;

public interface AplnBackMoneyMapper {
    int insert(AplnBackMoney record);

    AplnBackMoney getById(@Param("id") String id);

    void updateStatus(@Param("backMoney") AplnBackMoney aplnBackMoney, @Param("status")ApplicationStatusType status);
}