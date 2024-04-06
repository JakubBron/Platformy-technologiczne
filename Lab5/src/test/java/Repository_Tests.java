import org.example.Mage;
import org.example.RepositoryInterface;
import org.example.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Repository_Tests {
    private RepositoryInterface sut = null;

    @BeforeEach
    public void init() {
        sut = new Repository(List.of(new Mage("Mage1", 1), new Mage("Mage3", 3)));;
    }

    @Test
    public void findByName_whenMageExists_shouldReturnMage() {
        var result = sut.findByName("Mage1");
        assertThat(result, isPresent());
    }
    @Test
    public void findByName_whenMageDoesNotExist_shouldReturnEmpty() {
        var result = sut.findByName("Mage2");
        assertThat(result, isEmpty());
    }
    @Test
    public void delete_whenMageExists_shouldDeleteMage() {
        sut.delete("Mage1");
        var result = sut.findByName("Mage1");
        assertThat(result, isEmpty());
    }
    @Test
    public void delete_whenMageDoesNotExist_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> sut.delete("Mage2"));
    }
    @Test
    public void save_whenMageDoesNotExist_shouldSaveMage() {
        sut.save(new Mage("Mage2", 2));
        var result = sut.findByName("Mage2");
        assertThat(result, isPresent());
    }
    @Test
    public void save_whenMageExists_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> sut.save(new Mage("Mage1", 1)));
    }
}