package org.example;
import java.util.Comparator;

public class Komparatorka implements Comparator<Mage> {
    @Override
    public int compare(Mage f, Mage s) {
        /* Default hierarchy: power, level, name*/
        /* My hierarchy: name, power, level*/
        int textComparisonResult = f.getName().compareTo(s.getName());
        if(textComparisonResult == 0)
        {
            if(f.getPower() != s.getPower())
            {
                if (f.getPower() < s.getPower())
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                if(f.getLevel() > s.getLevel())
                {
                    return 1;
                }
                else if(f.getLevel() < s.getLevel())
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        }
        else
        {
            return textComparisonResult;
        }
    }
}
