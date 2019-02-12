package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Owner extends AbstractNamedEntity {
    /* Набор вещей, которые есть у хозяина*/
    private List<Thing> thingList = createThingList();


    public Thing getThing() {
        int randomIndex = new Random().nextInt(thingList.size());

        Thing thing = thingList.get(randomIndex);
        thingList.remove(randomIndex);
        return thing;
    }

    public boolean isThingListEmpty() {
        return thingList.isEmpty();
    }

    public int getAmountOfThings() {
        return thingList.size();
    }

    private List<Thing> createThingList() {
        List<Thing> thingList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < (random.nextInt(12 - 9 + 1) + 9); i++) {
            thingList.add(new Thing(random.nextInt(24), random.nextInt(40)));
        }
        return thingList;
    }
}
