package com.example.labxpert.Controller;

import com.example.labxpert.Dtos.MaterialDto;
import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Dtos.UserDto;
import com.example.labxpert.Exception.MessageErrorException.MessageError;
import com.example.labxpert.Service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService iUserService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll()
    {
        return ResponseEntity.ok(iUserService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto userDto)
    {
        UserDto userSaved = iUserService.add(userDto);
        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto userDto)
    {
        UserDto userUpdated = iUserService.update(id, userDto);
        return new ResponseEntity<>(userUpdated,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id)
    {
        try{
            UserDto userDto = iUserService.getById(id);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Material deleted successfully.");
        iUserService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getByName(@RequestParam String name)
    {
        try{
            UserDto user = iUserService.getByName(name);
            return  new ResponseEntity<>(user, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
