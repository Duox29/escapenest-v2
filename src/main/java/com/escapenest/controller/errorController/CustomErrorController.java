package com.escapenest.controller.errorController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {

    @GetMapping("/error-401")
    public String handleError401() {
        return "error/401";
    }

    @GetMapping("/error-403")
    public String handleError403() {
        return "error/403";
    }
}