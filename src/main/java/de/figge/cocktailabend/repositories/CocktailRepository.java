package de.figge.cocktailabend.repositories;

import de.figge.cocktailabend.entities.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    public Cocktail findByNumberAndJumbo(int number, boolean jumbo);
    public List<Cocktail> findAllByOrderByNumberAscJumboAsc();
    public List<Cocktail> findAllByJumboOrderByNumber(boolean jumbo);
    public List<Cocktail> findFirst10ByJumboOrderByDateAsc(boolean jumbo);
    public List<Cocktail> findFirst5ByJumboOrderByDateAsc(boolean jumbo);
}
