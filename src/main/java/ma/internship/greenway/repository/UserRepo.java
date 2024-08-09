package ma.internship.greenway.repository;

import ma.internship.greenway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);
    // UserRepo.java
// UserRepo.java
    @Query("SELECT u FROM User u WHERE u.role = :role AND (LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<User> searchByRoleAndName(@Param("role") String role, @Param("searchTerm") String searchTerm);

}
