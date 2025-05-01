package trixo.api.trixo_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trixo.api.trixo_api.entities.Post;
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
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Post> posts = postService.getAllPosts(limit);
            return ResponseEntity.ok(posts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/top")
    public ResponseEntity<List<Post>> getTopPosts(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Post> topPosts = postService.getTopPosts(limit);
            return ResponseEntity.ok(topPosts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Post>> getRecentPosts(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Post> recentPosts = postService.getRecentPosts(limit);
            return ResponseEntity.ok(recentPosts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}