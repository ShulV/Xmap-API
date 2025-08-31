package com.xmap_api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping({"", "/"})
    public String redirectToSpots() {
        return "redirect:/spots?pageNumber=0&pageSize=10&viewMode=YMAP";
    }
}