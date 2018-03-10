package de.figge.cocktailabend.controller;

import de.figge.cocktailabend.entities.Cocktail;
import de.figge.cocktailabend.repositories.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/cocktails")
    public String cocktails() {
        return "/cocktails";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @RequestMapping(value = "/admin/create",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Cocktail cocktail) {
        cocktail = cocktailRepository.save(cocktail);
        if (cocktail.isJumbo()) {
            template.convertAndSend("/topic/jumbos", cocktail);
        } else {
            template.convertAndSend("/topic/cocktails", cocktail);
        }
        return ResponseEntity.ok(cocktail);
    }

    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Cocktail> cocktails = cocktailRepository.findAllByOrderByNumberAscJumboAsc();
        return ResponseEntity.ok(cocktails);
    }

    @RequestMapping(value = "/ready/{number}/{jumbo}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> isReady(@PathVariable int number, @PathVariable boolean jumbo) {
        Cocktail cocktail = cocktailRepository.findByNumberAndJumbo(number, jumbo);
        return ResponseEntity.ok(cocktail != null);
    }

    @RequestMapping(value = "/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNumberOfCocktails() {
        int jumbo = cocktailRepository.findAllByJumboOrderByNumber(true).size();
        int normal = cocktailRepository.findAllByJumboOrderByNumber(false).size();
        return ResponseEntity.ok((jumbo * 3) + normal);
    }
}
