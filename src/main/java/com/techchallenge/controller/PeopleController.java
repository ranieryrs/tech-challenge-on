package com.techchallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final static String PEOPLE_JSON = "classpath:people.json";

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(produces = "application/json")
    public ResponseEntity get() throws IOException {
        final Resource resource = resourceLoader.getResource(PEOPLE_JSON);
        final Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return ResponseEntity.ok(FileCopyUtils.copyToString(reader));
    }
}
