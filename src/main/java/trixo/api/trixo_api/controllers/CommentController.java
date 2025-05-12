package trixo.api.trixo_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import trixo.api.trixo_api.entities.Comment;
import trixo.api.trixo_api.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping("/insert")
    public ResponseEntity<String> insertComment(@RequestBody Comment comment) {
        if (commentService.insertComment(comment)) {
            return ResponseEntity.ok("Comment inserted successfully");
        } else {
            return ResponseEntity.status(404).body("Error inserting comment");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestBody String commentID) {
        if (commentService.deleteComment(commentID)) {
            return ResponseEntity.ok("Comment deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Error deleting comment");
        }
    }

    @GetMapping("/getCommentsByID")
    public ResponseEntity<String> getComments(@RequestParam String postID) {
        if (!commentService.getCommentById(postID).isEmpty()) {
            return ResponseEntity.ok("Comments retrieved successfully");
        } else {
            return ResponseEntity.status(404).body("Error retrieving comments");
        }
    }
    
}
