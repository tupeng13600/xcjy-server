package com.xcjy.web.service;

import com.xcjy.web.bean.Employee;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.enums.StudentPayType;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.req.TeacherScheduleStatReq;
import com.xcjy.web.controller.res.PayStatModel;
import com.xcjy.web.controller.res.StmanagerStatRes;
import com.xcjy.web.mapper.EmployeeMapper;
import com.xcjy.web.mapper.StmanagerStudentMapper;
import com.xcjy.web.mapper.StudentPayLogMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by tupeng on 2017/8/11.
 */
@Service
public class StmanagerStudentService {

    @Autowired
    private StmanagerStudentMapper stmanagerStudentMapper;

    @Autowired
    private StudentPayLogMapper studentPayLogMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<StmanagerStatRes> getStmanagerBack(TeacherScheduleStatReq req) {
        List<StmanagerStatRes> statResList = new ArrayList<>();
        List<String> idList = studentPayLogMapper.getIdsByStartAndEnd(req.getStartTime(), req.getEndTime());
        if (CollectionUtils.isNotEmpty(idList)) {
            List<PayStatModel> payList = studentPayLogMapper.getStatByIds(new HashSet<>(idList), StudentPayType.STUDENTMANAGER_PAY);
            List<PayStatModel> backList = studentPayLogMapper.getStatByIds(new HashSet<>(idList), StudentPayType.STUDENTMANAGER_BACK);
            List<Employee> employeeList = employeeMapper.getByIds(new HashSet<>(idList));
            idList.forEach(id -> {
                statResList.add(getRes(id, payList, backList, employeeList));
            });
        }
        return statResList;
    }

    private StmanagerStatRes getRes(String employeeId,
                                    List<PayStatModel> payList,
                                    List<PayStatModel> backList,
                                    List<Employee> employeeList) {
        StmanagerStatRes statRes = new StmanagerStatRes();
        statRes.setTeacherId(employeeId);
        for (PayStatModel payStatModel : payList) {
            if (employeeId.equals(payStatModel.getEmployeeId())) {
                statRes.setRenewMoney(payStatModel.getTotalMoney());
            }
        }
        for (PayStatModel backModel : backList) {
            if (employeeId.equals(backModel.getEmployeeId())) {
                statRes.setBackMoney(backModel.getTotalMoney());
            }
        }

        for (Employee employee : employeeList) {
            if (employeeId.equals(employee.getId())) {
                statRes.setTeacherName(employee.getName());
                statRes.setTeacherPhone(employee.getPhone());
            }
        }
        return statRes;
    }

}
