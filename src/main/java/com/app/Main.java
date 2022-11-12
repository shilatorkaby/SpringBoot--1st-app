package com.app;

import com.app.Controllers.AuthController;
import com.app.Controllers.UserController;
import com.app.Entity.User;

import java.util.List;

public class Main {
    static UserController userController = new UserController();
    static AuthController authController=new AuthController();

    public static void main(String[] args) {
        initUsersMap();
        List<User> users= userController.getAllUsers();
        User user = users.get(0);
        System.out.println(users);
        System.out.println(user.getEmail()+" "+user.getPassword());
        String token= authController.login(user.getEmail(),user.getPassword());
        System.out.println(token);
        userController.modifyUserName(user.getEmail(),"shilat",token);
        userController.modifyEmail(user.getEmail(),"shilat@gmail.com",token);
        userController.modifyPassword(user.getEmail(),"shilat@gmail.com",token);
        //userController.deleteByEmail(user.getEmail());
        System.out.println(userController.getAllUsers());

    }
    static void initUsersMap()
    {for (int i = 0; i < 10; i++) {
        userController.createNewUser(new User());
    }}

}
