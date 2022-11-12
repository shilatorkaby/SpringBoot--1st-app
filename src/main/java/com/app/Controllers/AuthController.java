package com.app.Controllers;


import com.app.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Validation validation;
    @Autowired
    AuthService authService;

    public AuthController() {
        validation = new Validation();
        authService = new AuthService();
    }

    @RequestMapping(value ="/login",method = RequestMethod.GET)
    public String login(@RequestParam String email, @RequestParam String password) {
        try {
            validation.isValidEmail(email);
            //validation.isValidPassword(password);
            String token = authService.login(email, password);
            System.out.println("Login succeeded.");
            return token;
        }
        catch (IllegalArgumentException exp)
        {
            System.out.println("Login failed."+exp.getMessage());
            return null;
        }
    }

    public void checkToken(String email, String Token) {
        if(Token==null || Token.isEmpty())
        {
            System.out.println("Login failed."+"The token is null or empty.\n You must login first to get a valid token.");
            return;
        }
        authService.checkToken(email, Token);
    }
}