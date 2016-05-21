package com.aurora.rti.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(ModelMap model) {
    	
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String uname = auth.getName(); //get logged in username
        
        model.addAttribute("username", uname);
        return "pages/dashboard";
    }
    
    @RequestMapping(value = "/rti", method = RequestMethod.GET)
    public String rti(ModelMap model) {
    	
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String uname = auth.getName(); //get logged in username
        
        model.addAttribute("username", uname);
        return "pages/PTHome";
    }
}
