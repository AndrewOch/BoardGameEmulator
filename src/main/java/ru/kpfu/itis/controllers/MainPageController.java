package ru.kpfu.itis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.dto.EditGameDto;
import ru.kpfu.itis.dto.GameDto;
import ru.kpfu.itis.dto.PlayDto;
import ru.kpfu.itis.models.entities.*;
import ru.kpfu.itis.models.form.CardForm;
import ru.kpfu.itis.models.form.CurrencyForm;
import ru.kpfu.itis.models.form.DeckForm;
import ru.kpfu.itis.models.form.GameForm;
import ru.kpfu.itis.services.CookieService;
import ru.kpfu.itis.services.interfaces.GamesService;
import ru.kpfu.itis.services.interfaces.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Controller
public class MainPageController {

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;
    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.GET, value = "/main")
    private ModelAndView gamesPage(Authentication authentication, GameDto gameDto) throws ServletException, IOException {
        ModelAndView modelAndView = new ModelAndView();

        if (authentication == null) {
            modelAndView.setViewName("redirect:/auth");
            return modelAndView;
        }
        User user = (User) authentication.getPrincipal();
        modelAndView.addObject("username", user.getUsername());

        List<Game> games = gamesService.findGamesByUserId(user.getId());
        modelAndView.addObject("games", games);

        String currentEditGameId = gameDto.getCurrentEditGameId();
        if (currentEditGameId != null) {
            modelAndView.addObject("currentEditGameId", Long.valueOf(currentEditGameId));
            modelAndView.setViewName("redirect:/creator");
            return modelAndView;
        }

        String currentPlayGameId = gameDto.getCurrentPlayGameId();
        if (currentPlayGameId != null) {
            modelAndView.addObject("currentPlayGameId", Long.valueOf(currentPlayGameId));
            modelAndView.setViewName("redirect:/play");
            return modelAndView;
        }

        modelAndView.setViewName("main");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/main")
    private ModelAndView createGame(GameDto gameDto, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        if (authentication == null) {
            return new ModelAndView("redirect:/auth");
        }
        User user = (User) authentication.getPrincipal();

        String name = gameDto.getGameName();
        String description = gameDto.getGameDescription();

        if (!name.equals("") && !description.equals("")) {
            GameForm gameForm = new GameForm(name, description, user);
            Game game = gamesService.addGame(gameForm);
            if (game != null) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("currentEditGameId", game.getId());
                modelAndView.setViewName("redirect:/creator");
                return modelAndView;
            }
        }
        return new ModelAndView("redirect:/games");
    }

}
