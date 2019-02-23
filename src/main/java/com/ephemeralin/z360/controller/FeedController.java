package com.ephemeralin.z360.controller;

import com.ephemeralin.z360.grabber.IGrabber;
import com.ephemeralin.z360.grabber.MeduzaGrabber;
import com.ephemeralin.z360.grabber.VestiGrabber;
import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.model.KeywordSet;
import com.ephemeralin.z360.model.SOURCE;
import com.ephemeralin.z360.service.ItemService;
import com.ephemeralin.z360.service.KeywordService;
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
 * The Feed controller.
 */
@Controller
@Log4j2
public class FeedController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private KeywordService keywordService;

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

    @GetMapping(value = "/feed")
    public void showFeed(
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate", required=false, defaultValue="startOfDay") LocalDate startDate,
            @RequestParam(name = "sourceName", required = true) String sourceName,
            Model model) {

        showFeedByPeriod(sourceName, startDate, model);
    }

    @PostMapping(value = "/feed", params = "show-news")
    public String showFeedByPeriod(
            @RequestParam(name = "sourceName", required = true) String sourceName,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        SOURCE source = SOURCE.valueOf(sourceName);
        List<Item> itemList = itemService.findItemsByPubDateBetween(startDate.atStartOfDay(), startDate.atTime(LocalTime.MAX), source);
        KeywordSet keys = keywordService.findByDate(startDate, source);

        model.addAttribute("keys", keys);
        model.addAttribute("sourceName", sourceName);
        model.addAttribute("itemList", itemList);
        model.addAttribute("startDate", startDate);
        return "feed";
    }

    @PostMapping(value = "/feed", params = "update-news")
    public void updateFeed(
            @RequestParam(name = "sourceName", required = true) String sourceName,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        List<Item> items = Collections.EMPTY_LIST;

        SOURCE source = SOURCE.valueOf(sourceName);
        if (source == SOURCE.vesti) {
            IGrabber grabber = new VestiGrabber();
            items = grabber.getData();

        } else if(source == SOURCE.meduza) {
            IGrabber grabber = new MeduzaGrabber();
            items = grabber.getData();
        }

        for (Item item : items) {
            if (itemService.findByTitle(item.getTitle(), source) == null) {
                long id = itemService.create(item);
                item.setId(id);
            }
        }
        showFeedByPeriod(sourceName, startDate, model);
    }

    @PostMapping(value = "/feed", params = "save-keys")
    public void saveKeys(
            @RequestParam(name = "sourceName", required = true) String sourceName,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "words", required = false) String words,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        SOURCE source = SOURCE.valueOf(sourceName);
        if (id != null) {
            if (words.isEmpty()) {
                keywordService.delete(id);
            } else {
                KeywordSet keywordSet = new KeywordSet(id);
                keywordSet.setSource(source);
                keywordSet.setCreatedDate(startDate.atStartOfDay());
                keywordSet.setWords(words);
                keywordService.update(keywordSet);
            }
        } else if (!words.isEmpty()) {
            KeywordSet keywordSet = new KeywordSet();
            keywordSet.setSource(source);
            keywordSet.setCreatedDate(startDate.atStartOfDay());
            keywordSet.setWords(words);
            long idNew = keywordService.create(keywordSet);
            keywordSet.setId(idNew);
        }
        showFeedByPeriod(sourceName, startDate, model);
    }
}
