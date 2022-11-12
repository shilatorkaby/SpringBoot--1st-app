package com.app.Services;


import com.app.Entity.User;
import com.app.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthService {
    private final Map<Integer, String> Tokens;

    public AuthService() {
        this.Tokens = new HashMap<>();
    }

    private String createNewToken(User user)
    {
        String s= AuthService.generateRandomToken(8);
        Tokens.put(user.getId(),s);
        return s;
    }


    public String login(String email, String password) {
        if ( UserRepository.getInstance().userIsExist(email)) {
            User userByEmail =  UserRepository.getInstance().getUserByEmail(email);
            System.out.println(userByEmail);
            if (userByEmail.getPassword().equals(password)) {
                return createNewToken(userByEmail);
            }
            throw new IllegalArgumentException("The password does not match the email.");
        }
        return null;
    }

    public boolean checkToken(String email,String Token)
    {
        Objects.requireNonNull(Token);
        User user =  UserRepository.getInstance().getUserByEmail(email);
        if(user!=null) {
            {
                String getToken = Tokens.get(new ArrayList<>(Tokens.keySet()).indexOf(user.getId()));
                return (Objects.equals(getToken, Token));
            }
        }        throw new IllegalArgumentException("There is no user with the email you type");
    }

    private static String generateRandomToken(int length)
    {
        assert length>0;
        StringBuilder result= new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append((char) ThreadLocalRandom.current().nextInt(33, 125));
        }
        return result.toString();
    }
    public static User isUserExists(Optional<User> user) {
        return user.orElseThrow(() -> {
            throw new IllegalArgumentException("There is no user with the email you type");
        });
    }
}