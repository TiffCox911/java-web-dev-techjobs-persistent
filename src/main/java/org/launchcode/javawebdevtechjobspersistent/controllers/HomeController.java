package org.launchcode.javawebdevtechjobspersistent.controllers;


import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam(required = false) Integer employerId,
                                    @RequestParam(required = false) List<Integer> skills) {
        if(skills == null){
            errors.rejectValue("skills", "NullPointerException", "Job requires a skill.");}

        if(employerId == null){
            errors.rejectValue("employer", "NullPointerException",
                    "Employer is required");}

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("employers",employerRepository.findAll());
            model.addAttribute("skills",skillRepository.findAll());
            return "add";
        }
        Optional<Employer> response = employerRepository.findById(employerId);
        if(response.isEmpty()){
            model.addAttribute("title", "Invalid Employer Id:" + employerId);
        }
        else {
            List<Skill> skillObject = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillObject);
            newJob.setEmployer(response.get());
            jobRepository.save(newJob);
        }
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> response =jobRepository.findById(jobId);

        if(response.isPresent()){
            model.addAttribute("title", response.get().getName());
            model.addAttribute("job",response.get());
        }

        return "view";
    }

}
