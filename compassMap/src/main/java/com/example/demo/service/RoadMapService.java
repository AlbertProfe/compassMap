package com.example.demo.service;

import com.example.demo.model.RoadMap;
import com.example.demo.repository.RoadMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoadMapService {

    // we are using here DEPENDENCE INJECTION
    @Autowired
    private RoadMapRepository roadMapRepository;

    public Iterable<RoadMap> getAllRoadMaps() {
        return roadMapRepository.findAll();
    }

    public RoadMap createRoadMap (RoadMap roadMap){
        // SET ID roadMap to UUID
        roadMap.setId(UUID.randomUUID().toString());

        // call repository to use SAVE operation to save data to h2 db
        RoadMap createdRoadMap = roadMapRepository.save(roadMap);
        //System.out.println("Service created roadMap: " +  createdRoadMap);
        return createdRoadMap;
    }

    public void deleteRoadMap (String id){
      roadMapRepository.deleteById(id);
    }

    public RoadMap getRoadMapById(String id){
        Optional<RoadMap> foundRoadMap = roadMapRepository.findById(id);
        return foundRoadMap.orElse(null);
    }

    public Iterable<RoadMap> saveAll(Iterable<RoadMap> roadMaps) {
        // SET ID roadMap to UUID, for all the objects
        for (RoadMap roadMap : roadMaps) {
            roadMap.setId(UUID.randomUUID().toString());
        }
        return roadMapRepository.saveAll(roadMaps);
    }

    public void deleteAllRoadMaps() { roadMapRepository.deleteAll();}

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
        if (incoming.getSteps() != 0) {
            existing.setSteps(incoming.getSteps());
        }
        if (incoming.isCompleted()) {
            existing.setCompleted(incoming.isCompleted());
        }
        return roadMapRepository.save(existing);
    }

}
