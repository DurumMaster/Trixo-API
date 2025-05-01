package trixo.api.trixo_api.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import trixo.api.trixo_api.entities.User;

@Repository
public interface UserRepository {

    boolean existsByUsername(String username);
    
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
