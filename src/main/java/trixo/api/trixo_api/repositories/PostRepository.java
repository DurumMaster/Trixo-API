package trixo.api.trixo_api.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import trixo.api.trixo_api.entities.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class PostRepository {

    private static final String COLLECTION_NAME = "posts";
    
    public Post save(Post post) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef;
        Map<String, Object> postData = new HashMap<>();

        // Convertir la entidad Post a mapa de datos para Firestore
        postData.put("id", post.getId());
        postData.put("caption", post.getCaption());
        postData.put("images", post.getImages());
        postData.put("likedBy", post.getLikedBy());
        postData.put("likes_count", post.getLikedBy().size());
        postData.put("comments_count", post.getComments_count());
        postData.put("tags", post.getTags());
        postData.put("user", post.getUser());

        // Manejar ID y timestamps
        if (post.getId() == null) {
            docRef = db.collection(COLLECTION_NAME).document();
            postData.put("created_at", FieldValue.serverTimestamp()); // Usar FieldValue solo aquí
            post.setId(docRef.getId());
        } else {
            docRef = db.collection(COLLECTION_NAME).document(post.getId().toString());
            postData.put("created_at", post.getCreated_at()); // Mantener el existente
        }

        // Guardar en Firestore
        ApiFuture<WriteResult> future = docRef.set(postData, SetOptions.merge());
        future.get();

        // Obtener el documento actualizado con el timestamp real
        DocumentSnapshot document = docRef.get().get();

        // Actualizar la entidad con los valores reales de Firestore
        if (document.exists()) {
            Post savedPost = document.toObject(Post.class);
            post.setCreated_at(savedPost.getCreated_at());
        }

        return post;
    }

    public List<Post> getAllPosts(int limit, int offset) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        return executeQuery(
                db.collection(COLLECTION_NAME)
                        .orderBy("created_at", Query.Direction.DESCENDING)
                        
                    .limit(limit).offset(offset));
    }

    public List<Post> getTopPosts(int limit, int offset) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        return executeQuery(
                db.collection(COLLECTION_NAME)
                        .orderBy("likes_count", Query.Direction.DESCENDING)
                    .limit(limit).offset(offset));
    }

    public List<Post> getRecentPosts(int limit, int offset) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        return executeQuery(
                db.collection(COLLECTION_NAME)
                        .orderBy("created_at", Query.Direction.DESCENDING)
                    .limit(limit).offset(offset));
    }

    public List<Post> getForYou(List<String> preferences, int limit, int offset) throws ExecutionException, InterruptedException {
        if (preferences == null || preferences.isEmpty()) {
            return new ArrayList<>(); // No preferences, return empty list
        }
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection(COLLECTION_NAME)
                .whereArrayContainsAny("tags", preferences)
                .orderBy("created_at", Query.Direction.DESCENDING)
            .limit(limit).offset(offset);
        return executeQuery(query);
    }

    public Post findById(String postId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(postId.toString());
        DocumentSnapshot document = docRef.get().get();

        if (document.exists()) {
            return document.toObject(Post.class);
        }
        return null;
    }

    public List<Post> getUsersPosts(String userId, int limit, int offset) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection(COLLECTION_NAME)
                .whereEqualTo("user", userId)
                .orderBy("created_at", Query.Direction.DESCENDING).limit(limit).offset(offset);
        return executeQuery(query);
    }

    public List<Post> getLikedPosts(String userId, int limit, int offset) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection(COLLECTION_NAME)
                .whereArrayContains("likedBy", userId)
                .orderBy("created_at", Query.Direction.DESCENDING).limit(limit).offset(offset);
        return executeQuery(query);
    }

    public boolean deletePost(String postId) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(postId).delete();
            future.get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Post> getPostByStatus(String status) {
        List<Post> posts = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection(COLLECTION_NAME)
                .whereEqualTo("report.status", status)
                .orderBy("created_at", Query.Direction.DESCENDING);
        try {
            ApiFuture<QuerySnapshot> future = query.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Post post = doc.toObject(Post.class);
                posts.add(post);
            }
            return posts;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return posts;
        }
    }

    public boolean updatePostStatus(String postId, String status) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection(COLLECTION_NAME).document(postId);
            ApiFuture<WriteResult> future = docRef.update("report.status", status);
            future.get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createReport(String postId, String reason, String userId) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection(COLLECTION_NAME).document(postId);
            Map<String, Object> reportData = new HashMap<>();
            reportData.put("status", "onRevision");
            reportData.put("reason", reason);
            reportData.put("reportedBy", userId);
            ApiFuture<WriteResult> future = docRef.update("report", reportData);
            future.get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Post> executeQuery(Query query) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Post> posts = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            Post post = doc.toObject(Post.class);
            if(doc.get("report") != null){
                String status = doc.getString("report.status");
                if(status.equals("notReported")){
                    posts.add(post);
                }
            }
        }
        return posts;
    }
}