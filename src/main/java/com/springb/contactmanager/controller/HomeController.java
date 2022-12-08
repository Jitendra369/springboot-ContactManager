package com.springb.contactmanager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springb.contactmanager.dao.UserRepository;
import com.springb.contactmanager.entity.Contact;
import com.springb.contactmanager.entity.User;
import com.springb.contactmanager.helper.Message;

@Controller
public class HomeController {

//	database dependency 
    @Autowired
    UserRepository repository;

//   spring security dependency 
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test")
    @ResponseBody
    public String test(){

//        List<Contact> list = new ArrayList<>();
//        Contact contact = new Contact();
//        contact.setContEmail("contact@gmail.com");
//        list.add(contact);
//
//        User user = new User();
//        user.setUserEmail("jttukadu@gmail.com");
//        user.setPassword("andrid");
//        user.setContactList(list);
//        
////        Response
//        
//        repository.save(user);


        return "sucesses";
    }

    // home page Handler
    @GetMapping(path = "/")
    public String getHomePage(Model model){
        model.addAttribute("title", "Smart Contact manager");
        return "home";
    }

    // about page Handler
    @GetMapping(path = "about")
    public String getAboutPage(Model model){
        model.addAttribute("title", "About Page");
        return "about";
    }

    // signup page handler
    @GetMapping(path = "/signup")
    public String getSignUpPage(Model model){
        // send empty for user object for 2ways data-binding
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }
    
    
//   after hittinh /user , spring will invoke user_login page for validation 
    //   // signup page handler
    //    @GetMapping(path = "/user")
    //    public String getUserHomePage(Model model){
    //       model.addAttribute("title","User Home Page");
    //       System.out.println("inside the user page controller");

        
    //        return "user";
    //    }
    
    @GetMapping(path = "/signin")
    public String getUserPage(Model model) {
        model.addAttribute("title", "login");
    	return "user_login";
    }


    // @GetMapping(path = "/user/index")
    // public String getUserHomePage(Model model) {
    //     // model.addAttribute("title", "login");
    // 	return "user_home_page";
    // }

    // @PostMapping(params = "/do_register")
//  handle signup page from data 
    @RequestMapping(path = "/do_register", method = RequestMethod.POST)
//    binding result should comes after model 
    public String pocessSignPage(@Valid @ModelAttribute("user") User user,BindingResult bindingResult
    		, @RequestParam(value = "termscond", defaultValue = "false") boolean aggrement,
    		Model model, HttpSession session) {

        System.out.println("check list value :"+ aggrement);
      try {
        user.setUserRole("ROLE_USER");
        // enable user
        user.setUserStatus(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       
    	// if (!aggrement) { user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //     // System.out.println("please aggre terms & Conditions");
        //     throw new Exception("please aggre terms & Conditions");
        //     // return "signup";
        // }

        if (bindingResult.hasErrors()) {
            System.out.println("Error :");

            model.addAttribute("user",user);
            return "signup";
        }
            System.out.println("inside the do_register method ");
            System.out.println("agremment-vale : " + aggrement);
            System.out.println("user value "+ user );
            model.addAttribute("user", user);

            // save user in DataBase
           User resultUser = this.repository.save(user);
           model.addAttribute("message", new Message("sucessfully register!!","alert-success"));
           System.err.println(resultUser);
           return "signup";


      } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("user", user);
        // adding message to session object 
        session.setAttribute("message", new Message("something went wrong","alert-error"));
        return "signup";
      }
    }

}
