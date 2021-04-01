package com.panpal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "https://beeware319fe.azurewebsites.net")
@RestController
@RequestMapping(path="/")
public class LoginController {
    @RequestMapping("/")
    public ModelAndView helloWorld() {
        return  new ModelAndView(new RedirectView("http://localhost:3000/"));
    }

    @RequestMapping("/logout")
    public ModelAndView loginOut(HttpSession session){
      session.invalidate();
      return  new ModelAndView(new RedirectView("http://localhost:3000/"));
  }
}
