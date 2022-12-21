package com.springb.contactmanager.controller;

import java.security.Principal;
import java.util.Random;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springb.contactmanager.dao.UserRepository;
import com.springb.contactmanager.entity.User;
import com.springb.contactmanager.services.EmailService;

import net.bytebuddy.asm.Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName;

@Controller
public class ForgotController {
	
	private Random random = new Random(1000);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;

//	forgot password , enter register email 
	@GetMapping("/forgot")
	public String openEmailForm() {
		return "forgot_email_form";
	}
	

	@PostMapping("/send_otp")
	public String sendOtp(Model model,
			@RequestParam("email") String email,
			HttpSession session) {
		
		System.out.println(email);
		
//		generate 4-digit number
		
		int otp = random.nextInt(999999);
		System.out.println(otp);
		
		if(email != null) {
//			public boolean sendEmail(String subject, String message, String to){
			String subject = "Forgot Password";
			String message =""
					+ "<div style='border:1px solid #e2e2e2; padding:20px'>"
					+ "<h1>"
					+ "OTP :"
					+ "<b>"
					+ otp
					+ "</b>"
					+ "</h1>"
					+ "</div>";
			String to = email;
			
			boolean isMailSend = this.emailService.sendEmail(subject, message, to);
			if (isMailSend) {
				System.out.println("Mail has been sent");
//				save the OTP to the session variable or to the database
				session.setAttribute("otp", otp);
				session.setAttribute("email", email);
				
				return "verify_otp";
			}else {
				session.setAttribute("message", "Check your Email Address....");
				System.out.println("Mail not send");
				return "forgot_email_form";
			}
		}

		return "forgot_email_form";
	}
	
//	handler for change password
	@PostMapping("/change_password")
	public String changePassword(@ModelAttribute("newpassword") String newPassword,
			Principal principal,
			HttpSession httpSession) {
//		get the new-password & store in the database
		String email  = (String) httpSession.getAttribute("email");
		String newEncodedPassword = this.bCryptPasswordEncoder.encode(newPassword);
		
		User user = this.userRepository.getUserByUserName(email);
		user.setPassword(newEncodedPassword);
		this.userRepository.save(user);
		
		return "redirect:/signin?change=password change sucessfully";
	}
	
//	Handler for verify OTP
	@PostMapping("/verify_otp")
	public String verifyOTP(Model model,
			@RequestParam("otp") int OTP,
			HttpSession session) {
		
		
		int myOTP = (int) session.getAttribute("otp");
		String email = (String) session.getAttribute("email");
//		String sessionOTP = (String) session.getAttribute("otp");
		String output = "session OTP :"
				+ myOTP
				+ "  "
				+ "user OTP :"
				+ OTP;
		
		System.out.println(output);
		if (myOTP==OTP) {
			
			User user = this.userRepository.getUserByUserName(email);
			if (user==null) {
//				send Error Message
				System.out.println("user with this email is not present...");
				System.out.println(user);
				return "verify_otp";
			}else {
//				send Changed Password
//				user.setPassword(output)
				return "password_change_form";	
			}
			
			
		}else {
			session.setAttribute("message", "your have Entered Incorrect OTP");
			return "verify_otp";
		}
	}

}
