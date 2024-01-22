package com.example.labxpert.Repository;

import com.example.labxpert.Model.Enum.Sexe;
import com.example.labxpert.Model.Patient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;


@DataJpaTest
class IPatientRepositoryTest {

    @Autowired
    private IPatientRepository iPatientRepository;

    @Test
    void patientRepository_saveOne_ReturnOnePatient()
    {
        Patient patient = new Patient();
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        Patient p = iPatientRepository.save(patient);
        Assertions.assertThat(p).isNotNull();
        Assertions.assertThat(p.getId()).isGreaterThan(0);
    }

    @Test
    void patientRepository_deleteOnePatient_ReturnForDeleteIsEmpty()
    {
        Patient patient = new Patient();
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        Patient p = iPatientRepository.save(patient);
        p.setDeleted(true);
        Patient p2 = iPatientRepository.save(p);
        Patient p3 = iPatientRepository.findByIdAndDeletedFalse(p2.getId()).orElse(null);
        Assertions.assertThat(p3).isNull();
    }

    @Test
    void patientRepository_updateOne_ReturnOnePatient()
    {
        Patient patient = new Patient();
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        Patient p1 = iPatientRepository.save(patient);
        p1.setAge(56);
        Patient p2 = iPatientRepository.save(p1);
        Assertions.assertThat(p2.getAge()).isEqualTo(56);
    }

    @Test
    void patientRepository_getAll_ReturnMoreThanOnePatient()
    {
        Patient patient1 = new Patient();
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
        patient2.setNom("oussama");
        patient2.setPrenom("mouslih");
        patient2.setTel("0630011246");
        patient2.setSexe(Sexe.MALE);
        patient2.setAddress("address 1");
        patient2.setDateNaissance(LocalDate.of(2005,8,19));
        patient2.setVille("casablanca");
        patient2.setDeleted(false);
        patient2.setAge(21);

        Patient patient3 = new Patient();
        patient3.setNom("ziad");
        patient3.setPrenom("mouslih");
        patient3.setTel("0630011246");
        patient3.setSexe(Sexe.MALE);
        patient3.setAddress("address 1");
        patient3.setDateNaissance(LocalDate.of(2007,8,19));
        patient3.setVille("casablanca");
        patient3.setDeleted(false);
        patient3.setAge(21);

        Stream.of(patient1,patient2,patient3).forEach(patient -> {
            Patient patientSaved = iPatientRepository.save(patient);
            Assertions.assertThat(patientSaved).isNotNull();
        });

        List<Patient> patients = iPatientRepository.findByDeletedFalse();
        Assertions.assertThat(patients).isNotEmpty();
    }

    @Test
    void patientRepository_getPatientById_ReturnOnePatientWithId()
    {
        Patient patient = new Patient();
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        Patient patientSaved = iPatientRepository.save(patient);
        Patient patientGet = iPatientRepository.findByIdAndDeletedFalse(patientSaved.getId()).orElse(null);
        Assertions.assertThat(patientGet).isNotNull();
    }

    @Test
    void patientRepository_getPatientByName_ReturnPatientWithName()
    {
        Patient patient = new Patient();
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        Patient patientSaved = iPatientRepository.save(patient);
        Patient patientByName = iPatientRepository.findByNomAndDeletedFalse(patientSaved.getNom()).orElse(null);
        Assertions.assertThat(patientByName).isNotNull();
        Assertions.assertThat(patientSaved.getNom()).isEqualTo(patientByName.getNom());
    }
}