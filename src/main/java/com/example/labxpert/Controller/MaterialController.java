package com.example.labxpert.Controller;

import com.example.labxpert.Service.IFournisseurService;
import com.example.labxpert.Service.IMaterialService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/material")
public class MaterialController {

    private final IMaterialService iMaterialService;
}
