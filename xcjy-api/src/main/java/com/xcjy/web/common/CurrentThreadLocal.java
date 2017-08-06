package com.xcjy.web.common;

import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.res.PageRes;

/**
 * Created by tupeng on 2017/7/22.
 */
public class CurrentThreadLocal {

    private static ThreadLocal<String> school = new ThreadLocal();

    private static ThreadLocal<PageRes> pageRes = new ThreadLocal();

    private static ThreadLocal<PageReq> pageReq = new ThreadLocal();

    public static void setSchoolId(String schoolId) {
        school.set(schoolId);
    }

    public static String getSchoolId() {
        return school.get();
    }

    public static void removeSchoolId(){
        school.remove();
    }

    public static void setPageReq(PageReq req) {
        pageReq.set(req);
    }

    public static PageReq getPageReq() {
        return pageReq.get();
    }

    public static void removePageReq(){
        pageReq.remove();
    }

    public static void setPageRes(PageRes res) {
        pageRes.set(res);
    }

    public static PageRes getPageRes() {
        return pageRes.get();
    }

    public static void removePageRes(){
        pageRes.remove();
    }

}
