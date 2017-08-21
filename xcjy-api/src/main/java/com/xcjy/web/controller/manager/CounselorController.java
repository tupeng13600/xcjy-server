package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.Student;
import com.xcjy.web.common.enums.DistributionTypeEnum;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    private ExcelService excelService;

    @Autowired
    private CounselorStudentService counselorStudentService;

    @RequiresRoles({CommonUtil.CONSULTANT, CommonUtil.CONSULTANT_MAIN})
    @ApiOperation("获取学生资产信息[咨询师]")
    @GetMapping("/assets")
    public List<StudentAssetsRes> getAssets() {
        return studentService.getAssets();
    }

    @RequiresRoles({CommonUtil.CONSULTANT, CommonUtil.CONSULTANT_MAIN})
    @ApiOperation("为学生购买课程")
    @PostMapping("/course")
    public void course(@RequestBody @Valid CourseStudentReq req) {
        courseStudentService.buyCourse(req);
    }

    @RequiresRoles({CommonUtil.CONSULTANT, CommonUtil.CONSULTANT_MAIN})
    @ApiOperation("咨询师查看自己金额和总和")
    @GetMapping("/student/stat")
    public CounselorStatRes getCounselorStat(CounselorStatReq req) {
        return studentAssetService.getCounselorStat(req);
    }

    @RequiresRoles({CommonUtil.CONSULTANT_MAIN, CommonUtil.CONSULTANT_BOSS})
    @ApiOperation("咨询主任获取咨询师签约数据")
    @GetMapping("/stat")
    public List<CounselorAssesSignRes> getAssetsSign(AssetsSignReq req) {
        return studentAssetService.getAssetsSign(req);
    }

    @RequiresRoles({CommonUtil.CONSULTANT_BOSS})
    @ApiOperation("咨询总监获取咨询记录")
    @GetMapping("/record")
    public List<CounselorStuStatusRes> getCounselorStudentTypeHis(Date startTime, Date endTime) {
        return courseStudentService.getCounselorStudentTypeHis(startTime, endTime);
    }

    @RequiresRoles({CommonUtil.CONSULTANT_BOSS})
    @ApiOperation("获取未分配的学生列表")
    @GetMapping("/student/distribution/no")
    public List<StudentShowRes> getList4NoCounselor() {
        return studentService.getList4ByDisType(DistributionTypeEnum.NO_DISTRIBUTION);
    }

    @RequiresRoles({CommonUtil.CONSULTANT_BOSS})
    @ApiOperation("咨询总监分配学生给咨询师")
    @PostMapping("/counselor/student")
    public void createCounselorStudent(@RequestBody @Valid CounselorStudentCreateReq req) {
        relationService.counselorStudent(req);
    }

    @RequiresRoles({CommonUtil.CONSULTANT, CommonUtil.CONSULTANT_MAIN})
    @ApiOperation("创建学生")
    @PostMapping("/student")
    public CreateIdRes create(@RequestBody @Valid StudentCreateReq req) {
        return studentService.create(req);
    }

    @RequiresRoles({CommonUtil.CONSULTANT, CommonUtil.CONSULTANT_MAIN})
    @ApiOperation("修改学生信息")
    @PutMapping("/student")
    public void update(@RequestBody @Valid StudentUpdateReq req) {
        studentService.update(req);
    }

    @RequiresRoles({CommonUtil.CONSULTANT_BOSS})
    @ApiOperation("删除学生")
    @DeleteMapping("/student/{studentId}")
    public void update(@PathVariable String studentId) {
        studentService.deleteLogic(studentId);
    }


    @RequiresRoles({CommonUtil.CONSULTANT, CommonUtil.CONSULTANT_MAIN})
    @ApiOperation("获取分配到的学生列表")
    @GetMapping("/student")
    public List<CounselorStudentRes> list() {
        return studentService.list4Counselor();
    }

    @RequiresRoles({CommonUtil.CONSULTANT_BOSS})
    @ApiOperation("导入学生信息")
    @PostMapping("/student/excel")
    public void student(@RequestParam MultipartFile file) throws IOException {
        excelService.importStudent(file);
    }

    @RequiresRoles({CommonUtil.CONSULTANT, CommonUtil.CONSULTANT_MAIN})
    @ApiOperation("更新学生跟进状态")
    @PostMapping("/counselor/student/{studentId}")
    public void updateCounStu2NoPay(@PathVariable String studentId) throws IOException {
        counselorStudentService.updateCounStu2NoPay(studentId);
    }

}
