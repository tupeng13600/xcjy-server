package com.xcjy.web.controller.req;

import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class AssetsSignReq {

    private Date startTime;

    private Date endTime;

    private String employeeId;

}
