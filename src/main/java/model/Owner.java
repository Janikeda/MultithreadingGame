package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Owner extends AbstractNamedEntity {
    /* Набор вещей, которые есть у хозяина*/
    private List<Thing> thingList = new ArrayList<>(Arrays.asList(new Thing(7, 9), new Thing(14, 19), new Thing(19, 11), new Thing(2, 5),
            new Thing(21, 20), new Thing(2, 5), new Thing(3, 9), new Thing(9, 3)));


    public Thing getThing() {
        int randomIndex = new Random().nextInt(thingList.size());

        Thing thing = thingList.get(randomIndex);
        thingList.remove(randomIndex);
        return thing;
    }

    public boolean isThingListEmpty() {
        return thingList.isEmpty();
    }
}
