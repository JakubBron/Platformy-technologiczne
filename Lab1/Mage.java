import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Mage implements Comparable<Mage> {
    private String name;
    private int level;
    private double power;

    private Set<Mage> apprentices;

    public Mage(String name, int level, double power,   String sort) {
        this.name = name;
        this.level = level;
        this.power = power;
        this.apprentices = new HashSet<>();
        if(sort.equals("non")) apprentices = new HashSet<>();
        else if(sort.equals("normal")) apprentices = new TreeSet<>();
        else if(sort.equals("alternative")) apprentices = new TreeSet<>(new Comparatoring());
    }

    @Override
    public int compareTo(Mage mage){
        int ret = this.name.compareTo(mage.name);
        if (ret != 0) return ret;

        ret = Integer.compare(this.level,mage.level);
        if (ret != 0) return ret;
        return Double.compare(this.power,mage.power);
    }
    // ZADANIE 1
    public boolean equals(Mage mage) {
        /* System.out.println((this.name.equals(mage.name)) + " " + (this.level == mage.level) + " " +
                (this.post == mage.post) + " " + (this.departament.equals(mage.departament)) + " " +
                (this.salary == mage.salary) + " " + (this.apprentices.equals(mage.apprentices))); */
        if (this.name.equals(mage.name) &&
                this.level == mage.level &&
                this.power == mage.power &&
                this.apprentices.equals(mage.apprentices)) return true;
        return false;
    }
    // ZADANIE 1
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.name != null ? this.name.hashCode() : 0;
        result = 31 * result + this.level != 0 ? this.level : 0;
        result = 31 * result + this.apprentices.size() != 0 ? this.apprentices.hashCode() : 0;
        result = 31 * result + this.power != 0 ? (int) this.power : 0;

        return result;
    }
    // ZADANIE 1
    @Override
    public String toString() {
        return "Mage{name='" + (this.name != null ? this.name : "bezimienny")  +
                "', accessLevel=" + this.level + ", power=" + this.power +
                 "}";
    }

    public void genStat(int level){
        for (int i = 0; i < level; i++) System.out.print("-");
        System.out.print(this);
        System.out.println(", " + apprenticeNumber());

        for (Mage mage: apprentices) mage.genStat(level+1);
    }

    public void print(int level){
        for (int i = 0; i < level; i++) System.out.print("-");
        System.out.println(this);
        for (Mage mage: apprentices) mage.print(level+1);
    }

    public void addApprentice(Mage mage){
        apprentices.add(mage);
    }

    public int apprenticeNumber(){
        int ret = 0;
        for(Mage m : apprentices){
            ret += m.apprenticeNumber() + 1;
        }

        return ret;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public double getPower() { return power; }

}