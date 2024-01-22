package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.ResultDto;
import com.example.labxpert.Dtos.SousAnalyseDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Enum.StatusResult;
import com.example.labxpert.Model.Result;
import com.example.labxpert.Model.SousAnalyse;
import com.example.labxpert.Repository.IAnalyseRepository;
import com.example.labxpert.Repository.IResultRepository;
import com.example.labxpert.Repository.ISousAnalyseRepository;
import com.example.labxpert.Service.IResultService;
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
public class ResultServiceImpl implements IResultService {

    private final IResultRepository iResultRepository;
    private final ISousAnalyseRepository iSousAnalyseRepository;
    private final IAnalyseRepository iAnalyseRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResultDto add(ResultDto resultDto)
    {
        validation(resultDto);

        SousAnalyse sousAnalyse = iSousAnalyseRepository.findByIdAndDeletedFalse(resultDto.getSousAnalyse().getId()).orElseThrow(() -> new NotFoundException("Sous analyse not found with this id : " + resultDto.getSousAnalyse().getId()));
        resultDto.setSousAnalyse(modelMapper.map(sousAnalyse, SousAnalyseDto.class));
        //TODO: CHANGE STATUS RESULT OF SOUS ANALYSE
        changeStatusResultOfSousAnalyse(resultDto, sousAnalyse);

        Result result = iResultRepository.save(modelMapper.map(resultDto, Result.class));
        return modelMapper.map(result, ResultDto.class);
    }

    @Override
    public void delete(Long id)
    {
        Result result = iResultRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Result not found with this id : " + id));
        result.setDeleted(true);
        iResultRepository.save(result);
    }

    @Override
    public ResultDto getById(Long id)
    {
        Result result = iResultRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Result not found with this id : " + id));
        return modelMapper.map(result, ResultDto.class);
    }

    @Override
    public List<ResultDto> getAll()
    {
        List<Result> results = iResultRepository.findByDeletedFalse();
        return results.stream()
                .map(result -> modelMapper.map(result, ResultDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResultDto update(Long id, ResultDto resultDto)
    {
        validation(resultDto);

        SousAnalyse sousAnalyseExist = iSousAnalyseRepository.findByIdAndDeletedFalse(resultDto.getSousAnalyse().getId()).orElseThrow(() -> new NotFoundException("Result not found with this id : " + id));
        Result resultExist = iResultRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Result not found with this id : " + id));
        resultExist.setValeurResult(resultDto.getValeurResult());
        resultExist.setUniteMesure(resultDto.getUniteMesure());
        resultExist.setSousAnalyse(sousAnalyseExist);

        changeStatusResultOfSousAnalyse(resultDto, sousAnalyseExist);

        Result result = iResultRepository.save(resultExist);
        return modelMapper.map(result, ResultDto.class);
    }

    @Override
    public void changeStatusResultOfSousAnalyse(ResultDto resultDto, SousAnalyse sousAnalyse)
    {
        if(sousAnalyse.getEtatNormalMin() < resultDto.getValeurResult() && sousAnalyse.getEtatNormalMax() > resultDto.getValeurResult())
        {
            sousAnalyse.setStatusResult(StatusResult.normal);
            iSousAnalyseRepository.save(sousAnalyse);
        }else{
            sousAnalyse.setStatusResult(StatusResult.anormal);
            iSousAnalyseRepository.save(sousAnalyse);
        }

        changeStatusResultOfAnalyse(sousAnalyse);
    }

    @Override
    public void changeStatusResultOfAnalyse(SousAnalyse sousAnalyse)
    {
        List<SousAnalyse> sousAnalysesByAnalyseId = iSousAnalyseRepository.findByAnalyseIdAndDeletedFalse(sousAnalyse.getAnalyse().getId());

        for (SousAnalyse sousAnalyseByAnalyseId : sousAnalysesByAnalyseId)
        {
            Analyse analyseForUpdatedStatusResult = iAnalyseRepository.findByIdAndDeletedFalse(sousAnalyseByAnalyseId.getAnalyse().getId()).orElseThrow(() -> new NotFoundException("Analyse not found with this id : " + sousAnalyseByAnalyseId.getAnalyse().getId()));
            if(sousAnalyseByAnalyseId.getStatusResult() == StatusResult.anormal)
            {
                analyseForUpdatedStatusResult.setStatusResult(StatusResult.anormal);
            }else{
                analyseForUpdatedStatusResult.setStatusResult(StatusResult.normal);
            }
            iAnalyseRepository.save(analyseForUpdatedStatusResult);
        }
    }

    @Override
    public void validation(ResultDto resultDto)
    {
        if(resultDto == null)
        {
            throw new ValidationException("Result est requise.");
        }

        if (StringUtils.isBlank(resultDto.getUniteMesure()))
        {
            throw new ValidationException("Unite de mesure est requise.");
        }

        if(resultDto.getValeurResult() == 0)
        {
            throw new ValidationException("Valeur result est requise.");
        }

        if(resultDto.getSousAnalyse() == null)
        {
            throw new ValidationException("Sous analyse est requise.");
        }
    }
}
