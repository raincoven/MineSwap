package com.rcn.mineswap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        return "index";
    }
}
