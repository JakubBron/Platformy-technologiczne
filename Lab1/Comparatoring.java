import java.util.Comparator;

public class Comparatoring implements Comparator<Mage> {

    @Override
    public int compare(Mage mage1, Mage mage2) {
        int ret = Integer.compare(mage1.getLevel(), mage2.getLevel());
        if (ret != 0) return ret;
        ret = mage1.getName().compareTo(mage2.getName());
        if (ret != 0) return ret;
        return Double.compare(mage1.getPower(),mage2.getPower());
    }
}