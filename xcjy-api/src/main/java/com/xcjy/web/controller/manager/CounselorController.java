package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.Student;
import com.xcjy.web.common.enums.DistributionTypeEnum;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.service.CourseStudentService;
import com.xcjy.web.service.RelationService;
import com.xcjy.web.service.StudentAssetService;
import com.xcjy.web.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/8/11.
 */
@Api(value = "/counselor", description = "咨询师，咨询主任，咨询总监相关接口")
@RestController
@RequestMapping("/counselor")
public class CounselorController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseStudentService courseStudentService;

    @Autowired
    private StudentAssetService studentAssetService;

    @Autowired
    private RelationService relationService;

    @ApiOperation("获取学生资产信息[学管师,咨询师]")
    @GetMapping("/assets")
    public List<StudentAssetsRes> getAssets(PageReq page) {
        return studentService.getAssets(page);
    }

    @ApiOperation("为学生购买课程")
    @PostMapping("/course")
    public void course(@RequestBody @Valid CourseStudentReq req) {
        courseStudentService.buyCourse(req);
    }

    @ApiOperation("咨询师查看自己金额和总和")
    @GetMapping("/student/stat")
    public CounselorStatRes getCounselorStat(CounselorStatReq req) {
        return studentAssetService.getCounselorStat(req);
    }

    @ApiOperation("咨询主任获取咨询师签约数据")
    @GetMapping("/stat")
    public List<CounselorAssesSignRes> getAssetsSign(AssetsSignReq req) {
        return studentAssetService.getAssetsSign(req);
    }

    @ApiOperation("咨询总监获取咨询记录")
    @GetMapping("/record")
    public List<CounselorStuStatusRes> getCounselorStudentTypeHis(Date startTime, Date endTime) {
        return courseStudentService.getCounselorStudentTypeHis(startTime, endTime);
    }

    @ApiOperation("获取未分配的学生列表")
    @GetMapping("/student/distribution/{distributionType}")
    public List<StudentShowRes> getList4NoCounselor(@PathVariable DistributionTypeEnum distributionType) {
        return studentService.getList4ByDisType(distributionType);
    }

    @ApiOperation("咨询总监分配学生给咨询师")
    @PostMapping("/counselor/student")
    public void createCounselorStudent(@RequestBody @Valid CounselorStudentCreateReq req) {
        relationService.counselorStudent(req);
    }

    @ApiOperation("创建学生")
    @PostMapping("/student")
    public CreateIdRes create(@RequestBody @Valid StudentCreateReq req) {
        return studentService.create(req);
    }

    @ApiOperation("修改学生信息")
    @PutMapping("/student")
    public void update(@RequestBody @Valid StudentUpdateReq req) {
        studentService.update(req);
    }

    @ApiOperation("获取学生列表")
    @GetMapping("/student")
    public List<CounselorStudentRes> list() {
        return studentService.list4Counselor();
    }

}
