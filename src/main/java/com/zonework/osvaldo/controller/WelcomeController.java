package com.zonework.osvaldo.controller;

import com.zonework.osvaldo.service.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class WelcomeController {

    private final Result result;

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        var application = result.application();
        modelAndView.addObject("hasVote", application);
        modelAndView.setViewName("/index");
        return modelAndView;
    }

    @GetMapping("/resultado")
    public ModelAndView result(ModelAndView modelAndView) {
        modelAndView.setViewName("/result");
        return modelAndView;
    }

    @PostMapping("/votar/trancoso")
    public String truncoso() {
        result.includeVoteTruncoso();
        return "redirect:/";
    }

    @PostMapping("/votar/bonito")
    public String bonito() {
        result.includeVoteBonito();
        return "redirect:/";
    }

    @PostMapping("/votar/gramado")
    public String gramado(ModelAndView modelAndView) {
        result.includeVoteGramado();
        return "redirect:/";
    }

    @PostMapping("/admin/iniciar")
    public String start() {
        result.init();
        result.setPlaces();
        return "redirect:/admin";
    }

    @PostMapping("/admin/mostrar-resultado")
    public String resultado() {
        result.result();
        return "redirect:/admin";

    }

    @PostMapping("/admin/stop")
    public String stop() {
        result.stop();
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public ModelAndView admin(ModelAndView modelAndView) {
        modelAndView.setViewName("/admin");
        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
