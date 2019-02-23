package com.ephemeralin.z360.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * The Cloud controller.
 */
@Controller
@Log4j2
public class CloudController {

    @GetMapping(value = "/cloud")
    public ModelAndView showMain(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("cloud");

        mv.addObject("loginValue", "");
        mv.addObject("isAdminValue", "");
        return mv;
    }
}
