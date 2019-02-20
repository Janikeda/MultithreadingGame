package model;

import controller.ApartmentActionPlace;

import java.util.ArrayList;
import java.util.List;

public class Thief extends AbstractNamedEntity {
    private Backpack backpack;
    private ApartmentActionPlace actionPlace;
    /* Коллекция для хранения вещей всех воров */
    private static List<Thing> allThievesThings = new ArrayList<>();

    public Thief(Backpack backpack, ApartmentActionPlace actionPlace) {
        this.backpack = backpack;
        this.actionPlace = actionPlace;
    }


    public void putIntoBackpack(String thiefName) {
        List<Thing> list = actionPlace.getThingList();

        Thing[] items = list.toArray(new Thing[0]);

        int THEFT_RESULT = list.size();
        int[][] matrix = new int[THEFT_RESULT + 1][backpack.getMaxWeight() + 1];

        for (int i = 0; i <= backpack.getMaxWeight(); i++)
            matrix[0][i] = 0;

        for (int i = 1; i <= THEFT_RESULT; i++) {
            for (int j = 0; j <= backpack.getMaxWeight(); j++) {
                if (items[i - 1].getWeight() > j) {
                    matrix[i][j] = matrix[i - 1][j];
                } else {
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - items[i - 1].getWeight()]
                            + items[i - 1].getValue());
                }
            }
        }

        int res = matrix[THEFT_RESULT][backpack.getMaxWeight()];
        int maxWeight = backpack.getMaxWeight();
        List<Thing> backpackList = backpack.getBackpackList();

        for (int i = THEFT_RESULT; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][maxWeight]) {
                backpackList.add(items[i - 1]);
                res -= items[i - 1].getValue();
                maxWeight -= items[i - 1].getWeight();
            }
        }
        LOGGER.info(thiefName + " ворует все вещи!");
        allThievesThings.addAll(backpackList);
        actionPlace.getThingList().removeAll(backpackList);
    }

    public int getBackpackMaxWeight() {
        return backpack.getMaxWeight();
    }

    public List<Thing> getBackpackListThing() {
        return backpack.getBackpackList();
    }

    public static List<Thing> getAllThievesThings() {
        return allThievesThings;
    }
}
