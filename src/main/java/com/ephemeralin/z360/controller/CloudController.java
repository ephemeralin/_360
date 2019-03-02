package com.ephemeralin.z360.controller;

import com.ephemeralin.z360.cloud.CloudType;
import com.ephemeralin.z360.cloud.WordCloudProducer;
import com.ephemeralin.z360.model.KeywordSet;
import com.ephemeralin.z360.model.Source;
import com.ephemeralin.z360.service.KeywordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * The Cloud controller.
 */
@Controller
@Log4j2
public class CloudController {

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
                } else {
                    super.setAsText(text);
                }
            }
        };
        binder.registerCustomEditor(LocalDate.class, dateEditor);
    }

    @GetMapping(value = "/cloud")
    public String showClouds(
            @RequestParam(name = "sourceFirst", defaultValue = "vesti") String sourceNameFirst,
            @RequestParam(name = "sourceSecond", defaultValue = "meduza") String sourceNameSecond,
            @RequestParam(name = "cloudType", defaultValue = "polarityCloud") String cloudTypeName,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate", required=false, defaultValue="startOfDay") LocalDate startDate,
            Model model) {

        Source sourceFirst = Source.valueOf(sourceNameFirst);
        Source sourceSecond = Source.valueOf(sourceNameSecond);
        CloudType cloudType = CloudType.valueOf(cloudTypeName);

        KeywordSet keysFirst = keywordService.findByDate(startDate, sourceFirst);
        KeywordSet keysSecond = keywordService.findByDate(startDate, sourceSecond);

        String cloud1 = "";
        String cloud2 = "";
        WordCloudProducer cloudProducer = new WordCloudProducer();
        if (keysFirst != null && keysSecond != null) {
            List<String> clouds = cloudProducer.createClouds(keysFirst, keysSecond, cloudType);
            cloud1 = clouds.get(0);
            cloud2 = clouds.get(1);
        }
        model.addAttribute("startDate", startDate);
        model.addAttribute("cloudTypesList", Arrays.asList(CloudType.values()));
        model.addAttribute("sourcesList", Arrays.asList(Source.values()));
        model.addAttribute("sourceFirst", sourceFirst);
        model.addAttribute("sourceSecond", sourceSecond);
        model.addAttribute("cloudType", cloudType);
        model.addAttribute("cloudImage1", cloud1);
        model.addAttribute("cloudImage2", cloud2);
        return "cloud";
    }

    @PostMapping(value = "/cloud", params = "show-cloud")
    public void showCloudsByPeriod(
            @RequestParam(name = "sourceFirst") String sourceNameFirst,
            @RequestParam(name = "sourceSecond") String sourceNameSecond,
            @RequestParam(name = "cloudType") String cloudTypeName,
            @DateTimeFormat(pattern="dd.MM.yyyy") @RequestParam(name="startDate") LocalDate startDate,
            Model model) {

        showClouds(sourceNameFirst, sourceNameSecond, cloudTypeName, startDate, model);
    }
}
