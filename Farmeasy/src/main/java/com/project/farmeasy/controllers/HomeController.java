package com.project.farmeasy.controllers;

import com.project.farmeasy.entities.User;
import com.project.farmeasy.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "About";
    }

    @GetMapping("/faq")
    public String faq() {
        return "FAQ";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                               @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model) {
        try {

            /*if (!agreement) {
                System.out.println("Please select the terms and conditions");
                throw new Exception("Please select the terms and conditions");
            }*/

            System.out.println(user.getPassword());

            if (bindingResult.hasErrors()) {
                System.out.println("ERROR " + bindingResult.toString());
                model.addAttribute("user", user);
                return "register";
            }

            User saved = userService.saveUser(user);
            model.addAttribute("user", new User());

            return "FarmerLogin";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            return "register";
        }
    }

    @GetMapping("/farmerLogin")
    public String farmerLogin(Model model) {
        return "FarmerLogin";
    }

    @PostMapping("/do_login")
    public String authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        try {
            System.out.println("Hello");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username, password
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            User user = (User) userDetails;

            model.addAttribute("user", user);

            System.out.println("Hello");
            return "redirect:/user/home";
        } catch (BadCredentialsException e) {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "FarmerLogin";
        }
    }

}
