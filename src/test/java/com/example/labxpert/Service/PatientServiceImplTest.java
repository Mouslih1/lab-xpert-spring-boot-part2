package com.example.labxpert.Service;

import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Model.Enum.Sexe;
import com.example.labxpert.Model.Patient;
import com.example.labxpert.Repository.IPatientRepository;
import com.example.labxpert.Service.Impl.PatientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private IPatientRepository iPatientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    @DisplayName("Instantiation object ipatientRepository par .mock & modelMapper & PatientService")
    public void setup()
    {
        iPatientRepository = Mockito.mock(IPatientRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        patientService = new PatientServiceImpl(iPatientRepository, modelMapper);
    }

    @Test
    @DisplayName("patientService_savePatient_ReturnOnePatient")
    public void patientService_savePatient_ReturnOnePatient()
    {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("oussama");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        PatientDto patientDto = new PatientDto();
        patientDto.setNom("marouane");
        patientDto.setPrenom("mouslih");
        patientDto.setTel("0630011246");
        patientDto.setSexe(Sexe.MALE);
        patientDto.setAddress("address 1");
        patientDto.setDateNaissance(LocalDate.of(2001,8,19));
        patientDto.setVille("casablanca");
        patient.setDeleted(false);
        patientDto.setAge(21);

        when(iPatientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);

        PatientDto patientDtoSaved = patientService.add(patientDto);
        System.out.println(patientDtoSaved);
        System.out.println(patient.getNom());
        Assertions.assertThat(patientDtoSaved.getId()).isEqualTo(patient.getId());
        Assertions.assertThat(patientDtoSaved).isNotNull();
    }

    @Test
    @DisplayName("patientService_getPatientByIdPatient_ReturnOnePatient")
    public void patientService_getPatientByIdPatient_ReturnOnePatient()
    {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        when(iPatientRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(patient));

        PatientDto patientDtoSaved = patientService.getById(1L);
        Assertions.assertThat(patientDtoSaved).isNotNull();
        Assertions.assertThat(patientDtoSaved.getId()).isEqualTo(patient.getId());
    }

    @Test
    @DisplayName("patientRepository_updatePatient_ReturnOnePatientUpdated")
    public void patientRepository_updatePatient_ReturnOnePatientUpdated()
    {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        PatientDto patientDto = new PatientDto();
        patientDto.setNom("oussama");
        patientDto.setPrenom("ziad");
        patientDto.setTel("0630011246");
        patientDto.setSexe(Sexe.MALE);
        patientDto.setAddress("address 1");
        patientDto.setDateNaissance(LocalDate.of(2001,8,19));
        patientDto.setVille("casablanca");
        patient.setDeleted(false);
        patientDto.setAge(21);

        when(iPatientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);
        when(iPatientRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(patient));


        PatientDto patientDtoUpdated = patientService.update(1L, patientDto);
        System.out.println(patientDtoUpdated);
        System.out.println(patient.getNom() + " " + patient.getAddress() + " " +  patient.getPrenom());
        Assertions.assertThat(patientDtoUpdated).isNotNull();
        Assertions.assertThat(patientDtoUpdated.getAddress()).isEqualTo(patient.getAddress());
    }

    @Test
    @DisplayName("patientService_deleteByIdPatient_Return0Patient")
    public void patientService_deleteByIdPatient_Return0Patient()
    {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        when(iPatientRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(patient));

        assertAll(() -> patientService.delete(1L));
    }

    @Test
    @DisplayName("patientService_getAllPatient_ReturnMoreThanOnePatient")
    public void patientService_getAllPatient_ReturnMoreThanOnePatient()
    {
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setNom("marouane");
        patient1.setPrenom("mouslih");
        patient1.setTel("0630011246");
        patient1.setSexe(Sexe.MALE);
        patient1.setAddress("address 1");
        patient1.setDateNaissance(LocalDate.of(2001,8,19));
        patient1.setVille("casablanca");
        patient1.setDeleted(false);
        patient1.setAge(21);

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setNom("marouane");
        patient2.setPrenom("mouslih");
        patient2.setTel("0630011246");
        patient2.setSexe(Sexe.MALE);
        patient2.setAddress("address 1");
        patient2.setDateNaissance(LocalDate.of(2001,8,19));
        patient2.setVille("casablanca");
        patient2.setDeleted(false);
        patient2.setAge(21);

        Patient patient3 = new Patient();
        patient3.setId(3L);
        patient3.setNom("marouane");
        patient3.setPrenom("mouslih");
        patient3.setTel("0630011246");
        patient3.setSexe(Sexe.MALE);
        patient3.setAddress("address 1");
        patient3.setDateNaissance(LocalDate.of(2001,8,19));
        patient3.setVille("casablanca");
        patient3.setDeleted(false);
        patient3.setAge(21);

        PatientDto patientDto = new PatientDto();
        patientDto.setNom("marouane");
        patientDto.setPrenom("mouslih");
        patientDto.setTel("0630011246");
        patientDto.setSexe(Sexe.MALE);
        patientDto.setAddress("address 1");
        patientDto.setDateNaissance(LocalDate.of(2001,8,19));
        patientDto.setVille("casablanca");
        patientDto.setDeleted(false);
        patientDto.setAge(21);

        when(iPatientRepository.findByDeletedFalse()).thenReturn(Arrays.asList(patient1, patient2, patient3));

        List<PatientDto> patientAll = patientService.getAll();
        System.out.println(patientAll);
        Assertions.assertThat(patientAll).isNotEmpty();
        Assertions.assertThat(patientAll).hasSize(3);
    }
}