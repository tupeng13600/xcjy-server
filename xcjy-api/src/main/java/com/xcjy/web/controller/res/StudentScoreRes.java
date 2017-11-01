package com.xcjy.web.controller.res;

import lombok.Data;

@Data
public class StudentScoreRes {

    private String studentId;

    private String studentName;

    private String gradeName;

    private String courseName;

    private Integer score;

    private Integer studyHour;

    private Integer buyHour;

    private Integer hasUsed;

}
