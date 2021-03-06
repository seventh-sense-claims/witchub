package witchub.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import witchub.com.models.User;
import witchub.com.services.SecurityService;
import witchub.com.services.UserService;

import java.security.Principal;

@Controller
public class LoginController {
    UserService userService;
    SecurityService securityService;

    @Autowired
    public LoginController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new User());
        return "login";
    }

    @RequestMapping("/welcome")
    public String welcome(Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        if(user.getRole().equals("ADMIN")){
            return "redirect:/admin";
        }
        else if(user.getRole().equals("SELLER")){
            System.out.println("seller");
            return "redirect:/self";
        }
        model.addAttribute("username",securityService.findLoggedInUsername());
        System.out.println(securityService.findLoggedInUsername());
        return "redirect:/homepage";
    }
}
