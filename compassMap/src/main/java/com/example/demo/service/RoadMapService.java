package com.example.demo.service;

import com.example.demo.model.RoadMap;
import com.example.demo.repository.RoadMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoadMapService {

    @Autowired
    private RoadMapRepository roadMapRepository;

    public Iterable<RoadMap> getAllRoadMaps() {
        return roadMapRepository.findAll();
    }

    public RoadMap createRoadMap(RoadMap roadMap) {
        RoadMap.setId(UUID.randomUUID().toString());
        RoadMap createdRoadMap = roadMapRepository.save(roadMap);
        System.out.println("RoadMap created: " + createdRoadMap);
        return createdRoadMap;
    }

    public RoadMap getRoadMapById(String id) {
        Optional<RoadMap> foundRoadMap = roadMapRepository.findById(id);
        return foundRoadMap.orElse(null);
    }

    public void deleteRoadMap(String id) {roadMapRepository.deleteById(id);}

    public void deleteAllRoadMaps(){roadMapRepository.deleteAll();}

    public long countRoadMaps() { return roadMapRepository.count();}

    }

// to do: createRoadMap, getAllRoadMaps, getRoadMapById,
    // to do: deleteRoadMapById, deleteAllRoadMaps, countRoadMaps



