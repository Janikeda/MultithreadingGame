package model;

import java.util.ArrayList;
import java.util.List;

public class Thief extends AbstractNamedEntity {
    private int currentWeight;
    private boolean isFull = false;


    public List<Thing> putIntoBackpack(List<Thing> thingList) {
        List<Thing> backpackList = Backpack.backpackList;
        List<Thing> thingsForReturn = thingList;

        for (Thing thing : thingList) {
            if (currentWeight < Backpack.MAX_WEIGHT) {
                backpackList.add(thing);
                thingsForReturn.remove(thing);
                currentWeight = currentWeight + thing.getWeight();
            } else {
                setFull(true);
            }
        }
        return thingsForReturn;
    }

    public List<Thing> getListAfterChecking() {
        List<Thing> thingList = Backpack.backpackList;
                //Backpack.backpackList.stream().sorted((v1, v2) -> Integer.compare(v2.getValue(), v1.getValue())).collect(Collectors.toList());

        while (currentWeight >= Backpack.MAX_WEIGHT) {
            Thing lessValueThing = thingList.get(thingList.size() - 1);
            LOGGER.info("Последняя вещь превысила размер рюкзака - удаляем ее!");
            currentWeight = currentWeight - lessValueThing.getWeight();
            thingList.remove(thingList.size() - 1);
        }
        return thingList;
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

    private static class Backpack {
        private static final int MAX_WEIGHT = 55;
        private static List<Thing> backpackList = new ArrayList<>();
    }
}
