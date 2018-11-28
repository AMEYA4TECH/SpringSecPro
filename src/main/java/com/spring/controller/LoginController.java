package com.spring.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String init(Model model) {
		model.addAttribute("msg", "Please Enter Your Login Details");
		return "login";
	}
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
       // model.addAttribute("user", getPrincipal());
        return "admin";
    }
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String onLogoutSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session= httpServletRequest.getSession(false);
	    SecurityContextHolder.clearContext();
	         session= httpServletRequest.getSession(false);
	        if(session != null) {
	            session.invalidate();
	        }
	        for(Cookie cookie : httpServletRequest.getCookies()) {
	            cookie.setMaxAge(0);
	        }

		
		if (authentication != null && authentication.getDetails() != null) {
			try {
				httpServletRequest.getSession().invalidate();
				System.out.println("User Successfully Logout");
				// you can add more codes here when the user successfully logs
				// out,
				// such as updating the database for last active.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		// redirect to login
		// httpServletResponse.sendRedirect("/login");
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(Model model,
			@ModelAttribute("loginBean") LoginBean loginBean) {
		if (loginBean != null && loginBean.getUserName() != null
				& loginBean.getPassword() != null) {
			if (loginBean.getUserName().equals("chandra")
					&& loginBean.getPassword().equals("chandra123")) {
				model.addAttribute("msg", loginBean.getUserName());
				return "success";
			} else {
				model.addAttribute("error", "Invalid Details");
				return "login";
			}
		} else {
			model.addAttribute("error", "Please enter Details");
			return "login";
		}
	}
}
