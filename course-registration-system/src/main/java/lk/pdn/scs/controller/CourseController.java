package lk.pdn.scs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CourseController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/courses")
    public String showCourses(Model model, HttpSession session) {
        if (session.getAttribute("studentId") == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("courses", jdbcTemplate.queryForList("SELECT * FROM courses"));
        return "courses";
    }
    
    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable int courseId, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
            return "redirect:/login";
        }
        
        // Check if already registered
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND course_id = ?",
            Integer.class, studentId, courseId);
        
        if (count == 0) {
            jdbcTemplate.update(
                "INSERT INTO registrations (student_id, course_id) VALUES (?, ?)",
                studentId, courseId);
        }
        
        return "redirect:/courses";
    }
}