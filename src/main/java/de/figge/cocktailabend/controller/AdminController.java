package de.figge.cocktailabend.controller;

import de.figge.cocktailabend.entities.Cocktail;
import de.figge.cocktailabend.repositories.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/",
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
}
