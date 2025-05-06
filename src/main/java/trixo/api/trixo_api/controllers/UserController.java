package trixo.api.trixo_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trixo.api.trixo_api.entities.User;
import trixo.api.trixo_api.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if(userService.registerUser(user)){
            return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
        } else {
            return ResponseEntity.status(500).body("Error registering user");
        }
    }
    
}
