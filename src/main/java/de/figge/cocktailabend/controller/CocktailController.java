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
@RequestMapping("/cocktailabend")
@CrossOrigin(origins = "*")
public class CocktailController {
    @Autowired
    private CocktailRepository cocktailRepository;

    @RequestMapping(value = "/",
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

    @RequestMapping(value = "/count/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNumberOfCocktails() {
        int jumbo = cocktailRepository.findAllByJumboOrderByNumber(true).size();
        int normal = cocktailRepository.findAllByJumboOrderByNumber(false).size();
        return ResponseEntity.ok((jumbo * 3) + normal);
    }
}
