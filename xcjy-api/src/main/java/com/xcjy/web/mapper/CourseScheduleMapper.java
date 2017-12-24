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

    void deleteLogic(@Param("id") String id, @Param("updateTime") Date updateTime);

    List<CourseSchedule> getByFinish(@Param("finish") Boolean finish);

    List<CourseScheduleStatModel> getByStartEndFinish(@Param("startTime") Date startTime,
                                                      @Param("endTime") Date endTime,
                                                      @Param("finish") Boolean finish);

    List<CourseScheduleStatModel> getByEmployeeIds(@Param("teacherIds") Set<String> teacherIds, @Param("finish") boolean finish);

    List<CourseScheduleStatModel> getByEmployeeIdsAndTime(@Param("teacherIds") Set<String> teacherIds,
                                                          @Param("startTime") Date startTime,
                                                          @Param("endTime") Date endTime,
                                                          @Param("finish") boolean finish);

    List<CourseSchedule> getByIds(@Param("ids") Set<String> ids);

    List<CourseSchedule> getByEmployeeId(@Param("teacherId") String teacherId);

    List<CourseSchedule> getByEmployeeIdAndFinish(@Param("teacherId") String teacherId,
                                                  @Param("startTime") Date startTime,
                                                  @Param("endTime") Date endTime,
                                                  @Param("finish") Boolean finish);

    void updateFinish(@Param("courseSchedule") CourseSchedule courseSchedule);

    List<CourseSchedule> getByCourseId(@Param("courseId") String courseId);

    void deleteByCourseId(@Param("courseId") String courseId);
}