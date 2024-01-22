package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.FournisseurDto;
import com.example.labxpert.Dtos.ReactifDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Model.Patient;
import com.example.labxpert.Model.Reactif;
import com.example.labxpert.Repository.IFournisseurRepository;
import com.example.labxpert.Repository.IReactifRepository;
import com.example.labxpert.Service.IReactifService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReactifServiceImpl implements IReactifService {

    private final IReactifRepository iReactifRepository;
    private final IFournisseurRepository iFournisseurRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReactifDto add(ReactifDto reactifDto)
    {
        validation(reactifDto);
        List<FournisseurDto> fournisseurs = new ArrayList<>();

        for (FournisseurDto fournisseur : reactifDto.getFournisseurs())
        {
            Fournisseur fournisseurExist = iFournisseurRepository.findByIdAndDeletedFalse(fournisseur.getId()).orElseThrow(() -> new NotFoundException("Founisseur not found with this id : " + fournisseur.getId()));
            fournisseurs.add(modelMapper.map(fournisseurExist, FournisseurDto.class));
        }

        reactifDto.setFournisseurs(fournisseurs);
        Reactif reactifSaved = iReactifRepository.save(modelMapper.map(reactifDto, Reactif.class));
        return modelMapper.map(reactifSaved, ReactifDto.class);
    }

    @Override
    public ReactifDto update(Long id, ReactifDto reactifDto)
    {
        validation(reactifDto);
        Reactif reactifExist = iReactifRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Reactif not found with this id : " + id));
        List<Fournisseur> fournisseurs = new ArrayList<>();

        for (FournisseurDto fournisseur : reactifDto.getFournisseurs())
        {
            Fournisseur fournisseurExist = iFournisseurRepository.findByIdAndDeletedFalse(fournisseur.getId()).orElseThrow(() -> new NotFoundException("Founisseur not found with this id : " + fournisseur.getId()));
            fournisseurs.add(fournisseurExist);
        }

        reactifExist.setNom(reactifDto.getNom());
        reactifExist.setDescription(reactifDto.getDescription());
        reactifExist.setQuantityStock(reactifDto.getQuantityStock());
        reactifExist.setDateExp(reactifDto.getDateExp());
        reactifExist.setFournisseurs(fournisseurs);

        Reactif reactifSaved = iReactifRepository.save(reactifExist);
        return modelMapper.map(reactifSaved, ReactifDto.class);
    }

    @Override
    public void delete(Long id)
    {
        Reactif reactifDeleted = iReactifRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Reactif not found with this id : " + id));
        reactifDeleted.setDeleted(true);
        iReactifRepository.save(reactifDeleted);
    }

    @Override
    public List<ReactifDto> getAll()
    {
        List<Reactif> reactifs = iReactifRepository.findByDeletedFalse();
        return reactifs.stream()
                .map(reactif -> modelMapper.map(reactif, ReactifDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReactifDto getById(Long id)
    {
        Reactif reactif = iReactifRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Reactif not found with this id : " + id));
        return modelMapper.map(reactif, ReactifDto.class);
    }

    @Override
    public ReactifDto getByName(String name)
    {
        Reactif reactif = iReactifRepository.findByNomAndDeletedFalse(name).orElseThrow(() -> new NotFoundException("Reactif not found with this name : " + name));
        return modelMapper.map(reactif, ReactifDto.class);
    }

    @Override
    public List<ReactifDto> getByQuantityStockBefore(int quantityStock)
    {
        List<Reactif> reactifs = iReactifRepository.findByQuantityStockBeforeAndDeletedFalse(quantityStock);
        return reactifs.stream()
                .map(reactif -> modelMapper.map(reactif, ReactifDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void validation(ReactifDto reactifDto)
    {
        if (StringUtils.isBlank(reactifDto.getNom())) {
            throw new ValidationException("Le nom est requise.");
        }

        if (StringUtils.isBlank(reactifDto.getDescription())) {
            throw new ValidationException("Le description est requise.");
        }

        if (reactifDto.getQuantityStock() < 0) {
            throw new ValidationException("La quantité doit être supérieure ou égale à zéro.");
        }

        if(reactifDto.getDateExp() == null)
        {
            throw new ValidationException("La date de expiration est requise.");
        }
    }
}
