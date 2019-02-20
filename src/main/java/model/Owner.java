package model;

import java.util.List;
import java.util.Random;

public class Owner extends AbstractNamedEntity {
    /* Набор вещей, которые есть у хозяина*/
    private Backpack backpack;

    public Owner(Backpack backpack) {
        this.backpack = backpack;
    }

    public Thing getThing() {
        List<Thing> thingList = backpack.getBackpackList();
        int randomIndex = new Random().nextInt(thingList.size());

        Thing thing = thingList.get(randomIndex);
        backpack.getBackpackList().remove(randomIndex);
        return thing;
    }

    public boolean isThingListEmpty() {
        return backpack.getBackpackList().isEmpty();
    }

    public int getAmountOfThings() {
        return backpack.getBackpackList().size();
    }
}
