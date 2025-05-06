package trixo.api.trixo_api.repositories;

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

}
