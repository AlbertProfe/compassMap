package com.example.demo.controller;

import com.example.demo.model.RoadMap;
import com.example.demo.service.RoadMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// REST Controller is a annotation that marks the class as a controller
// that is a component that handles HTTP requests
@RestController
// RequestMapping is a annotation that maps HTTP requests to handler methods
// it is used to map the HTTP requests to the methods in the controller
@RequestMapping("/api/v1/roadmaps")
public class RoadMapRestController {

    @Autowired
    private RoadMapService roadMapService;

    //----------------------------- CREATE -----------------------------
    @PostMapping("/create")
    public RoadMap createRoadMap(@RequestBody RoadMap roadMap) {
        RoadMap newRoadMap = roadMapService.createRoadMap(roadMap);
        System.out.println("Service createRoadMap: " + newRoadMap);
        return newRoadMap;
    }

    @PostMapping("/createAll")
    public Iterable<RoadMap> createAllRoadMaps(@RequestBody Iterable<RoadMap> roadMaps) {
        Iterable<RoadMap> newRoadMaps = roadMapService.saveAll(roadMaps);
        System.out.println("Service createAllRoadMaps: " + newRoadMaps);
        return newRoadMaps;
    }

    //----------------------------- UPDATE -----------------------------
    @PutMapping("/update")
    public RoadMap updateRoadMap(@RequestBody RoadMap roadMap) {
        if (roadMap.getId() == null || roadMapService.getRoadMapById(roadMap.getId()) == null) {
            System.out.println("Service updateRoadMap: not found");
            return null;
        }
        RoadMap updatedRoadMap = roadMapService.updateRoadMap(roadMap);
        System.out.println("Service updateRoadMap: " + updatedRoadMap);
        return updatedRoadMap;
    }

    //----------------------------- GET -----------------------------
    @GetMapping("/all")
    public Iterable<RoadMap> getAllRoadsMaps() {
        // call service to get all roadmaps and return them
        Iterable<RoadMap> roadMaps =  roadMapService.getAllRoadMaps();
        System.out.println("Service getAllRoadMaps: " + roadMaps);
        return roadMaps;
    }

    @GetMapping("/byId")
    public RoadMap getRoadMapById(@RequestParam String id) {
        RoadMap roadMap = roadMapService.getRoadMapById(id);
        if (roadMap == null) {
            System.out.println("Service getRoadMapById: not found");
            return null;
        }
        System.out.println("Service getRoadMapById: " + roadMap);
        return roadMap;
    }

    @GetMapping("/count")
    public long countRoadMaps() {
        long count = roadMapService.countRoadMaps();
        System.out.println("Service countRoadMaps: " + count);
        return count;
    }

    //----------------------------- DELETE -----------------------------
    @DeleteMapping("/byId")
    public RoadMap deleteById(@RequestParam String id) {
        RoadMap foundRoadMap = roadMapService.getRoadMapById(id);
        if (foundRoadMap == null) {
            System.out.println("Service deleteById: not found");
            return null;
        }
        roadMapService.deleteRoadMap(id);
        System.out.println("Service deleteById: " + foundRoadMap);
        return foundRoadMap;
    }

    @DeleteMapping("/all")
    public void deleteAllRoadMaps() { roadMapService.deleteAllRoadMaps(); }
}
