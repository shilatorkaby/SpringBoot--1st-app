package com.app.Repository;
import com.app.Entity.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private static final String PATH = "src\\main\\java\\com\\app\\Repository\\jsons";
    private static final Gson gson = new Gson();
    private final Map<String, User> usersByEmails=new HashMap<>();
    private static UserRepository instance;



    public static UserRepository getInstance() {
        if(instance == null)
            instance = new UserRepository();
        return instance;
    }

    public void addNewUser(User user) {
        int userId = user.getId();
        try {
            gson.toJson(user,new FileWriter(PATH + "\\User" + userId));
            usersByEmails.put(user.getEmail(), user);
            //logger.info("file created");
        } catch (IOException e) {
            //logger.error("new user json file creation/writing failed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public boolean userIsExist(String email)
    {
        System.out.println(usersByEmails.keySet() + " "+email);
        return(usersByEmails.containsKey(email));
    }


    public User getUserById(int id) {
        File userJsonFile = new File(PATH + "User" + id);
        FileReader fileReader;
        if (!userJsonFile.exists()) {
            System.out.println("User " + id + "does not exist");
            return null;
        }
        try {
            fileReader = new FileReader(userJsonFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);  //will never occur
        }
        return gson.fromJson(fileReader, User.class);
    }

    public User getUserByEmail(String email) {
        User user=usersByEmails.get(email);
        return user;

    }


    public void updateUsersName(String email, String newName)   {
        User tempUser = throwUserNotFoundException((getUserByEmail(email)),email);
        tempUser.setName(newName);
        addNewUser(tempUser);
        //logger.info(tempUser.getName());
    }

    public void updateUsersPassword(String email, String newPassword)   {
        User tempUser = throwUserNotFoundException((getUserByEmail(email)),email);
        tempUser.setPassword(newPassword);
        addNewUser(tempUser);
        //logger.info(tempUser.getName());
    }


    public void updateUsersEmail(String email, String newEmail)   {
        User tempUser = throwUserNotFoundException((getUserByEmail(email)),email);
        tempUser.setEmail(newEmail);
        addNewUser(tempUser);
        //logger.info(tempUser.getName());
    }
    private User throwUserNotFoundException(User user,String email)
    {
        if(user!=null)return user;
        throw new NullPointerException("No user with the following email address: "+ email+ " was found in the system.");
    }

    public List<User> getUsers()
    {
       return(new ArrayList<>(usersByEmails.values()));
    }


    public void deleteByEmail(String email){
        usersByEmails.remove(email);
    }
}