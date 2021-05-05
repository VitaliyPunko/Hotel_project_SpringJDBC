package com.punko.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Goto apartments list page.
     *
     * @return redirect to /apartments
     */
    @GetMapping(value = "/")
    public final String apartments() {
        return "redirect:/apartments";
    }
}
