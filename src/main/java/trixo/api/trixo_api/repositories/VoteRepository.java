package trixo.api.trixo_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import trixo.api.trixo_api.entities.Vote;
import trixo.api.trixo_api.entities.User;
import trixo.api.trixo_api.entities.Post;

@Repository
public interface VoteRepository {

    Optional<Vote> findByPostAndUser(Post post, User user);

    List<Vote> findByPostId(Integer postId);

    void deleteAllByPostId(Integer id);

}
