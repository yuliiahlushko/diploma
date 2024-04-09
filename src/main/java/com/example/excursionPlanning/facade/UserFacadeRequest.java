package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacadeRequest {

    public UserDTO convertUserToUserDTO(User user){

        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setBio(user.getBio());
        userDTO.setImage(user.getImage());
        return userDTO;
    }
}
