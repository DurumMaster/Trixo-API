package trixo.api.trixo_api.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import trixo.api.trixo_api.entities.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PostRepository {

    private static final String COLLECTION_NAME = "posts";

    public void savePost(Post post) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME).document(post.getId().toString()).set(post).get();
    }

    public List<Post> getAllPosts(int limit) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).limit(limit).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Post> posts = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            posts.add(doc.toObject(Post.class));
        }
        return posts;
    }

    public List<Post> getTopPosts(int limit) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                .orderBy("likes_count", Query.Direction.DESCENDING)
                .limit(limit)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Post> topPosts = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            topPosts.add(doc.toObject(Post.class));
        }
        return topPosts;
    }

    public List<Post> getRecentPosts(int limit) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                .orderBy("created_at", Query.Direction.DESCENDING)
                .limit(limit)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Post> recentPosts = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            recentPosts.add(doc.toObject(Post.class));
        }
        return recentPosts;
    }

}