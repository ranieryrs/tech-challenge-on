package com.techchallenge.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.Charset;
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
    @DeleteMapping(value="/{requestPeopleId}",produces = "application/json")
    public ResponseEntity delete(@PathVariable int requestPeopleId ) throws IOException, JSONException {
        final Resource resource = resourceLoader.getResource(PEOPLE_JSON);
        final Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        final JSONArray jsonArray = new JSONArray(FileCopyUtils.copyToString(reader));
        for(int i=0;i<jsonArray.length();i++){
            final JSONObject jsonData = new JSONObject(jsonArray.get(i).toString());
            if(Integer.parseInt(jsonData.get("id").toString()) == requestPeopleId){
                jsonArray.remove(i);
                final OutputStream tes = new FileOutputStream(resource.getFile()) ;
                tes.write(jsonArray.toString().getBytes(Charset.forName("UTF-8")));
                tes.close();
            }
        }
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value="/{requestPeopleId}",produces = "application/json")
    public ResponseEntity edit(@RequestBody String requestEditPeople,@PathVariable int requestPeopleId) throws IOException, JSONException {
        final JSONObject requestEditPeopleJson = new JSONObject(requestEditPeople);
        final Resource resource = resourceLoader.getResource(PEOPLE_JSON);
        final Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        final JSONArray jsonArray = new JSONArray(FileCopyUtils.copyToString(reader));
        JSONObject response=null;
        for(int i=0;i<jsonArray.length();i++){
            final JSONObject jsonData = new JSONObject(jsonArray.get(i).toString());
            if(Integer.parseInt(jsonData.get("id").toString()) == requestPeopleId){
                jsonArray.put(i,requestEditPeopleJson);
                final OutputStream tes = new FileOutputStream(resource.getFile()) ;
                tes.write(jsonArray.toString().getBytes(Charset.forName("UTF-8")));
                tes.close();
                response =  new JSONObject(jsonArray.get(i).toString());
            }
        }
        return ResponseEntity.ok(response.toString());
    }

}

