package com.example.demo.service;

import com.example.demo.model.RoadMap;
import com.example.demo.repository.RoadMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoadMapService {

    @Autowired
    private RoadMapRepository roadMapRepository;

    public Iterable<RoadMap> getAllRoadMaps() {
        return roadMapRepository.findAll();
    }

    // to do: createRoadMap, getAllRoadMaps, getRoadMapById,
    // to do: deleteRoadMapById, deleteAllRoadMaps, countRoadMaps


}
