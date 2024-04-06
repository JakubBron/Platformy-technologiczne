package org.example;

import org.example.Mage;
import org.example.Tower;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static SessionFactory getHibernateSessionFactory(StandardServiceRegistry registry) {
        return new MetadataSources(registry)
                .addAnnotatedClasses(Mage.class, Tower.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    private static void seedData(SessionFactory sessionFactory) {
        var session = sessionFactory.openSession();
        session.beginTransaction();
        Mage mage1 = new Mage("Mage1", 50);
        Mage mage2 = new Mage("Mage2", 100);
        Mage mage3 = new Mage("Mage3", 30);
        Tower tower1 = new Tower("Tower1", 1000);
        tower1.addMage(mage1);
        mage1.setTower(tower1);
        tower1.addMage(mage2);
        mage2.setTower(tower1);
        Tower tower2 = new Tower("Tower2", 500);
        tower2.addMage(mage3);
        mage3.setTower(tower2);

        session.persist(mage1);
        session.persist(mage2);
        session.persist(mage3);
        session.persist(tower1);
        session.persist(tower2);
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        final String shebang = "> ";
        final var registry = new StandardServiceRegistryBuilder().build();
        try (var sessionFactory = getHibernateSessionFactory(registry)) {
            seedData(sessionFactory);

            boolean shouldRun = true;
            Scanner scanner = new Scanner(System.in);
            while(shouldRun) {
                System.out.println("Enter a command: ");
                System.out.println("\tlist - list all mages and towers");
                System.out.println("\taddMage - add a mage");
                System.out.println("\taddTower - add a tower");
                System.out.println("\taddMageToTower - add a mage to a tower");
                System.out.println("\tremoveMageFromTower - remove a mage from a tower");
                System.out.println("\tstrongMages - list all mages with level > 50");
                System.out.println("\texit - exit the program");
                System.out.printf(shebang);
                String prompt = scanner.nextLine();

                if (prompt.equals("exit")) {
                    shouldRun = false;
                }
                else if (prompt.equals("list")) {
                    var session = sessionFactory.openSession();
                    var mages = session.createQuery("from Mage", Mage.class).list();
                    var towers = session.createQuery("from Tower", Tower.class).list();
                    System.out.println("Mages: ");
                    for(Mage mage : mages) {
                        System.out.println(mage);
                    }
                    System.out.println("Towers: ");
                    for(Tower tower : towers) {
                        System.out.println(tower);
                    }
                    session.close();
                }
                else if (prompt.equals("addMage")) {
                    var session = sessionFactory.openSession();
                    session.beginTransaction();
                    String mageName = "";
                    int mageLevel = -1;

                    List<String> usedNames = session.createQuery("select name from Mage", String.class).list();

                    System.out.println("Enter mage name: ");
                    System.out.printf(shebang);
                    mageName = scanner.nextLine();
                    System.out.println("Enter mage level: ");
                    System.out.printf(shebang);
                    mageLevel = scanner.nextInt();
                    scanner.nextLine();
                    while(usedNames.contains(mageName)) {
                        System.out.println("Name already used, please enter a different name.");
                        System.out.println("Enter mage name: ");
                        System.out.printf(shebang);
                        mageName = scanner.nextLine();
                        System.out.println("Enter mage level: ");
                        System.out.printf(shebang);
                        mageLevel = scanner.nextInt();
                        scanner.nextLine();
                    }

                    Mage mage = new Mage(mageName, mageLevel);
                    session.persist(mage);
                    session.getTransaction().commit();
                    session.close();
                }
                else if (prompt.equals("addTower")) {
                    var session = sessionFactory.openSession();
                    session.beginTransaction();
                    String towerName = "";
                    int towerHeight = -1;

                    List<String> usedNames = session.createQuery("select name from Tower", String.class).list();
                    System.out.println("Enter tower name: ");
                    System.out.printf(shebang);
                    towerName = scanner.nextLine();
                    System.out.println("Enter tower height: ");
                    System.out.printf(shebang);
                    towerHeight = scanner.nextInt();
                    scanner.nextLine();
                    while(usedNames.contains(towerName)) {
                        System.out.println("Name already used, please enter a different name.");
                            System.out.println("Enter tower name: ");
                            System.out.printf(shebang);
                            towerName = scanner.nextLine();
                            System.out.println("Enter tower height: ");
                            System.out.printf(shebang);
                            towerHeight = scanner.nextInt();
                            scanner.nextLine();
                    }

                    Tower tower = new Tower(towerName, towerHeight);
                    session.persist(tower);
                    session.getTransaction().commit();
                    session.close();
                }
                else if (prompt.equals("addMageToTower")) {
                    var session = sessionFactory.openSession();
                    session.beginTransaction();
                    String mageName = "";
                    String towerName = "";

                    List<String> mageNames = session.createQuery("select name from Mage", String.class).list();
                    List<String> towerNames = session.createQuery("select name from Tower", String.class).list();

                    System.out.println("Enter mage name: ");
                    System.out.printf(shebang);
                    mageName = scanner.nextLine();
                    while(!mageNames.contains(mageName)) {
                        System.out.println("Mage does not exist, please enter a different name.");
                        System.out.println("Enter mage name: ");
                            System.out.printf(shebang);
                            mageName = scanner.nextLine();
                    }
                    Mage mage = session.get(Mage.class, mageName);

                    System.out.println("Enter tower name: ");
                    System.out.printf(shebang);
                    towerName = scanner.nextLine();
                    while(!towerNames.contains(towerName)) {
                        System.out.println("Tower does not exist, please enter a different name.");
                            System.out.println("Enter tower name: ");
                            System.out.printf(shebang);
                            towerName = scanner.nextLine();
                    }
                    Tower tower = session.get(Tower.class, towerName);

                    tower.addMage(mage);
                    mage.setTower(tower);
                    session.persist(tower);
                    session.getTransaction().commit();
                    session.close();
                }
                else if (prompt.equals("removeMageFromTower")) {
                    var session = sessionFactory.openSession();
                    session.beginTransaction();
                    String mageName = "";
                    String towerName = "";

                    List<String> mageNames = session.createQuery("select name from Mage", String.class).list();
                    System.out.println("Enter mage name: ");
                    System.out.printf(shebang);
                    mageName = scanner.nextLine();
                    while(!mageNames.contains(mageName)) {
                        System.out.println("Mage does not exist, please enter a different name.");
                            System.out.println("Enter mage name: ");
                            System.out.printf(shebang);
                            mageName = scanner.nextLine();
                    }
                    Mage mage = session.get(Mage.class, mageName);

                    List<String> towerNames = session.createQuery("select name from Tower", String.class).list();
                    System.out.println("Enter tower name: ");
                    System.out.printf(shebang);
                    towerName = scanner.nextLine();
                    while(!towerNames.contains(towerName)) {
                        System.out.println("Tower does not exist, please enter a different name.");
                            System.out.println("Enter tower name: ");
                            System.out.printf(shebang);
                            towerName = scanner.nextLine();
                    }
                    Tower tower = session.get(Tower.class, towerName);
                    tower.removeMage(mage);
                    mage.setTower(null);
                    session.persist(tower);
                    session.getTransaction().commit();
                    session.close();
                }
                else if (prompt.equals("strongMages")){
                    var session = sessionFactory.openSession();
                    session.beginTransaction();
                    session.createQuery("from Mage where level > 50", Mage.class).list().forEach(System.out::println);
                }
                else {
                    System.out.println("Unknown command");
                }

                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}