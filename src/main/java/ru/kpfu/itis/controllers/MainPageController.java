package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.models.dtos.GameDto;
import ru.kpfu.itis.models.entities.*;
import ru.kpfu.itis.models.forms.GameForm;
import ru.kpfu.itis.services.interfaces.GamesService;
import ru.kpfu.itis.services.interfaces.UsersService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;

    @GetMapping("/")
    private ModelAndView gamesPage(Authentication authentication, GameDto gameDto) throws ServletException, IOException {
        ModelAndView modelAndView = new ModelAndView();

        if (authentication == null) {
            modelAndView.setViewName("redirect:/auth");
            return modelAndView;
        }
        User user = (User) authentication.getPrincipal();
        modelAndView.addObject("user", user);

        List<Game> games = gamesService.findAllGames();
        modelAndView.addObject("games", games);

        String currentPlayGameId = gameDto.getCurrentPlayGameId();
        if (currentPlayGameId != null) {
            modelAndView.addObject("currentPlayGameId", Long.valueOf(currentPlayGameId));
            modelAndView.setViewName("redirect:/play");
            return modelAndView;
        }
        modelAndView.setViewName("main");
        return modelAndView;
    }

}
