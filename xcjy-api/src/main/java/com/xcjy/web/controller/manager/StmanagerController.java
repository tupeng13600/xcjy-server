package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.Student;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/8/11.
 * 学管师，教研主任
 */
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

    @ApiOperation("获取学生资产信息[学管师,咨询师]")
    @GetMapping("/assets")
    public List<StudentAssetsRes> getAssets(PageReq page) {
        return studentService.getAssets(page);
    }

    @ApiOperation("为学生安排课表")
    @PostMapping("/course/schedule")
    public CreateIdRes createStudentSchedule(@RequestBody @Valid CourseScheduleCreateReq req) {
        return courseScheduleService.createStudentSchedule(req);
    }

    @ApiOperation("获取教师课表信息")
    @GetMapping("/teacher/schedule")
    public List<TeacherScheduleRes> getTeacherSchedule(@RequestParam Boolean finish, PageReq page){
        return courseScheduleService.getTeacherSchedule(finish, page);
    }

    @ApiOperation("获取教师课时统计信息")
    @GetMapping("/teacher/schedule/stat")
    public List<TeacherScheduleStatRes> getTeacherScheduleStat(TeacherScheduleStatReq req, PageReq page){
        return courseScheduleService.getTSStat(req, page);
    }

    @ApiOperation("获取学管师续费|退费统计列表")
    @GetMapping("/stmanager/back")
    public List<StmanagerStatRes> getStmanagerBack(TeacherScheduleStatReq req, PageReq page){
        return stmanagerStudentService.getStmanagerBack(req, page);
    }

    @ApiOperation("获取分配到的学生列表")
    @GetMapping("/student")
    public List<Student> getStudentList(PageReq page){
        return studentService.getForStmanager(page);
    }

    @ApiOperation("获取分配到的学生上课列表详情")
    @GetMapping("/student/schedule")
    public List<StudentScheduleRes> getStudentSchedule(){
        return courseScheduleStudentService.getForStmanager();
    }

    @ApiOperation("确认课表已经完成")
    @GetMapping("/student/finish/{id}")
    public void finishStudentCourse(@PathVariable String id){
        courseScheduleStudentService.finish(id);
    }

    @ApiOperation("取消课表")
    @GetMapping("/student/schedule/cancel/{courseScheduleId}/{studentId}")
    public void cancelSchedule(@PathVariable String courseScheduleId, @PathVariable String studentId) {
        courseScheduleStudentService.cancel(courseScheduleId, studentId);
    }

    @ApiOperation("查看学员上课统计")
    @GetMapping("/student/stat")
    public void getStudentStat() {
        // TODO: 2017/8/12 学生上课时间统计
    }

    @ApiOperation("创建退费申请")
    @PostMapping("/back/money")
    public CreateIdRes backMoney(@RequestBody @Valid BackMoneyCreateReq req) {
        return applicationService.backMoney(req);
    }

    /**
     * 分配学生给学管师
     *
     * @param req
     */
    @ApiOperation("分配学生给学管师")
    @PostMapping("/stmanager/student")
    public void createStmanagerStudent(@RequestBody @Valid StmanagerStudentCreateReq req) {
        relationService.stmanagerStudent(req);
    }

    @ApiOperation("更新课表")
    @PutMapping
    public void update(@RequestBody @Valid CourseScheduleUpdateReq req) {
        courseScheduleService.update(req);
    }

    @ApiOperation("获取课表列表")
    @GetMapping
    public List<ScheduleRes> listSchedule(PageReq req) {
        return courseScheduleService.list(req);
    }

}
