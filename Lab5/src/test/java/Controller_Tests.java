import org.example.Mage;
import org.example.Repository;
import org.example.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class Controller_Tests {
    private Controller controller;
    private Repository repositoryMock;

    @BeforeEach
    public void init() {
        repositoryMock = mock(Repository.class);
        controller = new Controller(repositoryMock);
    }

    @Test
    public void find_whenMageExists_shouldReturnMage() {
        when(repositoryMock.findByName("Mage1")).thenReturn(Optional.of(new Mage("Mage1", 1)));

        var result = controller.find("Mage1");

        assertThat(result, is("Mage{name: Mage1, level: 1}"));
    }

    @Test
    public void find_whenMageDoesNotExist_shouldReturnNotFound() {
        when(repositoryMock.findByName("Mage2")).thenReturn(Optional.empty());

        var result = controller.find("Mage2");

        assertThat(result, is("not found"));
    }

    @Test
    public void delete_whenMageExists_shouldDeleteMage() {
        var result = controller.delete("Mage1");

        verify(repositoryMock).delete("Mage1");
        assertThat(result, is("done"));
    }

    @Test
    public void delete_whenMageDoesNotExist_shouldReturnNotFound() {
        doThrow(new IllegalArgumentException()).when(repositoryMock).delete("Mage2");

        var result = controller.delete("Mage2");

        assertThat(result, is("not found"));
    }

    @Test
    public void save_whenMageIsValid_shouldSaveMage() {
        var result = controller.save("Mage2", "2");

        verify(repositoryMock).save(new Mage("Mage2", 2));
        assertThat(result, is("done"));
    }

    @Test
    public void save_whenMageIsInvalid_shouldReturnBadRequest() {
        doThrow(new IllegalArgumentException()).when(repositoryMock).save(new Mage("Mage2", 2));

        var result = controller.save("Mage2", "2");

        assertThat(result, is("bad request"));
    }
}