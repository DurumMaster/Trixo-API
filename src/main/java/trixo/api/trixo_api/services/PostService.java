package trixo.api.trixo_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.Timestamp;

import trixo.api.trixo_api.entities.Post;
import trixo.api.trixo_api.repositories.PostRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void createPost(Post post) throws ExecutionException, InterruptedException {
        Timestamp timestamp = Timestamp.parseTimestamp(post.getCreated_at().toString());
        post.setCreated_at(timestamp); // Or handle it in the repository
        postRepository.savePost(post);
    }

    public List<Post> getAllPosts(int limit) throws ExecutionException, InterruptedException {
        return postRepository.getAllPosts(limit);
    }

    public List<Post> getTopPosts(int limit) throws ExecutionException, InterruptedException {
        return postRepository.getTopPosts(limit);
    }

    public List<Post> getRecentPosts(int limit) throws ExecutionException, InterruptedException {
        return postRepository.getRecentPosts(limit);
    }

}