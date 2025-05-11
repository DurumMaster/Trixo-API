package trixo.api.trixo_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import trixo.api.trixo_api.entities.Comment;
import trixo.api.trixo_api.repositories.CommentRepository;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public boolean insertComment(Comment comment) {
        return commentRepository.insertComment(comment);
    }

    public boolean deleteComment(String commentId) {
        return commentRepository.deleteComment(commentId);
    }

    public List<Comment> getCommentById(String postID) {
        return commentRepository.getCommentsByPostID(postID);
    }

}
