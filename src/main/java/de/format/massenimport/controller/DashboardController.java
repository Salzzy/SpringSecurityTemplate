package de.format.massenimport.controller;

import de.format.massenimport.entity.User;
import de.format.massenimport.service.SecurityService;
import de.format.massenimport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;


    @GetMapping("/dashboard")
    public String dashboard(Model theModel)
    {
        // Send user to the Frontend
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        theModel.addAttribute("user", user);

        return "app/dashboard";
    }


    @GetMapping("/login")
    public String login() {
        return "app/login";
    }


    @GetMapping("/register")
    public String registration(Model model)
    {
        model.addAttribute("userForm", new User());
        return "app/registration";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm)
    {
        // Create new UserInfo Object and save it
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }

}
