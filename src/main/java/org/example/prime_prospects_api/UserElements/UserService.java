package org.example.prime_prospects_api.UserElements;


import org.example.prime_prospects_api.AuthTokenResponse;
import org.example.prime_prospects_api.TokenStore;
import org.example.prime_prospects_api.essence.TheUser;
import org.example.prime_prospects_api.essence.parsers.TheUserPars;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class UserService  implements UserDetailsService {

    private final TheUserPars userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;

    public UserService(TheUserPars userRepository, PasswordEncoder passwordEncoder, TokenStore tokenStore) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
    }

    public long getIdByLogin(String login){
        return userRepository.findByLogin(login).get().getId();
    }

    public TheUser getUsetById(Long id){
        return userRepository.findById(id).get();
    }

    public void saveUser(TheUser user){
        userRepository.save(user);
    }

    public TheUser getUsetByLogin(String login){
        return userRepository.findByLogin(login).get();
    }

    @Override
    public TheUser loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + login));
    }


    @Transactional
    public TheUser registerUser(String name, String login, String password, boolean isEmployee) {
        if (userRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("Login already exists");
        }

        TheUser user = new TheUser();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmployee(isEmployee);
        user.setBirthDate(LocalDate.now());

        return userRepository.save(user);
    }

    public AuthTokenResponse login(String username, String password) {
        TheUser user = loadUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = tokenStore.generateToken(username);
        return new AuthTokenResponse(token);
    }

    public boolean isValidToken(String username, String token) {
        return tokenStore.isValidToken(username, token);
    }


    public Object getUserField(String username, AllowedUserFields field) {
        TheUser user = loadUserByUsername(username);
        try {
            switch (field) {
                case NAME:
                    return user.getName();
                case BIRTH_DATE:
                    return user.getBirthDate();
                case LOGIN:
                    return user.getLogin();
                case CITY:
                    return user.getCity();
                case PHONE_NUMBER:
                    return user.getPhoneNumber();
                case EMAIL:
                    return user.getEmail();
                case IS_EMPLOYEE:
                    return user.isEmployee();
                case DESCRIPTION:
                    return user.getDescription();
                default:
                    throw new IllegalArgumentException("Invalid field requested: " + field);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting field " + field + " for user " + username, e);
        }
    }



}