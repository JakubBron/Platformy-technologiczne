package org.example;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Mage implements Comparable<Mage> {
    private String name;
    private int power;
    private int level;
    private Set<Mage> apprentices;
    private String orderingWay;

    public Mage(String name, int power, int level, String orderingWay) {
        this.name = name;
        this.power = power;
        this.level = level;
        this.apprentices = new HashSet<>();
        this.orderingWay = orderingWay;
        if (orderingWay.equals("default"))
        {
            apprentices = new HashSet<>();
        }
        else if (orderingWay.equals("normal"))
        {
            apprentices = new TreeSet<>();
        }
        else if (orderingWay.equals("alternative"))
        {
            apprentices = new TreeSet<>(new Komparatorka());
        }
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getLevel() {
        return level;
    }

    public Set<Mage> getApprentices() {
        return apprentices;
    }

    public String getOrderingWay() {
        return orderingWay;
    }

    public void setApprentices(Set<Mage> apprentices) {
        this.apprentices = apprentices;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Mage{" + "name='" + name + "', power=" + power + ", level=" + level + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 2137;
        int hash = 1;
        hash += prime * ((name == null) ? 0 : name.hashCode());
        hash += prime * ((power == 0) ? 0 : power);
        hash += prime * ((level == 0) ? 0 : level);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)    /* == compares references to objects*/
            return true;
        if (obj == null)    /*comparing anything with null gives false*/
            return false;
        if (getClass() != obj.getClass())   /*check if same classes*/
            return false;                   /*getClass() returns the runtime class of an object*/

        Mage mage = (Mage) obj;     /*casting obj to Mage, because class ob obj is Object*/

        /*For sure, obj is a valid object from Mage class, now we can compare values*/

        if (power == mage.power && level == mage.level && name.equals(mage.name)
                && Objects.equals(apprentices, mage.apprentices) )
            return true;

        return false;
    }

    @Override
    public int compareTo(Mage other) {
        /*Hierarchy: power, level, name*/
        if (power > other.power)
            return 1;
        else if (power < other.power)
            return -1;
        else {
            if (level > other.level)
                return 1;
            else if (level < other.level)
                return -1;
            else {
                return name.compareTo(other.name);
            }
        }
    }

    public void addApprentice(Mage m) {
        apprentices.add(m);
    }

    public int count()
    {
        int count = 0;
        for(Mage m: apprentices)
            count += m.count() + 1;

        return count;
    }

    public void generateSetVisualisation(int level)
    {
        for(int i = 0; i < level; i++)
            System.out.print("-");
        System.out.println(this + ", belowMe: " + this.count());
        for(Mage m: apprentices)
            m.generateSetVisualisation(level + 1);
    }
}
