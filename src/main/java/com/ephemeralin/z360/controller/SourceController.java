package com.ephemeralin.z360.controller;

import com.ephemeralin.z360.grabber.IGrabber;
import com.ephemeralin.z360.grabber.VestiGrabber;
import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.service.ItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * The Source controller.
 */
@Controller
@Log4j2
public class SourceController {

    @Autowired
    private ItemService itemServiceVesti;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        final CustomDateEditor dateEditor = new CustomDateEditor(df, true) {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if("startOfDay".equals(text)) {
                    setValue(LocalDateTime.now().toLocalDate());
                } else if("endOfDay".equals(text)) {
                    setValue(LocalDateTime.now().toLocalDate());
                } else {
                    super.setAsText(text);
                }
            }
        };
        binder.registerCustomEditor(LocalDate.class, dateEditor);
    }

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
    public String showVesti(
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate", required=false, defaultValue="startOfDay") LocalDate startDate,
            Model model) {

        List<Item> itemList = itemServiceVesti.findItemsByPubDateBetween(startDate.atStartOfDay(), startDate.atTime(LocalTime.MAX));

        model.addAttribute("itemList", itemList);
        model.addAttribute("startDate", startDate);

        return "vesti";
    }

    @PostMapping(value = "/vesti", params = "update-news")
    public String updateVesti(
            @RequestParam(name = "source", required = false) String source,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        IGrabber grabber = new VestiGrabber();
        final List<Item> items = grabber.getData();
        for (Item item : items) {
            if (itemServiceVesti.findByTitle(item.getTitle()) == null) {
                int id = itemServiceVesti.create(item);
                item.setId(id);
            }
        }

        return "redirect:/vesti";
    }

    @PostMapping(value = "/vesti", params = "show-news")
    public String showVestiByPeriod(
            @RequestParam(name = "source", required = false) String source,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        List<Item> itemList = itemServiceVesti.findItemsByPubDateBetween(startDate.atStartOfDay(), startDate.atTime(LocalTime.MAX));

        model.addAttribute("itemList", itemList);
        model.addAttribute("startDate", startDate);
        return "vesti";
    }

    @PostMapping(value = "/vesti", params = "save-keys")
    public String saveKeysVesti(
            @RequestParam(name = "source", required = false) String source,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        return "redirect:/vesti";
    }

}
