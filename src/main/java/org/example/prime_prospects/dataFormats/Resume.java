package org.example.prime_prospects.dataFormats;

import java.util.ArrayList;

public class Resume {
    public Long id;
    public String name;
    public String position;
    public int workYears;
    public Double expectedSalary;
    public String skills;
    public String aboutMe;
    public String additionalSkills;
    public String site;
    public String contactsOwner;

    public Resume(String id,
                  String name,
                  String position,
                  String workYears,
                  String expectedSalary,
                  String skills,
                  String aboutMe,
                  String additionalSkills,
                  String site,
                  String contactsOwner){
        this.id = Long.parseLong(id);
        this.name = name;
        this.position = position;
        this.workYears = Integer.parseInt(workYears);
        this.expectedSalary = Double.parseDouble(expectedSalary);
        this.skills = skills;
        this.aboutMe = aboutMe;
        this.additionalSkills = additionalSkills;
        this.site = site;
        this.contactsOwner = contactsOwner;
    }

    public ArrayList<String> getStartInfo(){
        ArrayList<String> startInfo = new ArrayList<>();
        startInfo.add(position);
        startInfo.add("" + workYears);
        startInfo.add("" + expectedSalary);
        return startInfo;
    }
}

