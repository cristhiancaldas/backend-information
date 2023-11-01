package com.app.information.controller;

import com.app.information.service.InformationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @GetMapping("/persons")
    public JsonNode getRandomPersons() {
        return informationService.persons();
    }

    @GetMapping("/foods")
    public JsonNode getRandomFoods() {
        return informationService.foods();
    }

    @GetMapping("/books")
    public JsonNode getRandomBook() {
        return informationService.books();
    }
}
