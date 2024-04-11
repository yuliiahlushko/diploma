package com.example.excursionPlanning.services.implementations;

import com.example.excursionPlanning.dao.UserRepository;
import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.entity.User;

import com.example.excursionPlanning.payload.web.UpdateUserFormRequest;
import com.example.excursionPlanning.services.FileLoader;
import com.example.excursionPlanning.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }


    public User putUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal).orElseThrow(() ->
                new UsernameNotFoundException("User with this email not found " ));

        user.setBio(userDTO.getBio());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setLogin(userDTO.getLogin());
        user.setImage(userDTO.getImage());
        User updatedUser = null;
        try {
            updatedUser = userRepository.save(user);
        } catch (Exception e) {
            LOG.error("User {} cannot be updated!", e.getMessage());
        }
        return updatedUser;
    }

    public User patchUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal).orElseThrow(() ->
                new UsernameNotFoundException("User with this email not found " ));
        if (userDTO.getBio() != null && !userDTO.getBio().isEmpty()) {
            user.setBio(userDTO.getBio());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getLogin() != null && !userDTO.getLogin().isEmpty()) {
            user.setLogin(userDTO.getLogin());
        }
        if (userDTO.getImage() != null ) {
            user.setImage(userDTO.getImage());
        }
        if (userDTO.getEmail() != null&& !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }

        User updatedUser = null;
        try {
            updatedUser = userRepository.save(user);
        } catch (Exception e) {
            LOG.error("User {} cannot be updated!", e.getMessage());
        }
        return updatedUser;
    }

    public UpdateUserFormRequest patchUser(UpdateUserFormRequest user, Principal principal,
                                           BCryptPasswordEncoder passwordEncoder) {
        User currentUser = getUserByPrincipal(principal).orElseThrow(() ->
                new UsernameNotFoundException("User with this email not found " ));

        UserDTO updateUser = new UserDTO();
        updateUser.setBio(user.getBio());
        updateUser.setLogin(user.getLogin());
        updateUser.setPassword(user.getNewPassword());
        if(!user.getNewImage().isEmpty()){
        try {
            updateUser.setImage(user.getNewImage().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }}
        updateUser.setEmail(user.getEmail());

        User updatedUser = patchUser(updateUser, principal);

        UpdateUserFormRequest response = new UpdateUserFormRequest();
        response.setBio(updatedUser.getBio());
        response.setLogin(updatedUser.getLogin());
        response.setEmail(updatedUser.getEmail());

        response.setNewImage(user.getNewImage());
        response.setNewPassword(updatedUser.getPassword());

        return response;

    }

    @Override
    public Optional<User> deletePhoto(Principal principal) {

        UserDTO currentUser = new UserDTO();

        try {
            currentUser.setImage(new FileLoader().loadFileAsBytes("image-icon.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(patchUser(currentUser,principal));
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private Optional<User> getUserByPrincipal(Principal principal) {
        String email = principal.getName();
        return userRepository.getUserByEmail(email);
    }


}
