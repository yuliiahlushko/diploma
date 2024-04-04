package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.entity.User;
import com.example.excursionPlanning.payload.web.UpdateUserFormRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public interface UserService {

    User putUser(UserDTO userDTO, Principal principal);

    User patchUser(UserDTO userDTO, Principal principal);

    Optional<User> getUserById(Long id);

    Optional<User> getCurrentUser(Principal principal);

    UpdateUserFormRequest patchUser(UpdateUserFormRequest user, Principal principal,
                                    BCryptPasswordEncoder passwordEncoder);

}
