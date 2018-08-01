package pl.jstk.controller;

import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String INFO_TEXT = "Here You shall display information containing information about newly created TO";
    protected static final String WELCOME = "This is a welcome page";

    @GetMapping(value = {"/", "/home"})
    public String welcome(Model model) {
        model.addAttribute(ModelConstants.MESSAGE, WELCOME);
        model.addAttribute(ModelConstants.INFO, INFO_TEXT);
        return ViewNames.WELCOME;
    }

    @GetMapping(value = {"/login"})
    public String login(Model model) {
        return ViewNames.LOGIN;
    }

}
