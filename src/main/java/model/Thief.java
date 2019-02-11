package model;

import java.util.ArrayList;
import java.util.List;

public class Thief extends AbstractNamedEntity {
    private int currentWeight;
    private boolean isFull = false;
    private Backpack backpack = new Backpack();


    public List<Thing> putIntoBackpack(List<Thing> thingList) {
        List<Thing> backpackList = backpack.backpackList;
        List<Thing> list = thingList;

        for (Thing thing : list) {
            if (currentWeight < Backpack.MAX_WEIGHT) {
                backpackList.add(thing);
                currentWeight += thing.getWeight();
            } else {
                setFull(true);
                break;
            }
        }
        list.removeAll(backpackList);
        return list;
    }

    public List<Thing> getListAfterChecking(List<Thing> currentListInAppartment) {
        List<Thing> backpackList = backpack.backpackList;
        List<Thing> appartmentListThings = currentListInAppartment;

        while (currentWeight >= Backpack.MAX_WEIGHT) {
            Thing lessValueThing = backpackList.get(backpackList.size() - 1);
            LOGGER.info("Последняя вещь превысила размер рюкзака - удаляем ее!");
            currentWeight -= lessValueThing.getWeight();
            backpackList.remove(backpackList.size() - 1);
            appartmentListThings.add(lessValueThing);
        }
        return appartmentListThings;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public String getBackpackMaxWeight() {
        return String.valueOf(Backpack.MAX_WEIGHT);
    }

    public List<Thing> getBackpackListThing() {
        return backpack.backpackList;
    }

    private class Backpack {
        private static final int MAX_WEIGHT = 50;
        private List<Thing> backpackList = new ArrayList<>();
    }
}
