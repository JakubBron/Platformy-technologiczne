package org.example;

import org.example.Mage;
import org.example.ControllerInterface;
import org.example.Repository;

public class Controller implements ControllerInterface {
    private final Repository repository;
    public Controller(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String find(String name) {
        var result = repository.findByName(name);

        if (result.isEmpty()) {
            return "not found";
        }
        else {
            return result.get().toString();
        }
    }
    @Override
    public String delete(String name) {
        try {
            repository.delete(name);
            return "done";
        } catch (IllegalArgumentException e) {
            return "not found";
        }
    }

    @Override
    public String save(String name, String level) {
        try {
            repository.save(new Mage(name, Integer.parseInt(level)));
            return "done";
        } catch (IllegalArgumentException e) {
            return "bad request";
        }
    }
}