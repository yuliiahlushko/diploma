package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.entity.User;
import com.example.excursionPlanning.payload.web.UserProfileResponse;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserFacadeResponse {

    public UserProfileResponse convertUserToUserFacadeResponse(User user){

        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setLogin(user.getLogin());
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setNewPassword(user.getPassword());
        userProfileResponse.setBio(user.getBio());
        userProfileResponse.setNewImage(Base64.getEncoder().encodeToString(user.getImage()));
        return userProfileResponse;
    }
}
