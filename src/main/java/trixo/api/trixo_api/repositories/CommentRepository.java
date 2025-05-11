package trixo.api.trixo_api.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import trixo.api.trixo_api.entities.Comment;

@Repository
public class CommentRepository {
    
    public String COLLECTION_NAME = "comment";

    public boolean insertComment(Comment comment) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(comment.getComment_id()).set(comment);
            future.get(); 
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Comment> getCommentsByPostID(String postId) {
        List<Comment> comments = null;
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                                        .whereEqualTo("post_id", postId)
                                    .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            comments = new ArrayList<Comment>();
            for (QueryDocumentSnapshot document : documents) {
                comments.add(document.toObject(Comment.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    public boolean deleteComment(String commentId) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(commentId).delete();
            future.get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
