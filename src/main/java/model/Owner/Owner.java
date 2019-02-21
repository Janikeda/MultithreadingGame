package model.Owner;

import model.Backpack;
import model.Participant;
import model.Thing;

import java.util.List;
import java.util.Random;

public class Owner extends Participant {
    /* Набор вещей, которые есть у хозяина*/
    private Backpack backpack;

    public Owner() {
        this.backpack = new Backpack();
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

    public Backpack getBackpack() {
        return backpack;
    }
}
