package com.coding.Java.Coding.Challenge2.service;

import com.coding.Java.Coding.Challenge2.entity.Task;
import com.coding.Java.Coding.Challenge2.entity.UserData;
import com.coding.Java.Coding.Challenge2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<Task> userAssignedTasks(Long userId) {
        UserData userData = fetchUserById(userId);
        if(userData !=null)
            return userData.getTasks();
        return null;
    }

    public UserData fetchUserById(Long userId){
        Optional<UserData> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }

    public void createUser(UserData userData) {
        userRepository.save(userData);
    }

    public void updateUser(UserData userData) {
        userRepository.save(userData);
    }
}
