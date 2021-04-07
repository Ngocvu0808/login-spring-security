package com.secury.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/403")
    public String forbiden() {
        return "error/403";
    }
}
