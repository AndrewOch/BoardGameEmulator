package ru.kpfu.itis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.models.entities.Game;
import ru.kpfu.itis.services.PlayService;
import ru.kpfu.itis.services.interfaces.GamesService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/play")
public class PlayController {

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;

    @Autowired
    private PlayService playService;

    @GetMapping
    public ModelAndView getPlayPage(Authentication authentication, Long currentPlayGameId) {
        if (authentication == null) return new ModelAndView("redirect:/auth");
        if (currentPlayGameId == null) return new ModelAndView("redirect:/games");

        Game game = gamesService.findGameById(currentPlayGameId);
        playService.setDecks(game.getDecks());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(game);
        modelAndView.setViewName("play");
        return modelAndView;
    }

    @PostMapping("/take_card")
    public ModelAndView takeCard(Authentication authentication, Long deckToTakeCardFrom, HttpServletResponse response) throws IOException {
        if (authentication == null) new ModelAndView("redirect:/auth");

        if (deckToTakeCardFrom != null) {
            Objects.requireNonNull(playService.getDeckById(deckToTakeCardFrom)).takeCard();
            String json = new ObjectMapper().writeValueAsString(playService.getDeckById(deckToTakeCardFrom));
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
        return null;
    }

    @PostMapping("/shuffle_deck")
    public ModelAndView shuffleDeck(Authentication authentication, Long deckToShuffle, HttpServletResponse response) throws IOException {
        if (authentication == null) return new ModelAndView("redirect:/auth");

        if (deckToShuffle != null) {
            playService.shuffleDeck(deckToShuffle);
            String json = new ObjectMapper().writeValueAsString(playService.getDeckById(deckToShuffle));
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
        return null;
    }
}
