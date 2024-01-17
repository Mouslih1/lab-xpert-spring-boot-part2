package com.example.labxpert.Service.Impl;

import com.example.labxpert.Model.Planification;
import com.example.labxpert.Repository.IPlanificationRepository;
import com.example.labxpert.Service.IPlanificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class PlanificationServiceImpl implements IPlanificationService {

    private IPlanificationRepository iPlanificationRepository;


    @Override
    public Planification add(Planification planification) {
        return iPlanificationRepository.save(planification);
    }

    @Override
    public Planification update(Planification planification) {
        return iPlanificationRepository.save(planification);
    }

    @Override
    public void delete(Long id) {
        iPlanificationRepository.deleteById(id);
    }

    @Override
    public List<Planification> getAll() {
        return iPlanificationRepository.findAll();
    }

    @Override
    public Planification getById(Long id) {
        return iPlanificationRepository.findById(id).orElse(null);
    }
}
