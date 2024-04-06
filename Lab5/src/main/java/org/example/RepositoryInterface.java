package org.example;
import java.util.Optional;
public interface RepositoryInterface {
    Optional<Mage> findByName(String name);
    void delete(String name);
    void save(Mage mage);
}