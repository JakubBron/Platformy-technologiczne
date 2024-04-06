import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PT1 {

    public static void main(String[] args){
        String arg = args.length > 0 ? args[0] : "alternative";

        Mage mage = new Mage("mage", 1, 1,  args[0]);
        Mage mage1 = new Mage("mage1", 2, 2,  args[0]);
        Mage mage2 = new Mage("mage2", 2, 2,  args[0]);
        Mage mage3 = new Mage("mage2", 2, 2,  args[0]);
        Mage mage4 = new Mage("mage4", 5, 5, args[0]);
        Mage mage5 = new Mage("mage5", 6, 6,  args[0]);
        Mage mage6 = new Mage("mage6", 5, 5,  args[0]);
        Mage mage7 = new Mage("mage7", 3, 3,  args[0]);
        Mage mage8 = new Mage("mage8", 3, 3,  args[0]);
        Mage mage9 = new Mage("mage8", 3, 3,  args[0]);
        Mage mage10 = new Mage("mage10", 1, 1,  args[0]);

        mage.addApprentice(mage1);
        mage1.addApprentice(mage2);
        mage1.addApprentice(mage3);
        mage1.addApprentice(mage6);
        mage2.addApprentice(mage4);
        mage2.addApprentice(mage10);
        mage3.addApprentice(mage5);
        mage4.addApprentice(mage7);
        mage4.addApprentice(mage8);
        mage4.addApprentice(mage9);

//        System.out.println("mage2 Equals mage3? - " + mage2.equals(mage3));
//        System.out.println("mage8 Equals mage9? - " + mage8.equals(mage9));
//
//        System.out.println("HashCode for mage8 - " + mage8.hashCode());
//        System.out.println("HashCode for mage9 - " + mage9.hashCode());
//        System.out.println("HashCode for mage10 - " + mage10.hashCode());
//
//        System.out.println("mage8 compares to mage9? - " + mage8.compareTo(mage9));
//        System.out.println("mage8 compares to mage10? - " + mage8.compareTo(mage10));


        //mage.print(0);
        mage.genStat(0);
    }
}
