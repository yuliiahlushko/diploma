package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.payload.auth.AuthenticationResponse;
import com.example.excursionPlanning.payload.auth.LoginRequest;
import com.example.excursionPlanning.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponse register(UserDTO userDTO);
    AuthenticationResponse authenticate(LoginRequest request);
}
