package org.example.prime_prospects_api.essence.parsers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.example.prime_prospects_api.essence.Response;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResponsePars extends JpaRepository<Response, Long> {
    @Query("SELECT r FROM Response r WHERE r.in = :id")
    Optional<List<Response>> findAllByIn(@Param("id") Long id);
    @Query("SELECT r FROM Response r WHERE r.out = :id")
    Optional<List<Response>> findAllByOut(@Param("id") Long id);
}
