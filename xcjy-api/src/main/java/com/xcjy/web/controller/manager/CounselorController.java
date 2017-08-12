package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.Student;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.controller.res.CounselorAssesSignRes;
import com.xcjy.web.controller.res.CounselorStatRes;
import com.xcjy.web.controller.res.CounselorStuStatusRes;
import com.xcjy.web.controller.res.StudentAssetsRes;
import com.xcjy.web.service.*;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/counselor")
public class CounselorController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseStudentService courseStudentService;

    @Autowired
    private StudentAssetService studentAssetService;

    @Autowired
    private RelationService relationService;

    @ApiOperation("导入已缴费学生信息")
    @PostMapping("/student/paid")
    public void studentPaid(@RequestParam MultipartFile file) throws IOException {
        excelService.importStudentPaid(file);
    }

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
    public CounselorStatRes getCounselorStat(@RequestBody @Valid CounselorStatReq req, PageReq page){
        return studentAssetService.getCounselorStat(req, page);
    }


    @ApiOperation("咨询主任获取本人咨询师签约数据")
    @GetMapping("/counselor/stat")
    public List<CounselorAssesSignRes> getAssetsSign(@RequestBody @Valid AssetsSignReq req, PageReq page){
        return studentAssetService.getAssetsSign(req, page);
    }

    @ApiOperation("咨询总监获取咨询记录")
    @GetMapping("/counselor/stat")
    public List<CounselorStuStatusRes> getCounselorStudentTypeHis(Date startTime, Date endTime){
        return courseStudentService.getCounselorStudentTypeHis(startTime, endTime);
    }

    /**
     * 分配学生给咨询师
     *
     * @param req
     */
    @ApiOperation("咨询总监分配学生给咨询师")
    @PostMapping("/counselor/student")
    public void createCounselorStudent(@RequestBody @Valid CounselorStudentCreateReq req) {
        relationService.counselorStudent(req);
    }



}
