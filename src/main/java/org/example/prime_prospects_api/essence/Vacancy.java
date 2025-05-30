package org.example.prime_prospects_api.essence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ovner_Id")
    private Long ovnerId;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "work_experience_years")
    private Integer workExperienceYears;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "requirements", length = 1000)
    private String requirements;

    @Column(name = "what_we_offer", length = 1000)
    private String whatWeOffer;

    @Column(name = "additional_skill", length = 500)
    private String additionalSkills;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "working_hours")
    private int workingHours;

    @Column(name = "address")
    private String address;

    public String toJson(){
        return  "{"
                + "\"id\":\"" + id + "\","
                + "\"position\":\"" + position + "\","
                +  "\"workExperienceYears\":\"" + workExperienceYears + "\","
                + "\"salary\":\"" + salary + "\","
                +  "\"requirements\":\"" + requirements + "\","
                + "\"whatWeOffer\":\"" + whatWeOffer + "\","
                +  "\"additionalSkills\":\"" + additionalSkills + "\","
                + "\"employmentType\":\"" + employmentType + "\","
                +  "\"schedule\":\"" + schedule + "\","
                + "\"workingHours\":\"" + workingHours + "\","
                +  "\"address\":\"" + address + "\","
                + "}";
    }


    public Long getOvner_Id() {
        return ovnerId;
    }

    public void setOvner_Id(Long ovner_Id) {
        this.ovnerId = ovner_Id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getAdditionalSkills() {
        return additionalSkills;
    }

    public void setAdditionalSkills(String additionalSkills) {
        this.additionalSkills = additionalSkills;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getWhatWeOffer() {
        return whatWeOffer;
    }

    public void setWhatWeOffer(String whatWeOffer) {
        this.whatWeOffer = whatWeOffer;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getWorkExperienceYears() {
        return workExperienceYears;
    }

    public void setWorkExperienceYears(Integer workExperienceYears) {
        this.workExperienceYears = workExperienceYears;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", position='" + position+
        ", workExperienceYears=" + workExperienceYears +
                ", salary=" + salary +
                ", requirements='" + requirements+
        ", whatWeOffer='" + whatWeOffer +
        ", additionalSkills=" + additionalSkills +
                ", employmentType='" + employmentType +
        ", schedule='" + schedule +
        ", workingHours='" + workingHours +
        ", address='" + address +
        '}';
    }
}
