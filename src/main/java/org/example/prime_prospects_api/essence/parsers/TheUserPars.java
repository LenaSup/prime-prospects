package org.example.prime_prospects_api.essence.parsers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.prime_prospects_api.essence.TheUser;
import java.util.Optional;


@Repository
public interface TheUserPars extends JpaRepository<TheUser, Integer> {
    Optional<TheUser> findByLogin(String login);
    Optional<TheUser> findById(Long id);
    boolean existsByLogin(String login);
}
