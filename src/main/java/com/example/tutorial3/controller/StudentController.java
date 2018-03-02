package com.example.tutorial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;
import com.example.tutorial3.model.StudentModel;


@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController(){
		studentService = new InMemoryStudentService();
	}
	@Autowired
    StudentService studentDAO;
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm, 
			@RequestParam(value = "name",required = true) String name, 
			@RequestParam(value = "gpa", required = true) double gpa){
		StudentModel student = new StudentModel(npm, name, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
	@RequestMapping("/student/view")
	public String view (Model model, @RequestParam(value = "npm", required = true) String npm){
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		return "view";
	}
	
	@RequestMapping("/student/view/{npm}")
	public String viewPath (Model model, @PathVariable String npm){
		StudentModel student = studentService.selectStudent(npm);
		if (student != null) {
			model.addAttribute("student", student);
			return "view";
		}else {
			model.addAttribute("npm", npm);
			return "not-found";
		}
	}
	
//	@RequestMapping("/student/view/{npm}")
//	public String viewPath (Model model, @PathVariable String npm){
//		StudentModel student = studentDAO.selectStudent (npm);
//		if (student != null) {
//			model.addAttribute("student", student);
//			return "view";
//		}else {
//			model.addAttribute("npm", npm);
//			return "not-found";
//		}
//	}
	
	@RequestMapping("/student/viewall")
	public String view (Model model){
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
	
	@RequestMapping("/student/delete/{npm}")
	public String delete (Model model, @PathVariable String npm){
		StudentModel student = studentService.selectStudent(npm);
		if (student != null) {
			studentService.deleteStudent(npm);
			return "delete";
		}else {
			return "not-found";
		}
		
	}
}