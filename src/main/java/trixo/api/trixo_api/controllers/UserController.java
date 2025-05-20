package trixo.api.trixo_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trixo.api.trixo_api.dto.RegisterPreferencesRequest;
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

    @DeleteMapping("/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable String userID) {
        if(userService.deleteUser(userID)){
            return ResponseEntity.ok("User deleted successfully with ID: " + userID);
        } else {
            return ResponseEntity.status(500).body("Error deleting user");
        }
    }

    @GetMapping("{userID}/preferences")
    public ResponseEntity<Boolean> getUserPreferences(@PathVariable String userID) {
        boolean hasPreferences = userService.hasPreferences(userID);
        return ResponseEntity.ok(hasPreferences);
    }

    @PutMapping("/{userID}/preferences")
    public ResponseEntity<String> updateUserPreferences(
        @PathVariable String userID,
        @RequestBody List<String> preferences
    ) {
        if(userService.updateUserPreferences(userID, preferences)){
            return ResponseEntity.ok("User preferences updated successfully");
        } else {
            return ResponseEntity.status(500).body("Error updating user preferences");
        }
    }

    @PostMapping("/registerPreferences")
    public ResponseEntity<String> registerUserPreferences(@RequestBody RegisterPreferencesRequest request) {
        if(userService.registerPreferences(request.getUserID(), request.getPreferences())){
            return ResponseEntity.ok("User preferences registered successfully");
        } else {
            return ResponseEntity.status(500).body("Error registering user preferences");
        }
    }

    @PutMapping("/{userID}")
    public ResponseEntity<String> updateUser(
        @PathVariable String userID,
        @RequestBody User user
    ) {
        if(userService.updateUser(userID, user)){
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(500).body("Error updating user");
        }
    }

    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserById(@PathVariable String userID) {
        User user = userService.getUserById(userID);
        if(user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

}
