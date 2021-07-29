package co.com.library.management.controller;

import co.com.library.management.dto.ResourcesDTO;
import co.com.library.management.dto.UserDTO;
import co.com.library.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUserId/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") String id){
        return new ResponseEntity(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity(userService.createUser(userDTO), HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity(userService.updateUser(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id){
        try{
            userService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
