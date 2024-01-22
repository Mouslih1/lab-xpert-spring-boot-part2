package com.example.labxpert.Service;


import com.example.labxpert.Dtos.UserDto;
import com.example.labxpert.Model.User;

import java.util.List;

public interface IUserService {

    UserDto add(UserDto userDto);
    UserDto update(Long id, UserDto userDto);
    void delete(Long id);
    List<UserDto> getAll();
    UserDto getById(Long id);
    UserDto getByName(String name);
    String getByEmail(String email);
    void validation(UserDto userDto);
}
