package trixo.api.trixo_api.services;

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

}
