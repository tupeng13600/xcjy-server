package com.xcjy.web.mapper;

import com.xcjy.web.bean.CourseSchedule;
import com.xcjy.web.controller.res.CourseScheduleStatModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface CourseScheduleMapper {
    int insert(CourseSchedule record);

    CourseSchedule getById(@Param("id") String id);

    void updateBase(@Param("schedule") CourseSchedule courseSchedule);

    List<CourseSchedule> listAll();

    void deleteLogic(@Param("id") String id, @Param("updateTime")Date updateTime);

    List<CourseSchedule> getByFinish(@Param("finish") Boolean finish);

    List<CourseScheduleStatModel> getByStartEndFinish(@Param("startTime") Date startTime,
                                                      @Param("endTime") Date endTime,
                                                      @Param("finish") Boolean finish);

    List<CourseScheduleStatModel> getByEmployeeIds(@Param("teacherIds") Set<String> teacherIds, @Param("finish")  boolean finish);
}