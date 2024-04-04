package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO convertUserToUserDTO(User user){

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
