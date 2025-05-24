package com.escapenest.controller.adminController;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminReportController {
    @GetMapping("/report")
    public String viewReporPage(){
        return "/admin/report/index";
    }
}
