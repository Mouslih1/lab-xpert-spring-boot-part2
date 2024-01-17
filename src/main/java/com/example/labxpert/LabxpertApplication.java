package com.example.labxpert;

import com.example.labxpert.Model.*;
import com.example.labxpert.Model.Enum.Sexe;
import com.example.labxpert.Service.*;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
@SpringBootApplication
public class LabxpertApplication{

	public static void main(String[] args) {
		SpringApplication.run(LabxpertApplication.class, args);
	}

}
