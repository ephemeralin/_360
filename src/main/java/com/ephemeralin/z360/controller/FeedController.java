package com.ephemeralin.z360.controller;

import com.ephemeralin.z360.grabber.GrabberFactory;
import com.ephemeralin.z360.grabber.IGrabber;
import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.model.KeywordSet;
import com.ephemeralin.z360.model.Source;
import com.ephemeralin.z360.service.ItemService;
import com.ephemeralin.z360.service.KeywordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

/**
 * The Feed controller.
 */
@Controller
@Slf4j
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
    public String showFeed(
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate", required=false, defaultValue="startOfDay") LocalDate startDate,
            @RequestParam(name = "sourceName", required = true) String sourceName,
            Model model) {

        model.addAttribute("sourceName", sourceName);
        model.addAttribute("startDate", startDate);
        return "feed";
    }

    @PostMapping(value = "/feed", params = "show-news")
    public String showFeedByPeriodInitial(
            @RequestParam(name = "sourceName", required = true) String sourceName,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        model.addAttribute("sourceName", sourceName);
        model.addAttribute("startDate", startDate);
        return "feed";
    }

    @PostMapping(value = "/feed-load-news")
    @ResponseBody
    public String showFeedByPeriodExp(
            @RequestParam(name = "sourceName", required = true) String sourceName,
            @DateTimeFormat(pattern = "dd.MM.yyyy") @RequestParam(name = "startDate") LocalDate startDate) {

        log.info("-- " + sourceName);
        log.info("-- " + startDate);

        Source source = Source.valueOf(sourceName);
        List<Item> itemList = itemService.findItemsByPubDateBetween(startDate.atStartOfDay(), startDate.atTime(LocalTime.MAX), source);
        KeywordSet keys = keywordService.findByDate(startDate, source);

        String testData = "{\"itemList\":[]}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ArrayNode arrayItems = objectMapper.valueToTree(itemList);

        ObjectNode nodeKeys = objectMapper.valueToTree(keys);

        ObjectNode parentNode = objectMapper.createObjectNode();
        parentNode.set("itemList", arrayItems);
        parentNode.set("keys", nodeKeys);

        try {
            testData = objectMapper.writeValueAsString(parentNode);
        } catch (JsonProcessingException e) {
            log.error("Error while generating json response", e);
        }

        log.info("--- " + testData);
        return testData;
    }

    @PostMapping(value = "/feed", params = "update-news")
    public void updateFeedOld(
            @RequestParam(name = "sourceName", required = true) String sourceName,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {
        Source source = Source.valueOf(sourceName);
        IGrabber grabber = new GrabberFactory().getGrabber(Source.valueOf(sourceName));
        List<Item> items = grabber.getData();
        for (Item item : items) {
            if (itemService.findByTitle(item.getTitle(), source) == null) {
                long id = itemService.create(item);
                item.setId(id);
            }
        }
        showFeedByPeriodInitial(sourceName, startDate, model);
    }

    @PostMapping(value = "/feed", params = "save-keys")
    public void saveKeys(
            @RequestParam(name = "sourceName") String sourceName,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "words", required = false) String words,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        Source source = Source.valueOf(sourceName);
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
        showFeedByPeriodInitial(sourceName, startDate, model);
    }
}
