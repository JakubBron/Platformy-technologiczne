package org.example;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class RepositoryInMemory implements RepositoryInterface {
    private final Collection<Mage> allMages = new ArrayList<>();
    public RepositoryInMemory() {}
    public RepositoryInMemory(Collection<Mage> collection) {
        this.allMages.addAll(collection);
    }
    @Override
    public Optional<Mage> findByName(String name) {
        for (Mage mage : allMages) {
            if (mage.getName().equals(name)) {
                return Optional.of(mage);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(String name) {
        var mage = findByName(name);

        if (mage.isEmpty()) {
            throw new IllegalArgumentException("Mage with name " + name + " doen't exist!");
        }
        allMages.remove(mage.get());
    }

    @Override
    public void save(Mage toSave) {
        for(Mage m: allMages) {
            if (m.getName().equals(toSave.getName())) {
                throw new IllegalArgumentException("Mage already exists!");
            }
        }
        allMages.add(toSave);
    }
}