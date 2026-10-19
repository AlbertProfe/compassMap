package com.example.demo.service;

import com.example.demo.model.RoadMap;
import com.example.demo.repository.RoadMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoadMapService {

    // we are using here DEPENDENCE INJECTION
    @Autowired
    private RoadMapRepository roadMapRepository;

    public Iterable<RoadMap> getAllRoadMaps() {
        return roadMapRepository.findAll();
    }

    public List<RoadMap> getAllRoadMapsAsList() {
        List<RoadMap> roadMaps = new ArrayList<>();
        roadMapRepository.findAll().forEach(roadMaps::add);
        return roadMaps;
    }

    public RoadMap createRoadMap(RoadMap roadMap) {
        // the id is generated automatically by @GeneratedValue(GenerationType.UUID)
        // so we do NOT set it here, otherwise Hibernate tries to merge a detached row
        RoadMap createdRoadMap = roadMapRepository.save(roadMap);
        System.out.println("Service created roadMap: " + createdRoadMap);
        return createdRoadMap;
    }

    public Iterable<RoadMap> saveAll(Iterable<RoadMap> roadMaps) {
        return roadMapRepository.saveAll(roadMaps);
    }

    public RoadMap getRoadMapById(String id) {
        Optional<RoadMap> foundRoadMap = roadMapRepository.findById(id);
        return foundRoadMap.orElse(null);
    }

    public void deleteRoadMap(String id) {
        roadMapRepository.deleteById(id);
    }

    public void deleteAllRoadMaps() {
        roadMapRepository.deleteAll();
    }

    public long countRoadMaps() {
        return roadMapRepository.count();
    }

    public RoadMap updateRoadMap(RoadMap incoming) {
        if (incoming.getId() == null) {
            return null;
        }
        RoadMap existing = getRoadMapById(incoming.getId());
        if (existing == null) {
            return null;
        }
        // merge only non-null fields from incoming (partial update semantics)
        if (incoming.getName() != null) {
            existing.setName(incoming.getName());
        }
        if (incoming.getDescription() != null) {
            existing.setDescription(incoming.getDescription());
        }
        if (incoming.getSteps() != null && incoming.getSteps() > 0) {
            existing.setSteps(incoming.getSteps());
        }
        if (incoming.isCompleted() != null) {
            existing.setCompleted(incoming.isCompleted());
        }
        return roadMapRepository.save(existing);
    }
}
