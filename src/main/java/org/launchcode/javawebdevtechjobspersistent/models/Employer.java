package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Employer extends AbstractEntity {

    @OneToMany
    @JoinColumn
    private List<Job> jobs = new ArrayList<>();

    @NotBlank(message = "Location?!!!")
    @Size(min = 3, max = 50, message = "Must be between 3 & 50 characters.")
    private String location;

    public Employer(String location){this.location = location;}
    public Employer() {}

    public String getLocation(){return location;}
    public void setLocation(String location){this.location = location;}
}
