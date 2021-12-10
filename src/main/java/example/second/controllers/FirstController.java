package example.second.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        System.out.println("Hello, " + name + " " + surname);

        return "first/hello";
    }

    @GetMapping("/helloSecond")
    public String helloPageSecond(@RequestParam("name") String name,
                                  @RequestParam("surname") String surname) {

        System.out.println("Hello, " + name + " " + surname);

        return "first/hello";
    }

    @GetMapping("/helloThird")
    public String helloPageSecond(@RequestParam(value = "name", required = false) String name) {

        System.out.println("Hello, " + name);

        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodbyePage() {
        return "first/goodbye";
    }
}
