package example.second.controller;

import example.second.dao.UserDAO;
import example.second.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDAO userDAO;

    @Autowired
    public AdminController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    public String adminPage(Model model, @ModelAttribute("user") User user) { // Пустой объект
        model.addAttribute("users", userDAO.index());

        return "adminPage";
    }

    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute("user") User user) {
        System.out.println(user.getId());

        return "redirect:/users";
    }
}
