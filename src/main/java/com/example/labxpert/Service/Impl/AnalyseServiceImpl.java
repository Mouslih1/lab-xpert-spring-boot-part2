package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.UserDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Enum.TypeAnalyse;
import com.example.labxpert.Model.User;
import com.example.labxpert.Repository.IAnalyseRepository;
import com.example.labxpert.Repository.IUserRepository;
import com.example.labxpert.Service.IAnalyseService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AnalyseServiceImpl implements IAnalyseService {

    private final IAnalyseRepository iAnalyseRepository;
    private final IUserRepository iUserRepository;
    private final ModelMapper modelMapper;

    @Override
    public AnalyseDto add(AnalyseDto analyseDto)
    {
        validation(analyseDto);

        User userExist = iUserRepository.findByIdAndDeletedFalse(analyseDto.getTechnicienResponsable().getId()).orElseThrow(() -> new NotFoundException("User not found with this id : " + analyseDto.getTechnicienResponsable().getId()));
        analyseDto.setTechnicienResponsable(modelMapper.map(userExist, UserDto.class));

        Analyse analyseSaved = iAnalyseRepository.save(modelMapper.map(analyseDto, Analyse.class));
        return modelMapper.map(analyseSaved, AnalyseDto.class);
    }

    @Override
    public AnalyseDto update(Long id,AnalyseDto analyseDto)
    {
        validation(analyseDto);

        Analyse analyseExist = iAnalyseRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Analyse not found with this id : "+ id));
        User userExist = iUserRepository.findByIdAndDeletedFalse(analyseDto.getTechnicienResponsable().getId()).orElseThrow(() -> new NotFoundException("User not found with this id : "+ id));

        analyseExist.setTypeAnalyse(analyseDto.getTypeAnalyse());
        analyseExist.setCommantaires(analyseDto.getCommantaires());
        analyseExist.setStatusResult(analyseDto.getStatusResult());
        analyseExist.setDateDebut(analyseDto.getDateDebut());
        analyseExist.setDateFin(analyseDto.getDateFin());
        analyseExist.setTechnicienResponsable(userExist);

        Analyse analyseUpdated = iAnalyseRepository.save(analyseExist);
        return modelMapper.map(analyseUpdated, AnalyseDto.class);
    }

    @Override
    public void delete(Long id)
    {
        User userDeleted = iUserRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("User not found with this id : "+ id));
        userDeleted.setDeleted(true);
        iUserRepository.save(userDeleted);
    }

    @Override
    public List<AnalyseDto> getAll()
    {
        List<Analyse> analyses = iAnalyseRepository.findByDeletedFalse();
        return analyses.stream()
                .map(analyse -> modelMapper.map(analyse, AnalyseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AnalyseDto getById(Long id)
    {
        Analyse analyse = iAnalyseRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Analyse not found with this id : " + id));
        return modelMapper.map(analyse, AnalyseDto.class);
    }

    @Override
    public AnalyseDto getByTypeAnalyse(TypeAnalyse typeAnalyse)
    {
        Analyse analyse = iAnalyseRepository.findByTypeAnalyseAndDeletedFalse(typeAnalyse).orElseThrow(() -> new NotFoundException("Analyse not found with this type analyse : " + typeAnalyse));
        return modelMapper.map(analyse, AnalyseDto.class);
    }

    @Override
    public List<AnalyseDto> getByDateBetween(LocalDate dateStart, LocalDate dateEnd)
    {
        List<Analyse> analyses = iAnalyseRepository.findByDateDebutBetween(dateStart, dateEnd);
        return analyses.stream()
                .map(analyse -> modelMapper.map(analyse, AnalyseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void validation(AnalyseDto analyseDto)
    {
        if (analyseDto == null) {
            throw new ValidationException("Les données du analyse sont nécessaires.");
        }

        if (analyseDto.getTechnicienResponsable().getId() == null)
        {
            throw new javax.validation.ValidationException("user id est requise.");
        }

        if (analyseDto.getTypeAnalyse() == null) {
            throw new ValidationException("Le Type analyse est requise.");
        }

        if (StringUtils.isBlank(analyseDto.getCommantaires())) {
            throw new ValidationException("Le commantaire est requise.");
        }

        if (analyseDto.getStatusAnalyse() == null) {
            throw new ValidationException("Le status analyse est requise.");
        }

        if (analyseDto.getDateDebut() == null) {
            throw new ValidationException("La date début est requise.");
        }

        if (analyseDto.getDateFin() == null) {
            throw new ValidationException("La date fin est requise.");
        }
    }
}
