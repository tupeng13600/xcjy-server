package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 */
@Data
public class PresidentStatRes {

    private Integer total = 0;

    private Integer num = 0;

    private List<PresidentDetail> details;

}
