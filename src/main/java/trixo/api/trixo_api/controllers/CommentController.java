package trixo.api.trixo_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("/delete/{commentID}")
    public ResponseEntity<String> deleteComment(@RequestParam String commentID) {
        if (commentService.deleteComment(commentID)) {
            return ResponseEntity.ok("Comment deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Error deleting comment");
        }
    }

    @GetMapping("/getCommentsByID")
    public ResponseEntity<List<Comment>> getComments(@RequestParam String postID) {
        List<Comment> comments = commentService.getCommentById(postID);
        return ResponseEntity.ok(comments);
    }
    
}
