package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.Student;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/8/11.
 * 学管师，教研主任
 */
@Api(value = "/stmanager", description = "学管师，教管主任相关接口")
@RestController
@RequestMapping("/stmanager")
public class StmanagerController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseScheduleStudentService courseScheduleStudentService;

    @Autowired
    private CourseScheduleService courseScheduleService;

    @Autowired
    private StmanagerStudentService stmanagerStudentService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private RelationService relationService;

    @Autowired
    private CourseStudentService courseStudentService;

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("获取学生资产信息[学管师]")
    @GetMapping("/assets")
    public List<StudentAssetsRes> getAssets(PageReq page) {
        return studentService.getAssets(page);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("为学生安排课表")
    @PostMapping("/course/schedule")
    public CreateIdRes createStudentSchedule(@RequestBody @Valid CourseScheduleCreateReq req) {
        return courseScheduleService.createStudentSchedule(req);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER_BOSS})
    @ApiOperation("获取教师课表信息")
    @GetMapping("/teacher/schedule")
    public List<TeacherScheduleRes> getTeacherSchedule(@RequestParam Boolean finish){
        return courseScheduleService.getTeacherSchedule(finish);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER_BOSS})
    @ApiOperation("获取教师课时统计信息")
    @GetMapping("/teacher/schedule/stat")
    public List<TeacherScheduleStatRes> getTeacherScheduleStat(TeacherScheduleStatReq req, PageReq page){
        return courseScheduleService.getTSStat(req, page);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER_BOSS})
    @ApiOperation("获取学管师续费|退费统计列表")
    @GetMapping("/stmanager/back")
    public List<StmanagerStatRes> getStmanagerBack(TeacherScheduleStatReq req){
        return stmanagerStudentService.getStmanagerBack(req);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("获取分配到的学生列表")
    @GetMapping("/student")
    public List<Student> getStudentList(PageReq page){
        return studentService.getForStmanager(page);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("获取分配到的学生上课列表详情")
    @GetMapping("/student/schedule")
    public List<StudentScheduleRes> getStudentSchedule(){
        return courseScheduleStudentService.getForStmanager();
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("确认课表已经完成")
    @GetMapping("/student/finish/{id}")
    public void finishStudentCourse(@PathVariable String id){
        courseScheduleStudentService.finish(id);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("取消课表")
    @GetMapping("/student/schedule/cancel/{courseScheduleId}/{studentId}")
    public void cancelSchedule(@PathVariable String courseScheduleId, @PathVariable String studentId) {
        courseScheduleStudentService.cancel(courseScheduleId, studentId);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("查看学员上课统计")
    @GetMapping("/student/stat")
    public List<CourseStudentStatRes> getStudentStat() {
        return courseStudentService.stat();
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("创建退费申请")
    @PostMapping("/back/money")
    public CreateIdRes backMoney(@RequestBody @Valid BackMoneyCreateReq req) {
        return applicationService.backMoney(req);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER_BOSS})
    @ApiOperation("分配学生给学管师")
    @PostMapping("/stmanager/student")
    public void createStmanagerStudent(@RequestBody @Valid StmanagerStudentCreateReq req) {
        relationService.stmanagerStudent(req);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("更新课表")
    @PutMapping("/course/schedule")
    public void update(@RequestBody @Valid CourseScheduleUpdateReq req) {
        courseScheduleService.update(req);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("获取课表列表")
    @GetMapping("/course/schedule")
    public List<ScheduleRes> listSchedule(PageReq req) {
        return courseScheduleService.list(req);
    }

    @RequiresRoles({CommonUtil.STUDENTMANAGER})
    @ApiOperation("录入学生成绩")
    @PutMapping("/score/{courseId}/{score}/{studentId}")
    public void setScore(@PathVariable String courseId, @PathVariable String studentId, @PathVariable Integer score) {
        courseStudentService.updateScore(courseId,studentId, score);
    }

}
