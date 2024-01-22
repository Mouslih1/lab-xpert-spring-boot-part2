package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Patient;
import com.example.labxpert.Repository.IPatientRepository;
import com.example.labxpert.Service.IPatientService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements IPatientService {

    private final IPatientRepository iPatientRepository;
    private final ModelMapper modelMapper;

    @Override
    public PatientDto add(PatientDto patientDto)
    {
        validation(patientDto);
        Patient patientEntity = iPatientRepository.save(modelMapper.map(patientDto,Patient.class));
        return modelMapper.map(patientEntity, PatientDto.class);
    }

    @Override
    public PatientDto update(Long id, PatientDto patientDto)
    {
        validation(patientDto);
        Patient patientExist = iPatientRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Patient not found with this id :" + id));
        patientExist.setNom(patientDto.getNom());
        patientExist.setPrenom(patientDto.getPrenom());
        patientExist.setAge(patientDto.getAge());
        patientExist.setSexe(patientDto.getSexe());
        patientExist.setDateNaissance(patientDto.getDateNaissance());
        patientExist.setVille(patientDto.getVille());
        patientExist.setAddress(patientDto.getAddress());
        patientExist.setTel(patientDto.getTel());
        Patient patientUpdated = iPatientRepository.save(patientExist);
        return modelMapper.map(patientUpdated, PatientDto.class);
    }

    @Override
    public void delete(Long id)
    {
        Patient patient = iPatientRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Patient not found with this id : " + id));
        patient.setDeleted(true);
        iPatientRepository.save(patient);
    }

    @Override
    public List<PatientDto> getAll()
    {
        List<Patient> patients = iPatientRepository.findByDeletedFalse();
        return patients
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDto getById(Long id)
    {
        Patient patient = iPatientRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Patient not found with id : " + id));
        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    public PatientDto getByName(String name) {
        Patient patient = iPatientRepository.findByNomAndDeletedFalse(name).orElseThrow(() -> new NotFoundException("Patient not found with name :" + name));
        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    public void validation(PatientDto patientDto)
    {
        if (patientDto == null) {
            throw new ValidationException("Les données du patient sont nécessaires.");
        }

        if (StringUtils.isBlank(patientDto.getNom())) {
            throw new ValidationException("Le nom est requise.");
        }

        if (StringUtils.isBlank(patientDto.getPrenom())) {
            throw new ValidationException("Le prénom est requise.");
        }

        if (StringUtils.isBlank(patientDto.getAddress())) {
            throw new ValidationException("L'adresse est requise.");
        }

        if (StringUtils.isBlank(patientDto.getTel())) {
            throw new ValidationException("Le numéro de téléphone est requise.");
        }

        if (StringUtils.isBlank(patientDto.getVille())) {
            throw new ValidationException("La ville est requise.");
        }

        if (patientDto.getSexe() == null) {
            throw new javax.validation.ValidationException("Le sexe est requise.");
        }

        if (patientDto.getDateNaissance() == null) {
            throw new ValidationException("La date de naissance est requise.");
        }

        if (patientDto.getAge() <= 0) {
            throw new ValidationException("L'âge doit être supérieur à 0.");
        }
    }
}
