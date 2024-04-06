package org.example;

public class Main {
    public static void main(String[] args) {
        String orderingWay;     /*default, normal, alternative*/
        if (args.length != 0) {
            orderingWay = args[0];
        }
        else {
            orderingWay = "default";
        }

        Mage mag = new Mage("Bogos", 0, 1, orderingWay);
        Mage mag1 = new Mage("Aogos", 100, 10, orderingWay);
        Mage mag2 = new Mage("Aogos", 100, 100, orderingWay);
        Mage mag3 = new Mage("Cogos", 0, 1, orderingWay);
        Mage mag4 = new Mage("Cogosik", 1, 1, orderingWay);
        Mage mag5 = new Mage("Cogos", 1, 4, orderingWay);
        Mage mag6 = new Mage("Dogos", 10, 1, orderingWay);
        Mage mag7 = new Mage("Eogos", 50, 70, orderingWay);
        Mage mag8 = new Mage("Fogos", 34, 21, orderingWay);
        Mage mag9 = new Mage("Gogos", 10, 10, orderingWay);
        Mage mag10 = new Mage("Hogos", 23, 48, orderingWay);

        mag.addApprentice(mag1);
        mag1.addApprentice(mag2);
        mag1.addApprentice(mag3);
        mag1.addApprentice(mag6);
        mag2.addApprentice(mag4);
        mag2.addApprentice(mag10);
        mag3.addApprentice(mag5);
        mag4.addApprentice(mag7);
        mag4.addApprentice(mag8);
        mag4.addApprentice(mag9);

        mag.generateSetVisualisation(0);

        /*System.out.println("Compare mag vs mag2: " + mag.equals(mag2));
        System.out.println("Compare mag vs mag3: " + mag.equals(mag3));*/

        /*System.out.println("Hash mag: " + mag.hashCode() + " hash mag2: " + mag2.hashCode() + " same? " +
                ((mag.hashCode() == mag2.hashCode()) ? "yes" : "no"));
        System.out.println("Hash mag: " + mag.hashCode() + " hash mag3: " + mag3.hashCode() + " same? " +
                ((mag.hashCode() == mag3.hashCode()) ? "yes" : "no"));*/

        /*Smallest first*/
        /*Mage[] mages = {mag, mag2, mag3, mag4, mag5, mag6, mag7, mag8, mag9, mag10};
        System.out.println("Before sorting: ");
        for(Mage m : mages)
        {
            System.out.println(m);
        }
        java.util.Arrays.sort(mages);
        System.out.println("After sorting: ");
        for(Mage m: mages)
        {
            System.out.println(m);
        }*/

        /*Custom sorting*/
        /*Mage[] mages2 = {mag, mag2, mag3, mag4, mag5};
        Komparatorka komparatorka = new Komparatorka();
        System.out.println("Before custom sorting: ");
        for(Mage m : mages2)
        {
            System.out.println(m);
        }
        java.util.Arrays.sort(mages2, komparatorka);
        System.out.println("After custom sorting: ");
        for(Mage m: mages2)
        {
            System.out.println(m);
        }*/
    }
}