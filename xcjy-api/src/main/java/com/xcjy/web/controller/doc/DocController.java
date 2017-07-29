package com.xcjy.web.controller.doc;

import com.knappsack.swagger4springweb.controller.ApiDocumentationController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tupeng on 2017/7/29.
 */
@Controller
@RequestMapping("/doc")
public class DocController extends ApiDocumentationController {

    public DocController() {
        setBaseControllerPackage("com.knappsack.swagger4springweb.controllers.api");
        setBaseModelPackage("com.knappsack.swagger4springweb.models");
        setApiVersion("v1");
    }

    @GetMapping
    public String documentation() {
        return "documentation";
    }

}
