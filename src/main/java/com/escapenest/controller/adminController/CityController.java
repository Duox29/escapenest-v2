package com.escapenest.controller.adminController;

import lombok.RequiredArgsConstructor;
import com.escapenest.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    @GetMapping()
    public String viewPageCity(Model model){
        model.addAttribute("cityList", cityService.getAllCity());
        return "/admin/city/index" ;
    }
}
