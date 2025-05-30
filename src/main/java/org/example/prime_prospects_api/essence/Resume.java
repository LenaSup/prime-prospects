package org.example.prime_prospects_api.essence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ovner_id")
    private Long ovnerId;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "work_years")
    private Integer workYears;

    @Column(name = "expected_salary")
    private Double expectedSalary;

    @Column(name = "skills", length = 250)
    private String skills;

    @Column(name = "about_me", length = 1000)
    private String aboutMe;

    @Column(name = "additional_skill", length = 250)
    private String additionalSkills;

    public Long getOvner_Id() {
        return ovnerId;
    }

    public void setOvner_Id(
            Long ovner_Id) {
        this.ovnerId = ovner_Id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWorkYears() {
        return workYears;
    }

    public void setWorkYears(Integer workYears) {
        this.workYears = workYears;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(Double expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAdditionalSkills() {
        return additionalSkills;
    }

    public void setAdditionalSkills(String additionalSkills) {
        this.additionalSkills = additionalSkills;
    }

    public String toJson(){
        return  "{"
                + "\"id\":\"" + id + "\","
                + "\"position\":\"" + position + "\","
                +  "\"workYears\":\"" + workYears + "\","
                + "\"expectedSalary\":\"" + expectedSalary + "\","
                +  "\"skills\":\"" + skills + "\","
                + "\"aboutMe\":\"" + aboutMe + "\","
                +  "\"additionalSkills\":\"" + additionalSkills + "\","
                + "}";
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", ovner_Id=" + ovnerId +
                ", position='" + position +
                ", workExperienceYears=" + workYears +
                ", expectedSalary=" + expectedSalary +
                ", skills=" + skills +
                ", aboutMe='" + aboutMe +
                ", additionalSkills=" + additionalSkills +
                '}';
    }
}
