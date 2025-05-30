package org.example.prime_prospects_api.VacansyElements;

import org.example.prime_prospects_api.essence.Vacancy;
import org.example.prime_prospects_api.essence.parsers.VacancyPars;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacansyService {
    private VacancyPars vacancyPars;

    public List<Vacancy> findByOwverId(long id){
        return vacancyPars.findAllByOvnerId(id).get();
    }

    public VacansyService(VacancyPars vacancyPars){
        this.vacancyPars = vacancyPars;
    }

    public void save(Vacancy vacancy){
        vacancyPars.save(vacancy);
    }

    public Vacancy find(long id){return vacancyPars.findById(id).get();}

    public void del(Vacancy sadVacancy){
        vacancyPars.delete(sadVacancy);
    }

    public List<String> getPosition(long id){ return vacancyPars.findAllPositionsByOvnerId(id).get();}

    public List<Long> getId(long id){ return vacancyPars.findAllIdsByOvnerId(id).get();}

    public List<Vacancy> search(String position,
                               String city,
                               String employmentType,
                               String schedule,
                               int workYears,
                               int workingHours,
                               Double salary){
        return vacancyPars.findAll(workingHours, city, employmentType, schedule, position, workYears, salary).get();
    }

    public List<Vacancy> searchByPosition(String position){
        return vacancyPars.findAllByPosition(position).get();
    }
}
