package com.app.Services;
import com.app.Repository.UserRepository;
import com.app.Entity.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {


    public User createUser(User user) {
        isEmailFree(user.getEmail());
        UserRepository.getInstance().addNewUser(user);
        System.out.println("User is created!");
        return user;
    }

    private void isEmailFree(String email) {
        if (UserRepository.getInstance().getUserByEmail(email)!=null)
            throw new IllegalArgumentException("There is another user with the email you type. please try another.");
    }

    public void changePassword(String email, String password) {
        UserRepository.getInstance().updateUsersPassword(email,password);
    }

    public void changeName(String email, String name) {
        UserRepository.getInstance().updateUsersName(email, name);
    }
    public void changeEmail(String email,String newEmail)
    {
        if(UserRepository.getInstance().getUserByEmail(newEmail)!=null) throw  new IllegalArgumentException(String.format("The email address:%s is already in use.\nPlease trt another.",newEmail));
        UserRepository.getInstance().updateUsersEmail(email, newEmail);
    }

    public List<User> getUsers()  {return UserRepository.getInstance().getUsers();}

    public void deleteByEmail(String email) {UserRepository.getInstance().deleteByEmail(email);}

}