package com.springb.contactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;

import com.springb.contactmanager.dao.ContactRepository;
import com.springb.contactmanager.dao.UserRepository;
import com.springb.contactmanager.entity.Contact;
import com.springb.contactmanager.entity.User;
import com.springb.contactmanager.helper.Message;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    private Optional<Contact> findById;

    // common data which shared among the various user URLs
    @ModelAttribute
    public void commomData(Model model, Principal principal) {
        String usernameString = principal.getName();
        User user = this.userRepository.getUserByUserName(principal.getName());
        model.addAttribute("user", user);
    }

    // user-home page
    @RequestMapping(value = "index")
    public String dashBoard(Model model, Principal principal) {
        String username = principal.getName();
        // get the user information for userEmail

        User user = this.userRepository.getUserByUserName(principal.getName());
        model.addAttribute("user", user);
        System.out.println(username);
        return "normal/user_dashboard";
    }

    // add form handler html-page
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add contact");
        // sending contact object , to bind the contact data with the user, 2-ways
        // data-binding
        model.addAttribute("contact", new Contact());
        return "normal/open_add_contact_form";
    }

    // process the contact form-data, adding contact to database
    @PostMapping("/addingcontact")
    public String addContactInfromation(
            @Valid @ModelAttribute Contact contact,
            BindingResult bindingResult,
            @RequestParam("contssImage") MultipartFile file,
            Model model,
            HttpSession httpSession,
            Principal principal) {

        // check the form has any error
        if (bindingResult.hasErrors()) {
            System.out.println("add contact form has error");
            // bindingResult.addError(null)
            // model.addAttribute("form-error");
            return "normal/open_add_contact_form";
        } else {
            System.out.println("inside the controller");
            System.out.println(contact);
            System.out.println(file.getOriginalFilename());
            // adding file name to contact-field

            // System.out.println(file.getOriginalFilename());
            // System.out.println(file.getOriginalFilename());
            // System.out.println("this is contact information");
            // System.out.println(contact.getContImage());
            //
            try {
                String email = principal.getName();

                if (file.isEmpty()) {
                    // file is empty
                    System.out.println("$$$ image is empty ");
                    contact.setContImage("default.png");
                } else {
                    contact.setContImage(file.getOriginalFilename());
                    File staticImageFileDir = new ClassPathResource("static/images").getFile();
                    Path path = Paths
                            .get(staticImageFileDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("$$$ image is uploaded");
                }
                // BeanDefinition
                // get the user
                User user = this.userRepository.getUserByUserName(email);
                // add user to contact-table
                contact.setUser(user);

                // add/update contact to user table
                user.getContactList().add(contact);
                // adding data to database

                // adding updated user information with contact is updated in the database
                this.userRepository.save(user);
                model.addAttribute("status-contact", "data saved in database");
                httpSession.setAttribute("message", new Message("Data Saved in Database", "success"));

            } catch (Exception e) {
                System.out.println("Error message :" + e.getMessage());
                httpSession.setAttribute("message", new Message("Something went Wrong!!, Try Again !!", "error"));
            }

        }

        return "normal/open_add_contact_form";

    }

    // show contact Page- view all the contact of User
    // 5 items per page
    // current page [0]th page
    @GetMapping("/show_contact/{pageno}")
    public String showContact(@PathVariable("pageno") int pageNo, Model model, Principal principal) {
        model.addAttribute("title", "ViewContact");
        System.out.println("inside the show all contact model");
        User user = this.userRepository.getUserByUserName(principal.getName());

        Pageable currentPageWindow = PageRequest.of(pageNo, 5);

        // List<Contact> contactList =
        // this.contactRepository.findContactByUserId(user.getUserID());
        Page<Contact> pageWiseContact = this.contactRepository.findContactByUserId(user.getUserID(), currentPageWindow);

        // System.out.println("------------ "+ contactList.size());
        // model.addAttribute("contacts", contactList);
        model.addAttribute("current_page", pageNo);
        model.addAttribute("contacts", pageWiseContact);
        model.addAttribute("total_pages", pageWiseContact.getTotalPages());

        return "normal/showcon";
    }

    // this is testing Controller
    @GetMapping("/test")
    public String test(Model model) {
        System.out.println("inside the show all Test controller");
        return "normal/test";

    }

    // show details of each contact
    @GetMapping("/user_contact/{userid}")
    public String getUserContact(Model model, @PathVariable("userid") int userID, Principal principal) {
        // check weather the logged user can get his contacts information
        String loggedInUserEmail = principal.getName();
        User user = this.userRepository.getUserByUserName(loggedInUserEmail);

        model.addAttribute("title", "User Contact information");

        Optional<Contact> contactOptional = this.contactRepository.findById(userID);
        model.addAttribute("userid", userID);
        Contact contact = contactOptional.get();
        if (user.getUserID() == contact.getUser().getUserID()) {
            model.addAttribute("contacts", contact);
        } else {
            // model.addAttribute("contacts", new Contact());
        }

        System.out.println(contact);
        // System.out.println(contact);
        // if (contact.isPresent()) {
        // Contact contact2 = contact.get();
        // System.out.println(contact2);

        // }else{
        // System.out.println("no record foound of the given id");
        // }

        return "normal/user_information_page";
    }

    /**
     * @param mode
     * @param contactID
     * @param principal
     */

    @GetMapping("/delete_user/{contactid}")
    public String deleteContact(Model mode, @PathVariable("contactid") int contactID, Principal principal,
            HttpSession httpSession) {
        Optional<Contact> contactOptional = this.contactRepository.findById(contactID);
        Contact contact = contactOptional.get();
        User user = this.userRepository.getUserByUserName(principal.getName());

        if (contact.getUser().getUserID() == user.getUserID()) {
            // this.contactRepository.delete(contact);
            user.getContactList().remove(contact);
            this.userRepository.save(user);
            httpSession.setAttribute("message", new Message("cotnact has been deleted ", "success"));
        }

        // User user = this.userRepository.getUserByUserName(principal.getName());
        // // check if the current-user is logged in User
        // // delect contact with contact-id
        // try {
        // this.contactRepository.deleteById(contactID);
        // } catch (Exception e) {
        // System.out.println("no contact is found, whith current id :"+ contactID);
        // }

        return "redirect:/user/show_contact/0";
    }

    // controller for update form
    @PostMapping("/update_contact/{contactid}")
    public String getUpdateForm(@PathVariable("contactid") int contactID, Model model, Principal principal) {
        model.addAttribute("title", "update User");
        // get the contact object from database
        // adding the object to modelAttribute
        Contact contact = this.contactRepository.findById(contactID).get();
        String userEmail = principal.getName();
        System.out.println(userEmail + " compare to " + contact.getUser().getUserEmail());
        if (contact.getUser().getUserEmail().equals(userEmail)) {
            model.addAttribute("contact", contact);
        } else {
        }
        return "normal/update_form";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/process_update_contact")
    public String updateContactHandler(@ModelAttribute Contact contact, @RequestParam("contssImage") MultipartFile file,
            HttpSession httpSession, Principal principal) {
        System.out.println(contact);

        Contact oldContactDetails = this.contactRepository.findById(contact.getContID()).get();
        try {
            if (!file.isEmpty()) {
                // file work
                // delete the old photo
                if (oldContactDetails.getContImage() != "") {
                    File deleteFile = new ClassPathResource("static/images").getFile();
                    // System.out.println("------------deleetFile " + deleteFile.getAbsolutePath());
                    File file1 = new File(deleteFile, oldContactDetails.getContImage());
                    // System.out.println("------------ file1 "+ file1.getAbsolutePath());
                    file1.delete();
                }

                // upload the new photo
                File locationDir = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(locationDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                contact.setContImage(file.getOriginalFilename());

            } else {
                // set the old contact detail to new contact detail
                contact.setContImage(oldContactDetails.getContImage());
            }

            // get contact & get user from current loggedin user , update user + contact
            String userEmail = principal.getName();
            User user = this.userRepository.getUserByUserName(userEmail);

            contact.setContImage(file.getOriginalFilename());
            contact.setUser(user);
            this.contactRepository.save(contact);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/user_contact/" + contact.getContID();
    }

// USER PROFILE PAGE SHOWN HERE
    @GetMapping("/user_profile")
    public String getProfilePage(Model model, Principal principal){
        model.addAttribute("title", "User Profile Page");
        
        User user  = this.userRepository.getUserByUserName(principal.getName());
        model.addAttribute("user", user);
        return "normal/user_profile";
    }

}