package com.escapenest.controller.adminController;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.User;
import com.escapenest.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserManagementController {

       private final AuthService authService;
    @GetMapping
    public String viewUserManagementPage(Model model){
//        List<UserDelete> userDeletes = userDeleteRepository.findAll().stream()
//                .sorted((ht1, ht2)-> ht2.getCreatedAt().compareTo(ht1.getCreatedAt()))
//                .toList();
                model.addAttribute("listUser",authService.getAllUser());
        return "admin/user/index";
    }
    @GetMapping("/detail/{id}")
    public String viewDetailUser(Model model, @PathVariable Integer id){
        User user = authService.getUserById(id);
        model.addAttribute("user",user);
        int currentYear = LocalDate.now().getYear();
        model.addAttribute("currentYear",currentYear);

        return "admin/user/detail";
    } @GetMapping("create")
    public String viewCreateUser(Model model){
//        List<UserDelete> userDeletes = userDeleteRepository.findAll().stream()
//                .sorted((ht1, ht2)-> ht2.getCreatedAt().compareTo(ht1.getCreatedAt()))
//                .toList();
        model.addAttribute("listUser",authService.getAllUser());
        return "admin/user/create";
    }

}
