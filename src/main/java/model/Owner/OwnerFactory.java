package model.Owner;

import model.Backpack;
import model.ParticipantFactory;
import model.Thing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OwnerFactory extends ParticipantFactory {
    private Random random = new Random();
    private int maxValueThing = random.nextInt(65 - 15 + 1) + 15;
    private int maxWeightThing = random.nextInt(35 - 7 + 1) + 7;

    /* Коллекция для хранения всех вещей хозяев. Нужна для статистики*/
    private static List<Thing> allOwnerThings = new ArrayList<>();


    @Override
    public Owner createParticipant() {
        Owner owner = new Owner();
        Backpack backpack = owner.getBackpack();
        backpack.setBackpackList(createThingList(backpack.getMaxWeight()));
        return owner;
    }

    private List<Thing> createThingList(int maxBackpackWeight) {
        List<Thing> thingList = new ArrayList<>();
        int currentWeight = 0;

        while (currentWeight <= maxBackpackWeight) {
            Thing thing = new Thing(random.nextInt(maxWeightThing), random.nextInt(maxValueThing));
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
