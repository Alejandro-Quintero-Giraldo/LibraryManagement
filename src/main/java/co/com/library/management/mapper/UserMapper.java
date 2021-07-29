package co.com.library.management.mapper;

import co.com.library.management.dto.UserDTO;
import co.com.library.management.model.User;

public class UserMapper {

    public User toCollection(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setIdResource(userDTO.getIdResource());
        user.setNameResource(userDTO.getNameResource());
        user.setDateResource(userDTO.getDateResource());
        return user;
    }

    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setIdResource(user.getIdResource());
        userDTO.setNameResource(user.getNameResource());
        userDTO.setDateResource(user.getDateResource());
    return userDTO;
    }
}
