package com.personal_organization.infrastructure.web.api;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class RedirectApi {

    @GetMapping("/")
    public String swagger() {
        return "redirect:swagger-ui.html";
    }

}