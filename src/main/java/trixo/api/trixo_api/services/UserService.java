package trixo.api.trixo_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trixo.api.trixo_api.entities.User;
import trixo.api.trixo_api.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(User user) {
        return userRepository.insertUser(user);
    }

    public User getUserById(String userId) {
        return userRepository.getUserById(userId);
    }    

    public List<String> getUserPreferences(String userId) {
        return userRepository.getUserPreferences(userId);
    }

    public boolean updateUserPreferences(String userID, List<String> preferences) {
        return userRepository.updateUserPreferences(userID, preferences);
    }

    public boolean registerPreferences(String userID, List<String> preferences) {
        return userRepository.registerPreferences(userID, preferences);
    }

    public boolean updateUser(String userID, User user) {
        return userRepository.updateUser(userID, user);
    }

    public boolean deleteUser(String userID) {
        return userRepository.deleteUser(userID);
    }

    public boolean hasPreferences(String userID) {
        return userRepository.hasPreferences(userID);
    }
}
