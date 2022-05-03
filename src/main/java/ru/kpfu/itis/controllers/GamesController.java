package ru.kpfu.itis.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.models.dtos.GameDto;
import ru.kpfu.itis.models.entities.Game;
import ru.kpfu.itis.models.entities.User;
import ru.kpfu.itis.models.forms.GameForm;
import ru.kpfu.itis.services.interfaces.GamesService;

import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;

    @GetMapping
    public ModelAndView getGamesPage(Authentication authentication) {
        if (authentication == null) return new ModelAndView("redirect:/auth");

        ModelAndView modelAndView = new ModelAndView();
        User user = (User) authentication.getPrincipal();
        modelAndView.addObject("username", user.getUsername());
        List<Game> games = gamesService.findGamesByUserId(user.getId());
        modelAndView.addObject("games", games);
        modelAndView.setViewName("games");
        return modelAndView;
    }

    @GetMapping("/play_game/{currentPlayGameId}")
    public ModelAndView playGame(@PathVariable Long currentPlayGameId) {
        ModelAndView modelAndView = new ModelAndView();
        if (currentPlayGameId != null) {
            modelAndView.addObject("currentPlayGameId", currentPlayGameId);
            modelAndView.setViewName("redirect:/play");
            return modelAndView;
        }
        return modelAndView;
    }

    @GetMapping("/edit_game/{currentEditGameId}")
    public ModelAndView editGame(@PathVariable Long currentEditGameId) {
        ModelAndView modelAndView = new ModelAndView();
        if (currentEditGameId != null) {
            modelAndView.addObject("currentEditGameId", currentEditGameId);
            modelAndView.setViewName("redirect:/creator");
            return modelAndView;
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createGame(GameDto gameDto, Authentication authentication) {
        if (authentication == null) new ModelAndView("redirect:/auth");
        User user = (User) authentication.getPrincipal();
        String name = gameDto.getGameName();
        String description = gameDto.getGameDescription();

        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(description)) {
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
