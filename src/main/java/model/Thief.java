package model;

import java.util.ArrayList;
import java.util.List;

public class Thief extends AbstractNamedEntity {
    private Backpack backpack = new Backpack();


    public List<Thing> putIntoBackpack(List<Thing> thingList) {
        List<Thing> list = thingList;

        Thing[] items = list.toArray(new Thing[0]);

        int THEFT_RESULT = thingList.size();
        int[][] matrix = new int[THEFT_RESULT + 1][Backpack.MAX_WEIGHT + 1];

        for (int i = 0; i <= Backpack.MAX_WEIGHT; i++)
            matrix[0][i] = 0;

        for (int i = 1; i <= THEFT_RESULT; i++) {
            for (int j = 0; j <= Backpack.MAX_WEIGHT; j++) {
                if (items[i - 1].getWeight() > j) {
                    matrix[i][j] = matrix[i - 1][j];
                }
                else {
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - items[i - 1].getWeight()]
                            + items[i - 1].getValue());
                }
            }
        }

        int res = matrix[THEFT_RESULT][Backpack.MAX_WEIGHT];
        int maxWeight = Backpack.MAX_WEIGHT;
        List<Thing> backpackList = backpack.backpackList;

        for (int i = THEFT_RESULT; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][maxWeight]) {
                backpackList.add(items[i - 1]);
                res -= items[i - 1].getValue();
                maxWeight -= items[i - 1].getWeight();
            }
        }

        list.removeAll(backpackList);
        return list;
    }

    public String getBackpackMaxWeight() {
        return String.valueOf(Backpack.MAX_WEIGHT);
    }

    public List<Thing> getBackpackListThing() {
        return backpack.backpackList;
    }

    private class Backpack {
        private static final int MAX_WEIGHT = 45;
        private List<Thing> backpackList = new ArrayList<>();
    }
}
