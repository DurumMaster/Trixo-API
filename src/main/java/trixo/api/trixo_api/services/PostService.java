package trixo.api.trixo_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trixo.api.trixo_api.dto.PostResponse;
import trixo.api.trixo_api.entities.Post;
import trixo.api.trixo_api.repositories.PostRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostResponse createPost(Post post) throws ExecutionException, InterruptedException {
        // Generar ID único y timestamp en Firestore
        Post savedPost = postRepository.save(post);
        return mapToResponse(savedPost, null); // userId null para nuevo post
    }

    public List<PostResponse> getAllPosts(int limit, String currentUserId) throws ExecutionException, InterruptedException {
        return postRepository.getAllPosts(limit).stream()
                .map(post -> mapToResponse(post, currentUserId))
                .toList();
    }

    public List<PostResponse> getTopPosts(int limit, String currentUserId) throws ExecutionException, InterruptedException {
        return postRepository.getTopPosts(limit).stream()
                .map(post -> mapToResponse(post, currentUserId))
                .toList();
    }

    public List<PostResponse> getRecentPosts(int limit, String currentUserId) throws ExecutionException, InterruptedException {
        return postRepository.getRecentPosts(limit).stream()
                .map(post -> mapToResponse(post, currentUserId))
                .toList();
    }

    public PostResponse toggleLike(String postId, String userId) {
        Post post;
        try {
            post = postRepository.findById(postId);
            
            if (post == null) {
                throw new IllegalArgumentException("Post no encontrado con ID: " + postId);
            }
            
            boolean isLiked = post.getLikedBy().contains(userId);
            
            if (isLiked) {
                post.getLikedBy().remove(userId);
            } else {
                post.getLikedBy().add(userId);
            }
            
            Post updatedPost = postRepository.save(post);
            return mapToResponse(updatedPost, userId);
            
        } catch (ExecutionException e) {
            throw new RuntimeException("Error de comunicación con Firestore: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException("Operación interrumpida: " + e.getMessage());
        }
    }

    public PostResponse getPostById(String postId, String currentUserId) throws ExecutionException, InterruptedException {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Post no encontrado con ID: " + postId);
        }
        return mapToResponse(post, currentUserId);
    }

    public void deletePost(String postId) throws ExecutionException, InterruptedException {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Post no encontrado con ID: " + postId);
        }
        postRepository.deletePost(postId);
    }

    public List<PostResponse> getPostsByUserId(String userId, int limit, String currentUserId) throws ExecutionException, InterruptedException {
        return postRepository.getUsersPosts(userId, limit).stream()
                .map(post -> mapToResponse(post, currentUserId))
                .toList();
    }

    public List<PostResponse> getLikedPosts(String userId, int limit, String currentUserId) throws ExecutionException, InterruptedException {
        return postRepository.getLikedPosts(userId, limit).stream()
                .map(post -> mapToResponse(post, currentUserId))
                .toList();
    }

    public PostResponse mapToResponse(Post post, String currentUserId) {
        return new PostResponse(
            post.getId(),
            post.getCaption(),
            post.getImages(),
            post.getCreated_at(),
            post.getComments_count(),
            post.getTags(),
            post.getUser(),
            currentUserId != null ? post.getLikedBy().contains(currentUserId) : false,
            post.getLikedBy().size()
        );
    }

}