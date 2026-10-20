package com.example.demo.controller;


import com.example.demo.model.RoadMap;
import com.example.demo.service.RoadMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roadmaps")
public class RoadMapRestController {

    @Autowired
    private RoadMapService roadMapService;

    @PostMapping("/create")
    public RoadMap createRoadMap(@RequestBody RoadMap roadMap) {
        RoadMap newRoadMap = roadMapService.createRoadMap(roadMap);
        System.out.println("New RoadMap: " + newRoadMap.toString());
        return newRoadMap;
        // to do
    }

    @GetMapping("/all")
    public Iterable<RoadMap> getAllRoadMap(){
        Iterable<RoadMap> roadMaps = roadMapService.getAllRoadMaps();
        System.out.println("All RoadMaps: " + roadMaps.toString());
        return roadMaps;
        // to do
        }
    @GetMapping("/count")
    public long countRoadMaps(){
        long count = roadMapService.countRoadMaps();
        System.out.println("Count RoadMaps: " + count);
        return count;
    }

    @DeleteMapping("/byId")
    public RoadMap deleteRoadMapById(@RequestParam String id){
        RoadMap foundRoadMap = roadMapService.getRoadMapById(id);
        if (foundRoadMap != null) {
            roadMapService.deleteRoadMap(id);
            }
        // to do
        return foundRoadMap;
    }

    @PutMapping("/byId")
    public RoadMap updateById(@RequestBody RoadMap roadMap){
        if (roadMap.getId()!=null){
            return  roadMapService.createRoadMap(roadMap);
        } else {
            return null;
        }
        // to do
    }
    public void deleteAllRoadMaps(){ roadMapService.deleteAllRoadMaps();}
}
