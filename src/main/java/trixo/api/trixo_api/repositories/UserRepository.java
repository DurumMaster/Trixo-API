package trixo.api.trixo_api.repositories;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

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

    public List<String> getUserPreferences(String userID) {
        try {
            List<String> userPreferences = null;
            User user = getUserById(userID);
            if (user != null) {
                if (user.getPreferences() != null) {
                    userPreferences = user.getPreferences();
                }
            }

            return userPreferences;
        } catch (Exception e) {
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
            DocumentReference docRef;

            Map<String, Object> data = new HashMap<>();
            data.put("preferences", preferences);

            docRef = db.collection(COLLECTION_NAME).document(userId);
            ApiFuture<WriteResult> future = docRef.set(data, SetOptions.merge());
            future.get();
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

            Map<String, Object> updates = new HashMap<>();
            if(user.getBio() != null){
                updates.put("bio", user.getBio());
            }

            if(user.getAvatar_img() != null){
                updates.put("avatar_img", user.getAvatar_img());
            }

            if(user.getUsername() != null){
                updates.put("username", user.getUsername());
            }
            
            if(!updates.isEmpty()) {
                db.collection(COLLECTION_NAME).document(userId).update(updates);
            } else {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String uploadImage(String userId, MultipartFile file) {
        if(file != null){
            try {
                StorageClient storageClient = StorageClient.getInstance();
                String bucketName = "trixo-1eacc.firebasestorage.app";
                String fileName = "avatar_images/" + userId + "_" + System.currentTimeMillis() + ".jpg";
                
                storageClient.bucket()
                .create(fileName, file.getBytes(), "image/jpeg");
                
                String imageUrl = "https://firebasestorage.googleapis.com/v0/b/"
                + bucketName + "/o/" + 
                URLEncoder.encode(fileName, "UTF-8")
                .replace("+", "%20")
                .replace("/", "%2F")
                + "?alt=media";
                
                deleteOldAvatar(userId);
                return imageUrl;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private void deleteOldAvatar(String userId) {
        User user = getUserById(userId);

        String oldImage = user.getAvatar_img();

        if (oldImage != null && !oldImage.isEmpty()) {
            try {
                int start = oldImage.indexOf("/o/") + 3;
                int end = oldImage.indexOf("?alt=media");
                if (start > 2 && end > start) {
                    String oldFileName = java.net.URLDecoder.decode(oldImage.substring(start, end), "UTF-8");
                    StorageClient.getInstance().bucket().get(oldFileName).delete();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean deleteUser(String userId) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            User user = getUserById(userId);
            if (user != null && user.getAvatar_img() != null && !user.getAvatar_img().isEmpty()) {
                try {
                    String avatarUrl = user.getAvatar_img();

                    String url = avatarUrl.substring(
                        avatarUrl.indexOf("/o/") + 3, avatarUrl.indexOf("?alt=media")
                    );

                    StorageClient.getInstance().bucket().get(url).delete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            db.collection(COLLECTION_NAME).document(userId).delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
