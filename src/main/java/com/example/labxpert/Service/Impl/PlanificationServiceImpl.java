package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.PlanificationDto;
import com.example.labxpert.Dtos.UserDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Planification;
import com.example.labxpert.Model.User;
import com.example.labxpert.Repository.IAnalyseRepository;
import com.example.labxpert.Repository.IPlanificationRepository;
import com.example.labxpert.Repository.IUserRepository;
import com.example.labxpert.Service.IAnalyseService;
import com.example.labxpert.Service.IPlanificationService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PlanificationServiceImpl implements IPlanificationService {

    private final IPlanificationRepository iPlanificationRepository;
    private final IAnalyseRepository iAnalyseRepository;
    private final IUserRepository iUserRepository;
    private final ModelMapper modelMapper;

    @Override
    public PlanificationDto add(PlanificationDto planificationDto)
    {
        validation(planificationDto);
        System.out.println(planificationDto);
        Analyse analyseExist = iAnalyseRepository.findByIdAndDeletedFalse(planificationDto.getAnalyse().getId()).orElseThrow(() -> new NotFoundException("Analyse not found with this id : " + planificationDto.getAnalyse().getId()));
        User userExist = iUserRepository.findByIdAndDeletedFalse(planificationDto.getTechnicien().getId()).orElseThrow(() -> new NotFoundException("User not found with this id : " + planificationDto.getTechnicien().getId()));
        planificationDto.setTechnicien(modelMapper.map(userExist, UserDto.class));
        planificationDto.setAnalyse(modelMapper.map(analyseExist, AnalyseDto.class));

        Planification planification = iPlanificationRepository.save(modelMapper.map(planificationDto, Planification.class));
        System.out.println(planification);
        return modelMapper.map(planification, PlanificationDto.class);
    }

    @Override
    public PlanificationDto update(Long id, PlanificationDto planificationDto)
    {
        validation(planificationDto);

        Planification planificationExist = iPlanificationRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Planification not found with id : " + id));
        Analyse analyseExist = iAnalyseRepository.findByIdAndDeletedFalse(planificationDto.getAnalyse().getId()).orElseThrow(() -> new NotFoundException("Analyse not found with this id : " + planificationDto.getAnalyse().getId()));
        User userExist = iUserRepository.findByIdAndDeletedFalse(planificationDto.getTechnicien().getId()).orElseThrow(() -> new NotFoundException("User not found with this id : " + planificationDto.getTechnicien().getId()));

        planificationExist.setAnalyse(analyseExist);
        planificationExist.setTechnicien(userExist);
        planificationExist.setDateDebut(planificationDto.getDateDebut());
        planificationExist.setDateFin(planificationDto.getDateFin());

        Planification planificationUpdated = iPlanificationRepository.save(planificationExist);
        return modelMapper.map(planificationUpdated, PlanificationDto.class);
    }

    @Override
    public void delete(Long id)
    {
        Planification planificationExist = iPlanificationRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Planification not found with id : " + id));
        planificationExist.setDeleted(true);
        iPlanificationRepository.save(planificationExist);
    }

    @Override
    public List<PlanificationDto> getAll()
    {
        List<Planification> planifications = iPlanificationRepository.findByDeletedFalse();
        return planifications.stream()
                .map(planification -> modelMapper.map(planification, PlanificationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PlanificationDto getById(Long id)
    {
        Planification planificationExist = iPlanificationRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Planification not found with id : " + id));
        return modelMapper.map(planificationExist, PlanificationDto.class);
    }

    @Override
    public void validation(PlanificationDto planificationDto)
    {
        if(planificationDto == null)
        {
            throw new ValidationException("Les données du planification sont nécessaires..");
        }

        if(planificationDto.getTechnicien() == null)
        {
            throw new ValidationException("Id de technicien est requise.");
        }

        if(planificationDto.getAnalyse() == null)
        {
            throw new ValidationException("Id de analyse est requise.");
        }

        if(planificationDto.getDateDebut() == null)
        {
            throw new ValidationException("Date début est requise.");
        }

        if(planificationDto.getDateFin() == null)
        {
            throw new ValidationException("Date fin est requise.");
        }
    }
}
