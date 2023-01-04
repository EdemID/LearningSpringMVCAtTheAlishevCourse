package example.second.controller;

import example.second.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {

    private final UserDAO userDAO;

    @Autowired
    public BatchController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    public String index() {
        return "batch/index";
    }

    @GetMapping("/batch")
    public String withBatch() {
        userDAO.testBatchUpdate();
        return "redirect:/users";
    }
}
