package com.xcjy.web.controller.req;

import lombok.Data;

/**
 * Created by tupeng on 2017/8/6.
 */
@Data
public class PageReq {

    private Integer page = 1;

    private Integer pageSize = 10;

}
