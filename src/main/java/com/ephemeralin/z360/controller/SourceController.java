package com.ephemeralin.z360.controller;

import com.ephemeralin.z360.grabber.IGrabber;
import com.ephemeralin.z360.grabber.VestiGrabber;
import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.service.ItemService;
import com.ephemeralin.z360.stemming.Stemmer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

/**
 * The Source controller.
 */
@Controller
@Log4j2
public class SourceController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/main")
    public ModelAndView showMain(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("main");

        String userName = "";
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            userName = principal.getName();
        }
        boolean isAdminValue = request.isUserInRole("ROLE_ADMIN");

        mv.addObject("loginValue", userName);
        mv.addObject("isAdminValue", isAdminValue);
        return mv;
    }

    @GetMapping(value = "/vesti")
    public ModelAndView showVesti(HttpServletRequest request) {
        List<Item> itemList = itemService.findAll();

        ModelAndView mv = new ModelAndView("vesti");
        mv.addObject("itemList", itemList);

        String userName = "";
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            userName = principal.getName();
        }
        boolean isAdminValue = request.isUserInRole("ROLE_ADMIN");

        mv.addObject("loginValue", userName);
        mv.addObject("isAdminValue", isAdminValue);
        return mv;
    }

    @PostMapping(value = "/update_vesti", params = {"source"})
    public String updateData() {
        IGrabber grabber = new VestiGrabber();
        final List<Item> items = grabber.getData();
        for (Item item : items) {
            System.out.println(item);
            if (itemService.findByTitle(item.getTitle()) == null) {
                int id = itemService.create(item);
                item.setId(id);
            }
//            try {
//                System.out.println(Stemmer.getInstance().stem(item.getFullText()));
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }

//            break;
        }

        return "redirect:/vesti";
    }


}
