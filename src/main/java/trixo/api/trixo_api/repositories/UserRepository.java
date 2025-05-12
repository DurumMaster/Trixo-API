package trixo.api.trixo_api.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import trixo.api.trixo_api.entities.User;

@Repository
public class UserRepository {
    private static final String COLLECTION_NAME = "users";

    public boolean insertUser(User user) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(user.getId().toString()).set(user);
            WriteResult result = future.get(); 
            System.out.println("User saved at: " + result.getUpdateTime());
            return true; 
        } catch (Exception e) {
            System.err.println("Error inserting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(String userId) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            User user = db.collection(COLLECTION_NAME).document(userId).get().get().toObject(User.class);
            if (user == null) {
                return null; 
            }
            return user; 
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    public List<String> getUserPreferences(String userID){
        try{
            List<String> userPreferences = null;
            User user = getUserById(userID);
            if(user != null){
                if(user.getPreferences() != null){
                    userPreferences = user.getPreferences();
                }
            }

            return userPreferences;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateUserPreferences(String userId, List<String> preferences) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection(COLLECTION_NAME).document(userId).update("preferences", preferences);
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    public boolean registerPreferences(String userId, List<String> preferences) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection(COLLECTION_NAME).document(userId).set(preferences);
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    public boolean hasPreferences(String userId) {
        try {
            User user = getUserById(userId);
            return user != null && user.getPreferences() != null && !user.getPreferences().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(String userId, User user) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection(COLLECTION_NAME).document(userId).set(user);
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    public boolean deleteUser(String userId) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection(COLLECTION_NAME).document(userId).delete();
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
}
