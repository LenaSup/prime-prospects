package org.example.prime_prospects_api.essence.parsers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.example.prime_prospects_api.essence.Resume;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResumePars extends JpaRepository<Resume, Long> {
    Optional<List<Resume>> findAllByOvnerId(long id);

    @Query("SELECT r.position FROM Resume r WHERE r.ovnerId = :ovnerId")
    Optional<List<String>> findAllPositionsByOvnerId(@Param("ovnerId") Long ovnerId);

    @Query("SELECT r.id FROM Resume r WHERE r.ovnerId = :ovnerId")
    Optional<List<Long>> findAllIdsByOvnerId(@Param("ovnerId") Long ovnerId);

    @Query("SELECT r FROM Resume r WHERE " +
            "LOWER(r.position) LIKE LOWER(CONCAT('%', :position, '%')) AND " +
            "LOWER(r.skills) LIKE LOWER(CONCAT('%', :skills, '%')) AND " +
            "r.workYears >= :workYears AND " +
            "r.expectedSalary <= :expectedSalary")
    Optional<List<Resume>> findAll(@Param("position") String position,
                                              @Param("skills") String skills,
                                              @Param("workYears") int workYears,
                                              @Param("expectedSalary") Double expectedSalary);

    @Query("SELECT r FROM Resume r WHERE LOWER(r.position) LIKE LOWER(CONCAT('%', :position, '%'))")
    Optional<List<Resume>> findAllByPosition(@Param("position") String position);

}
