package com.xcjy.web.mapper;

import com.xcjy.web.bean.AplnBackMoney;
import com.xcjy.web.common.enums.ApplicationStatusType;
import com.xcjy.web.controller.res.AplnSimpleRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface AplnBackMoneyMapper {
    int insert(AplnBackMoney record);

    AplnBackMoney getById(@Param("id") String id);

    void updateStatus(@Param("backMoney") AplnBackMoney aplnBackMoney, @Param("status")ApplicationStatusType status);

    List<AplnSimpleRes> getSimpleResByIds(@Param("ids") Set<String> ids);

    List<AplnBackMoney> getByApplicationIds(@Param("userId") String userId);

    List<AplnBackMoney> getByStatusAndSIds(@Param("applicationStatus") ApplicationStatusType applicationStatus, @Param("studentIds") List<String> studentIds);

    List<AplnBackMoney> getByStatusAndSId(@Param("applicationStatus") ApplicationStatusType applicationStatus, @Param("studentId") String studentId);
}