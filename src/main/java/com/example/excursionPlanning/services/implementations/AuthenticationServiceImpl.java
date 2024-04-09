package com.example.excursionPlanning.services.implementations;


import com.example.excursionPlanning.payload.auth.LoginRequest;
import com.example.excursionPlanning.payload.auth.AuthenticationResponse;
import com.example.excursionPlanning.dao.UserRepository;
import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.entity.User;
import com.example.excursionPlanning.entity.enums.Role;


import com.example.excursionPlanning.services.interfaces.AuthenticationService;
import com.example.excursionPlanning.securityconfig.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(UserDTO userDTO) {


        var newUser = User.builder()
                .email(userDTO.getEmail())
                .login(userDTO.getLogin())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .bio(userDTO.getBio())
                .build();


        try {

            if (userRepository.getUserByEmail(userDTO.getEmail()).isPresent()) {
               throw new DuplicateKeyException("User with this email already exist !");

            } else {
                userRepository.save(newUser);
                var jwtToken = jwtService.generateToken(newUser);

                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
        } catch ( RuntimeException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }


    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.getUserByEmail(request.getEmail())
                    .orElseThrow();

            var jwtToken = jwtService.generateToken(user);


            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}