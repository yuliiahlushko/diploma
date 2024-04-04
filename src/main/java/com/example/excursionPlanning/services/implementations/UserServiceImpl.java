package com.example.excursionPlanning.services.implementations;

import com.example.excursionPlanning.dao.UserRepository;
import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.entity.User;

import com.example.excursionPlanning.payload.web.UpdateUserFormRequest;
import com.example.excursionPlanning.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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

        user.setName(userDTO.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

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
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
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
        updateUser.setName(user.getName());
        updateUser.setPassword(currentUser.getPassword());
        updateUser.setEmail(currentUser.getEmail());

        User updatedUser = patchUser(updateUser, principal);

        UpdateUserFormRequest response = new UpdateUserFormRequest();
        response.setName(updatedUser.getName());
        response.setNewPassword(user.getNewPassword());
        return response;

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
