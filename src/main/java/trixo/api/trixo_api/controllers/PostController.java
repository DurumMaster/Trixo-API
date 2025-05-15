package trixo.api.trixo_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import trixo.api.trixo_api.dto.PostResponse;
import trixo.api.trixo_api.entities.Post;
import trixo.api.trixo_api.entities.User;
import trixo.api.trixo_api.services.PostService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        if (post.getId() == null) {
            return ResponseEntity.badRequest().body("Post ID is required.");
        }

        try {
            postService.createPost(post);
            return ResponseEntity.ok("Post created successfully with ID: " + post.getId());
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error creating post: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "0") int offset,
        @AuthenticationPrincipal UserDetails userDetails) {
        String userId = ((User) userDetails).getId();
        try {
            List<PostResponse> posts = postService.getAllPosts(limit, userId, offset);
            return ResponseEntity.ok(posts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/top")
    public ResponseEntity<List<PostResponse>> getTopPosts(
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "0") int offset) {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userId = jwt.getSubject();
        }
        try {
            List<PostResponse> topPosts = postService.getTopPosts(limit, userId, offset);
            return ResponseEntity.ok(topPosts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PostResponse>> getRecentPosts(
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "0") int offset,
        @AuthenticationPrincipal UserDetails userDetails) {
        String userId = ((User) userDetails).getId();
        try {
            List<PostResponse> recentPosts = postService.getRecentPosts(limit, userId, offset);
            return ResponseEntity.ok(recentPosts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/forYou/{userID}")
    public ResponseEntity<List<PostResponse>> getForYou(
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam String userID) {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userId = jwt.getSubject();
        }
        try {
            List<PostResponse> forYouPosts = postService.getForYou(userID, limit, offset, userId);
            return ResponseEntity.ok(forYouPosts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<PostResponse> toggleLike(
            @PathVariable String postId) {

        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userId = jwt.getSubject();
        }
        PostResponse response = postService.toggleLike(postId, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/postsByUserID")
    public ResponseEntity<List<PostResponse>> getPostByUserID(
        @RequestParam String userID,
        @RequestParam (defaultValue = "10") int limit){
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userId = jwt.getSubject();
        }
        try {
            List<PostResponse> posts = postService.getPostsByUserId(userID, limit, userId);
            return ResponseEntity.ok(posts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/likedPosts")
    public ResponseEntity<List<PostResponse>> getLikedPosts(@RequestParam String userID) {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userId = jwt.getSubject();
        }
        try {
            List<PostResponse> posts = postService.getLikedPosts(userID, 10, userId);
            return ResponseEntity.ok(posts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successfully.");
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error deleting post: " + e.getMessage());
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable String postId) {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userId = jwt.getSubject();
        }
        try {
            PostResponse post = postService.getPostById(postId, userId);
            return ResponseEntity.ok(post);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getPostsByStatus/{status}")
    public ResponseEntity<List<PostResponse>> getPostsByStatus(
        @PathVariable String status) {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userId = jwt.getSubject();
        }
        try {
            List<PostResponse> posts = postService.getPostsByStatus(status, userId);
            return ResponseEntity.ok(posts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }


    @PutMapping("/{postId}/status/{status}")
    public ResponseEntity<String> updatePostStatus(
        @PathVariable String postId,
        @PathVariable String status) throws ExecutionException, InterruptedException {
            postService.updatePostStatus(postId, status);
            return ResponseEntity.ok("Post status updated successfully.");
    }

    
}