package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OwnerBuilder {
    private static Random random = new Random();
    private static int maxBackpackWeight;
    /* Коллекция для хранения всех вещей хозяев*/
    private static List<Thing> allOwnerThings = new ArrayList<>();


    public static Owner createOwner(Backpack backpack) {
        backpack.setBackpackList(createThingList(backpack));
        return new Owner(backpack);
    }

    private static List<Thing> createThingList(Backpack backpack) {
        List<Thing> thingList = new ArrayList<>();
        maxBackpackWeight = backpack.getMaxWeight();
        int currentWeight = 0;

        while (currentWeight <= maxBackpackWeight) {
            Thing thing = new Thing(random.nextInt(24), random.nextInt(40));
            thingList.add(thing);
            currentWeight += thing.getWeight();
        }

        allOwnerThings.addAll(thingList);
        return thingList;
    }

    public static List<Thing> getAllOwnerThings() {
        return allOwnerThings;
    }
}
