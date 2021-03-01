package com.example.match_app.controller;

import java.util.List;

import com.example.match_app.domain.Employee;
import com.example.match_app.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

@Controller
public class HeloController {
  @Autowired
  EmployeeRepository employeeRepository;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    List<Employee> emplist = employeeRepository.findAll();
    model.addAttribute("emplist", emplist);
    return "index";
  }

}
