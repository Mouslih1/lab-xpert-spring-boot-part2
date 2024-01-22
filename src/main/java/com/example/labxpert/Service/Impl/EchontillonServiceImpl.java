package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.EchontillonDto;
import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Echontillon;
import com.example.labxpert.Model.Patient;
import com.example.labxpert.Repository.IAnalyseRepository;
import com.example.labxpert.Repository.IEchontillonRepository;
import com.example.labxpert.Repository.IPatientRepository;
import com.example.labxpert.Service.IEchontillonService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EchontillonServiceImpl implements IEchontillonService {

    private final IEchontillonRepository iEchontillonRepository;
    private final IPatientRepository iPatientRepository;
    private final IAnalyseRepository iAnalyseRepository;
    private final ModelMapper modelMapper;

    @Override
    public EchontillonDto add(EchontillonDto echontillonDto)
    {
        validation(echontillonDto);

        List<AnalyseDto> analyses = new ArrayList<>();

        for (AnalyseDto analyse : echontillonDto.getAnalyses())
        {
            //TODO: CHECK IF ANALYSES EFFECT TO ECHONTILLON EXIST IN SYSTEM
            Analyse analyseExist = iAnalyseRepository.findByIdAndDeletedFalse(analyse.getId()).orElseThrow(() -> new NotFoundException("Analyse not found with this id : " + analyse.getId()));
            analyses.add(modelMapper.map(analyseExist, AnalyseDto.class));
        }

        //TODO: ADD ANALYSES AFFECTER TO ECHONTILLON FOR RETURN OBJECT COMPLET NOT NULL
        echontillonDto.setAnalyses(analyses);

        //TODO: CHECK IF PATIENT EXIST IN SYSTEM
        Patient patientExist = iPatientRepository.findByIdAndDeletedFalse(echontillonDto.getPatient().getId()).orElseThrow(() -> new NotFoundException("Patient not found with this id : " + echontillonDto.getPatient().getId()));

        echontillonDto.setCodeEchontillon("ECHONTILLON-" + UUID.randomUUID()+ "-" + patientExist.getNom().toUpperCase() + "-" + patientExist.getPrenom().toUpperCase());
        echontillonDto.setPatient(modelMapper.map(patientExist, PatientDto.class));

        Echontillon echontillon = iEchontillonRepository.save(modelMapper.map(echontillonDto, Echontillon.class));
        return modelMapper.map(echontillon, EchontillonDto.class);
    }

    @Override
    public EchontillonDto update(Long id, EchontillonDto echontillonDto)
    {
        validation(echontillonDto);
        Echontillon echontillonExist = iEchontillonRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Echontillon not found with this id : " + id));
        Patient patientExist = iPatientRepository.findByIdAndDeletedFalse(echontillonDto.getPatient().getId()).orElseThrow(() -> new NotFoundException("Patient not found with this id : " + id));

        List<Analyse> analyses = new ArrayList<>();

        for (AnalyseDto analyse : echontillonDto.getAnalyses())
        {
            Analyse analyseExist = iAnalyseRepository.findByIdAndDeletedFalse(analyse.getId()).orElseThrow(() -> new NotFoundException("Analyse not found with this id : " + analyse.getId()));
            analyses.add(analyseExist);
        }

        echontillonExist.setPatient(patientExist);
        echontillonExist.setDate_p(echontillonDto.getDate_p());
        echontillonExist.setAnalyses(analyses);
        Echontillon echontillonUpdated = iEchontillonRepository.save(echontillonExist);
        return modelMapper.map(echontillonUpdated, EchontillonDto.class);
    }

    @Override
    public void delete(Long id)
    {
        Echontillon echontillon = iEchontillonRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Echontillon not found with this id : " + id));
        echontillon.setDeleted(true);
        iEchontillonRepository.save(echontillon);
    }

    @Override
    public List<EchontillonDto> getAll()
    {
        List<Echontillon> echontillons = iEchontillonRepository.findByDeletedFalse();
        return echontillons
                .stream()
                .map(echontillon -> modelMapper.map(echontillon, EchontillonDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EchontillonDto getById(Long id)
    {
        Echontillon echontillon = iEchontillonRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Echontillon not found with this id : " + id));
        return modelMapper.map(echontillon, EchontillonDto.class);
    }

    @Override
    public EchontillonDto getByCodeEchontillon(String codeEchontillon)
    {
        Echontillon echontillon = iEchontillonRepository.findByCodeEchontillonAndDeletedFalse(codeEchontillon).orElseThrow(() -> new NotFoundException("Echontillon not found with this code :" + codeEchontillon));
        return modelMapper.map(echontillon, EchontillonDto.class);
    }

    @Override
    public void validation(EchontillonDto echontillonDto)
    {
        if (echontillonDto == null) {
            throw new ValidationException("Les données de l'échantillon sont nécessaires.");
        }

        if (echontillonDto.getPatient().getId() == null) {
            throw new ValidationException("L'ID du patient est requise.");
        }

        if (echontillonDto.getDate_p() == null) {
            throw new ValidationException("La date de prélèvement est requise.");
        }

        if(echontillonDto.getAnalyses() == null)
        {
            throw new ValidationException("Analyses est requise.");
        }
    }
}
