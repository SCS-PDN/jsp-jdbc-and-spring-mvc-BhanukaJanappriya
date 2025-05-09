package lk.pdn.scs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    
	@PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        
        String sql = "SELECT * FROM students WHERE email = ? AND password = ?";
        try {
            jdbcTemplate.queryForObject(sql, new Object[]{email, password}, (rs, rowNum) -> {
                session.setAttribute("studentId", rs.getInt("student_id"));
                session.setAttribute("studentName", rs.getString("name"));
                return null;
            });
            return "redirect:/courses";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}