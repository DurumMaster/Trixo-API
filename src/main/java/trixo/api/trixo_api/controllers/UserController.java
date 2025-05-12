package trixo.api.trixo_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody String userID) {
        if(userService.deleteUser(userID)){
            return ResponseEntity.ok("User deleted successfully with ID");
        } else {
            return ResponseEntity.status(500).body("Error deleting user");
        }
    }

    @GetMapping("/getPreferences")
    public ResponseEntity<Boolean> getUserPreferences(@RequestParam String userID) {
        boolean hasPreferences = userService.hasPreferences(userID);
        return ResponseEntity.ok(hasPreferences);
    }

    @PostMapping("/updatePreferences")
    public ResponseEntity<String> updateUserPreferences(@RequestBody String userID, @RequestBody List<String> preferences) {
        if(userService.updateUserPreferences(userID, preferences)){
            return ResponseEntity.ok("User preferences updated successfully");
        } else {
            return ResponseEntity.status(500).body("Error updating user preferences");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody String userID, @RequestBody User user) {
        if(userService.updateUser(userID, user)){
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(500).body("Error updating user");
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserById(@RequestBody String userID) {
        User user = userService.getUserById(userID);
        if(user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

}
