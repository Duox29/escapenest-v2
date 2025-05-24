package com.escapenest.controller.adminController;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Support;
import com.escapenest.model.enums.SupportType;
import com.escapenest.service.SupportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/support")
public class SupportController {
    private final SupportService supportService ;

    @GetMapping()
    public String viewHomePage (Model model){
        model.addAttribute("supportList" , supportService.getAllSupport());
        return "admin/support/index";
    }
    @GetMapping("/create")
    public String viewCreateSupportPage(Model model){
        model.addAttribute("supportTypes" , SupportType.values());
        return "admin/support/create";
    }
    @GetMapping("/{id}/detail")
    public String viewDetailSupportPage(Model model, @PathVariable Integer id){
        Support support  = supportService.getSupportById(id);
        model.addAttribute("support" , support);
        model.addAttribute("supportTypes" , SupportType.values());
        return "admin/support/detail";
    }
}
