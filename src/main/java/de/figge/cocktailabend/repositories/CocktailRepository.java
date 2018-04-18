package de.figge.cocktailabend.repositories;

import de.figge.cocktailabend.entities.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    public Cocktail findByNumberAndJumbo(int number, boolean jumbo);
    public List<Cocktail> findAllByOrderByNumberAscJumboAsc();
    public List<Cocktail> findAllByJumboOrderByNumber(boolean jumbo);
    public List<Cocktail> findFirst10ByJumboOrderByDateAsc(boolean jumbo);
    public List<Cocktail> findFirst5ByJumboOrderByDateAsc(boolean jumbo);
    @Query("SELECT max(cocktail.date) FROM Cocktail cocktail")
    public Date getMaxDate();
    @Query("SELECT min(cocktail.date) FROM Cocktail cocktail")
    public Date getMinDate();
    public List<Cocktail> findAllByDateBetween(Date start, Date end);
    public List<Cocktail> findTop10ByOrderByCalledDesc();
}
