package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.FournisseurDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Repository.IFournisseurRepository;
import com.example.labxpert.Service.IFournisseurService;
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
public class FournisseurServiceImpl implements IFournisseurService {

    private final IFournisseurRepository iFournisseurRepository;
    private final ModelMapper modelMapper;

    @Override
    public FournisseurDto add(FournisseurDto fournisseurDto)
    {
        validation(fournisseurDto);
        Fournisseur fournisseurSaved = iFournisseurRepository.save(modelMapper.map(fournisseurDto, Fournisseur.class));
        return modelMapper.map(fournisseurSaved, FournisseurDto.class);
    }

    @Override
    public FournisseurDto update(Long id, FournisseurDto fournisseurDto)
    {
        validation(fournisseurDto);
        Fournisseur fournisseur = iFournisseurRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Fournisseur not found with this id :" + id));
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setSocieteName(fournisseurDto.getSocieteName());
        Fournisseur fournisseurUpdated = iFournisseurRepository.save(fournisseur);
        return modelMapper.map(fournisseurUpdated, FournisseurDto.class);
    }

    @Override
    public void delete(Long id)
    {
        Fournisseur fournisseurDeleted = iFournisseurRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Fournisseur not found with this id : " + id));
        fournisseurDeleted.setDeleted(true);
        iFournisseurRepository.save(fournisseurDeleted);
    }

    @Override
    public List<FournisseurDto> getAll()
    {
        List<Fournisseur> fournisseurs = iFournisseurRepository.findByDeletedFalse();
        return fournisseurs.stream().
                map(fournisseur -> modelMapper.map(fournisseur, FournisseurDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FournisseurDto getById(Long id)
    {
        Fournisseur fournisseur = iFournisseurRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Fournisseur not found with this id : " + id));
        return modelMapper.map(fournisseur, FournisseurDto.class);
    }

    @Override
    public FournisseurDto getByName(String name)
    {
        Fournisseur fournisseur = iFournisseurRepository.findByNomAndDeletedFalse(name).orElseThrow(() -> new NotFoundException("Fournisseur not found with this name : " + name));
        return modelMapper.map(fournisseur, FournisseurDto.class);
    }

    @Override
    public void validation(FournisseurDto fournisseurDto)
    {
        if(fournisseurDto == null)
        {
            throw new ValidationException("Les données du fournisseur sont nécessaires.");
        }

        if(fournisseurDto.getNom() == null)
        {
            throw new ValidationException("Le nom est requise.");
        }

        if(fournisseurDto.getSocieteName() == null)
        {
            throw new ValidationException("Le societe nom est requise.");
        }
    }
}
