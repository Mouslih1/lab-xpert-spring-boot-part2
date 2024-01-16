package com.example.labxpert.Controller;

import com.example.labxpert.Model.Reactif;
import com.example.labxpert.Model.User;
import com.example.labxpert.Service.IReactifService;
import com.example.labxpert.Service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserContoller {

    private IUserService iUserService;

    @GetMapping("/getAll")
    public List<User> list() {
        return iUserService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {

        try {
            User user = iUserService.getById(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        User userSaved = iUserService.add(user);
        return new ResponseEntity<User>(userSaved, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        User userUpdated = iUserService.update(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        iUserService.delete(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }


}
