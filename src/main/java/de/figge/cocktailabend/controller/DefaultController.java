package de.figge.cocktailabend.controller;

import de.figge.cocktailabend.entities.Cocktail;
import de.figge.cocktailabend.entities.Statistic;
import de.figge.cocktailabend.repositories.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/isReady")
    public String isReady() {
        return "/isReady";
    }

    @GetMapping("/statistics")
    public String statistics() {
        return "/statistics";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/console")
    public String console() {
        return "/console";
    }

    @GetMapping("/cocktails")
    public String cocktails() {
        return "/cocktails";
    }

    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> isAuthenticated() {
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/admin/create",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Cocktail cocktail) {
        if (cocktailRepository.findByNumberAndJumbo(cocktail.getNumber(), cocktail.isJumbo()) == null) {
            cocktail.incCalled();
            cocktail = cocktailRepository.save(cocktail);
        } else {
            cocktail = cocktailRepository.findByNumberAndJumbo(cocktail.getNumber(), cocktail.isJumbo());
            cocktail.incCalled();
            cocktail = cocktailRepository.save(cocktail);
        }
        if (cocktail.isJumbo()) {
            template.convertAndSend("/topic/jumbos", cocktail);
        } else {
            template.convertAndSend("/topic/cocktails", cocktail);
        }
        return ResponseEntity.ok(cocktail);
    }

    @RequestMapping(value = "/admin/clear",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> clear() {
        cocktailRepository.deleteAll();
        return ResponseEntity.ok("database cleared");
    }

    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Cocktail> cocktails = cocktailRepository.findAllByOrderByNumberAscJumboAsc();
        return ResponseEntity.ok(cocktails);
    }

    @RequestMapping(value = "/statistics/data",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatistics() {
        if (cocktailRepository.count() == 0) {
            return ResponseEntity.ok(-1);
        }
        List<Statistic> statistics = new LinkedList<Statistic>();
        long startTime = cocktailRepository.getMinDate().getTime();
        startTime = startTime - (startTime % (5 * 60 * 1000));
        long endTime = cocktailRepository.getMaxDate().getTime();
        endTime = endTime - (endTime % (5 * 60 * 1000));

        for (long time = startTime; time <= endTime; time = time + (5 * 60 * 1000)) {
            int jumboCount = 0;
            int cocktailCount = 0;

            for (Cocktail cocktail : cocktailRepository.findAllByDateBetween(new Date(time), new Date(time + (5 * 60 * 1000) - 1))) {
                if (cocktail.isJumbo()) {
                    jumboCount++;
                } else {
                    cocktailCount++;
                }
            }
            statistics.add(new Statistic(new Date(time), jumboCount, cocktailCount));
        }
        return ResponseEntity.ok(statistics);
    }

    @RequestMapping(value = "/statistics/mostCalled",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMostCalled() {
        List<Cocktail> mostCalled = cocktailRepository.findTop10ByOrderByCalledDesc();
        if (mostCalled == null || mostCalled.isEmpty()) {
            return ResponseEntity.ok(-1);
        } else {
            return ResponseEntity.ok(mostCalled);
        }
    }

    @RequestMapping(value = "/all/{jumbo}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(@PathVariable boolean jumbo) {
        return ResponseEntity.ok(cocktailRepository.findAllByJumboOrderByNumber(jumbo));
    }

    @RequestMapping(value = "/first/jumbo",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFirstJumbos() {
        List<Cocktail> cocktails = cocktailRepository.findFirst5ByJumboOrderByDateAsc(true);
        return ResponseEntity.ok(cocktails);
    }

    @RequestMapping(value = "/first/cocktail",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFirstCocktails() {
        List<Cocktail> cocktails = cocktailRepository.findFirst10ByJumboOrderByDateAsc(false);
        return ResponseEntity.ok(cocktails);
    }

    @RequestMapping(value = "/ready/{jumbo}/{number}",
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
