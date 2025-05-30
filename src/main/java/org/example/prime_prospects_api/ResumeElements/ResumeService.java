package org.example.prime_prospects_api.ResumeElements;


import org.example.prime_prospects_api.essence.Resume;
import org.example.prime_prospects_api.essence.parsers.ResumePars;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {
    private ResumePars resumePars;
    public ResumeService(ResumePars resumePars){
        this.resumePars = resumePars;
    }

    public Resume find(long id){return resumePars.findById(id).get();}

    public void del(Resume sadResume){
        resumePars.delete(sadResume);
    }

    public List<Resume> findByOwverId(long id){
        return resumePars.findAllByOvnerId(id).get();
    }

    public void save(Resume newResume){
        resumePars.save(newResume);
    }

    public List<String> getPosition(long id){ return resumePars.findAllPositionsByOvnerId(id).get();}
    public List<Long> getId(long id){ return resumePars.findAllIdsByOvnerId(id).get();}

    public List<Resume> search(String position,
                               String skills,
                               int workYears,
                               Double expectedSalary){
        return resumePars.findAll(position, skills, workYears, expectedSalary).get();
    }

    public List<Resume> searchByPosition(String position){
        return resumePars.findAllByPosition(position).get();
    }
}
