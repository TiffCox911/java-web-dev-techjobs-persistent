package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Skill extends AbstractEntity {
    @ManyToMany(mappedBy = "skills")
    List<Job> jobs = new ArrayList<>();

    @NotBlank
    @Size(max = 255)
    private String descInfo;

    public Skill(String descInfo)
    {
        this.descInfo = descInfo;
    }

    public Skill(){}

    public String getDescInfo()
    {
        return descInfo;
    }

    public void setDescInfo(String descInfo)
    {
        this.descInfo = descInfo;
    }

    public List<Job> getJobs(){
        return jobs;
    }

}