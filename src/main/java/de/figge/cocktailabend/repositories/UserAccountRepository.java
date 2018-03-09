package de.figge.cocktailabend.repositories;

import de.figge.cocktailabend.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    public UserAccount findByUsername(String username);
}
