package com.example.demo.controller;


import com.example.demo.service.RoadMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roadmaps")
public class RoadMapRestController {

    @Autowired
    private RoadMapService roadMapService;

    @PostMapping("/create")
    public void createRoadMap() {
        // to do
    }

    @GetMapping("/all")
    public void getAll(){
        // to do
    }

    @DeleteMapping("/byId")
    public void deleteById(){
        // to do
    }

    @PutMapping("/byId")
    public void updateById(){
        // to do
    }
}
