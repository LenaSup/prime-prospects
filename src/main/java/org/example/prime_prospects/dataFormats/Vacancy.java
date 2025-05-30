package org.example.prime_prospects.dataFormats;

import java.util.ArrayList;
import java.util.List;

public class Vacancy {
    public Long id;
    public String position;
    public int workExperienceYears;
    public Double salary;
    public String requirements;
    public String whatWeOffer;
    public String additionalSkills;
    public String employmentType;
    public String schedule;
    public int workingHours;
    public String address;
    public String owner;
    public String owner_description;
    public String contactsOwner;

    public Vacancy(String id,
                        String position,
                        String workExperienceYears,
                        String salary,
                        String requirements,
                        String whatWeOffer,
                        String additionalSkills,
                        String employmentType,
                        String schedule,
                        String workingHours,
                        String address,
                        String owner,
                        String owner_description,
                        String contactsOwner){

        this.id = Long.parseLong(id);
        this.position = position;
        this.workExperienceYears = Integer.parseInt(workExperienceYears);
        this.salary = Double.parseDouble(salary);
        this.requirements = requirements;
        this.whatWeOffer = whatWeOffer;
        this.additionalSkills = additionalSkills;
        this.employmentType = employmentType;
        this.schedule = schedule;
        this.workingHours = Integer.parseInt(workingHours);
        this.address = address;
        this.owner = owner;
        this.owner_description = owner_description;
        this.contactsOwner = contactsOwner;
    }

    public ArrayList<String> getStartInfo(){
        ArrayList<String> startInfo = new ArrayList<>();
        startInfo.add(position);
        startInfo.add(owner);
        startInfo.add("" + workExperienceYears);
        startInfo.add("" + salary);
        return startInfo;
    }
}
