package org.example.prime_prospects_api.essence.parsers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.example.prime_prospects_api.essence.Vacancy;

import java.util.List;
import java.util.Optional;


@Repository
public interface VacancyPars extends JpaRepository<Vacancy, Long> {
    Optional<List<Vacancy>> findAllByOvnerId(long id);

    @Query("SELECT v.position FROM Vacancy v WHERE v.ovnerId = :ovnerId")
    Optional<List<String>> findAllPositionsByOvnerId(@Param("ovnerId") Long ovnerId);

    @Query("SELECT v.id FROM Vacancy v WHERE v.ovnerId = :ovnerId")
    Optional<List<Long>> findAllIdsByOvnerId(@Param("ovnerId") Long ovnerId);

    @Query("SELECT v FROM Vacancy v WHERE " +
            "LOWER(v.address) LIKE LOWER(CONCAT('%', :address, '%')) AND " +
            "LOWER(v.employmentType) LIKE LOWER(CONCAT('%', :employmentType, '%')) AND " +
            "LOWER(v.schedule) LIKE LOWER(CONCAT('%', :schedule, '%')) AND " +
            "LOWER(v.position) LIKE LOWER(CONCAT('%', :position, '%')) AND " +
            "v.workingHours <= :workingHours AND " +
            "v.workExperienceYears <= :workExperienceYears AND " +
            "v.salary >= :salary")
    Optional<List<Vacancy>> findAll(@Param("workingHours") int workingHours,
                                   @Param("address") String address,
                                   @Param("employmentType") String employmentType,
                                   @Param("schedule") String schedule,
                                   @Param("position") String position,
                                   @Param("workExperienceYears") int workExperienceYears,
                                   @Param("salary") Double salary);

    @Query("SELECT v FROM Vacancy v WHERE LOWER(v.position) LIKE LOWER(CONCAT('%', :position, '%'))")
    Optional<List<Vacancy>> findAllByPosition(@Param("position") String position);

}
