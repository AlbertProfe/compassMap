package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.RoadMap;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoadMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoadMapService {

    // we are using here DEPENDENCE INJECTION
    @Autowired
    private RoadMapRepository roadMapRepository;

    public Iterable<RoadMap> getAllRoadMaps() {
        return RoadMapRepository.findAll();
    }

    public RoadMap createRoadMap (RoadMap roadmap){
        // SET ID roadmap to UUID
        roadmap.setId(UUID.randomUUID().toString());

        // call repository to use SAVE operation to save data to h2 db
        RoadMap createdRoadMap = roadmapRepository.save(roadmap);
        System.out.println("Service created roadmap: " +  createdRoadMap);
        return createdRoadMap;
    }

    public void deleteRoadMap (String id){
      roadmapRepository.deleteById(id);

    }

    public RoadMap getRoadMapById(String id){

        Optional<RoadMap> foundRoadMap = roadmapRepository.findById(id);

        return foundRoadMap.get();
    }

    public void saveAll(ArrayList<RoadMap> roadmaps) {
        roadmapRepository.saveAll(roadmaps);
    }

    public void deleteAllRoadMaps() {
        roadmapRepository.deleteAll();
    }

    public long countRoadMaps() {
        return roadmapRepository.count();
    }

}
