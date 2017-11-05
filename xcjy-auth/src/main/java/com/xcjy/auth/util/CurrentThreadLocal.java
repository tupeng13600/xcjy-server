package com.xcjy.auth.util;

/**
 * Created by tupeng on 2017/7/22.
 */
public class CurrentThreadLocal {

    private static ThreadLocal<String> school = new ThreadLocal();

    public static ThreadLocal<Boolean> deleted = new ThreadLocal();

    public static void setSchoolId(String schoolId) {
        school.set(schoolId);
    }

    public static String getSchoolId() {
        return school.get();
    }

    public static void removeSchoolId() {
        school.remove();
    }

    public static void ignoreDeleted() {
        deleted.set(false);
    }

    public static void needDeleted() {
        deleted.set(true);
    }

    public static Boolean isDeleted() {
        Boolean delete = deleted.get();
        deleted.remove();
        return null == delete ? true : delete;
    }

}
