package com.capgemini.web;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.model.Person;

@Controller
public class PersonController {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @RequestMapping(value = "/addPerson.htm", method = RequestMethod.POST)
    public ModelAndView addPerson(Person p, Errors errors) {
        if (errors.hasErrors()) {
            ModelAndView mav = new ModelAndView("addPerson");
            mav.addObject("errors", errors);
            return mav;
        }
        entityManager.persist(p);
        return new ModelAndView("redirect:/allPersons.htm");
    }

    @RequestMapping(value = "/addPerson.htm")
    public String addPerson() {
        return "addPerson";
    }

    @RequestMapping("/allPersons.htm")
    public ModelAndView allPersons() {
        List<Person> persons = entityManager.createQuery("from Person", Person.class).getResultList();
        ModelAndView mav = new ModelAndView("allPersons");
        mav.addObject("persons", persons);
        return mav;
    }

}
