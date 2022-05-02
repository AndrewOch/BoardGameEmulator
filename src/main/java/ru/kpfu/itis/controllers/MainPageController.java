package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.models.dtos.GameDto;
import ru.kpfu.itis.models.entities.Game;
import ru.kpfu.itis.models.entities.User;
import ru.kpfu.itis.services.interfaces.GamesService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainPageController {

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;

    @GetMapping
    public ModelAndView getMainPage(Authentication authentication) {
        if (authentication == null) return new ModelAndView("redirect:/auth");

        ModelAndView modelAndView = new ModelAndView();
        User user = (User) authentication.getPrincipal();
        modelAndView.addObject("user", user);
        List<Game> games = gamesService.findAllGames();
        modelAndView.addObject("games", games);
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @PostMapping("/play_game")
    public ModelAndView playGame(Long currentPlayGameId) {
        ModelAndView modelAndView = new ModelAndView();
        if (currentPlayGameId != null) {
            modelAndView.addObject("currentPlayGameId", currentPlayGameId);
            modelAndView.setViewName("redirect:/play");
            return modelAndView;
        }
        return modelAndView;
    }
}
