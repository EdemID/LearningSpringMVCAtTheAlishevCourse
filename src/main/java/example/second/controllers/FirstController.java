package example.second.controllers;

import example.second.Calculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/calculator")
    public String calculatorPage(@RequestParam(value = "a", required = false) String a,
                                  @RequestParam(value = "action", required = false) String action,
                                  @RequestParam(value = "b", required = false) String b,
                                  Model model) {
        model.addAttribute("message", a + " " + action + " " + b + " = " + Calculator.performAnOperation(a, action, b));
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodbyePage() {
        return "first/goodbye";
    }
}
