package trixo.api.trixo_api.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import trixo.api.trixo_api.entities.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query("SELECT r FROM Rating r WHERE r.product.id = ?1")
    List<Rating> findByProductId(int productId);

    @Query("DELETE FROM Rating r WHERE r.id = ?1")
    boolean deleteRating(int ratingId);
}
