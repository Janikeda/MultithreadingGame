package model.Thief;

import model.Backpack;
import model.Participant;
import model.Thing;
import util.exception.ThiefSnatchTransactionException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Thief extends Participant {
    private Backpack backpack;
    private String thiefName;
    /* Коллекция для хранения вещей всех воров */
    private static List<Thing> allThievesThings = new ArrayList<>();
    private static int i = 1;
    private static String sdf = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));

    public Thief() {
        this.thiefName = "\"Вор потока " + (i++) + "\"";
        this.backpack = new Backpack();
    }

    public void snatchFromApartment(List<Thing> apartmentThings) {
        List<Thing> list = apartmentThings;

        Thing[] items = list.toArray(new Thing[0]);

        try {
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
            LOGGER.info(sdf + " " + thiefName + " ворует все вещи!");
            allThievesThings.addAll(backpackList);
            apartmentThings.removeAll(backpackList);
        } catch (ThiefSnatchTransactionException e) {
            e.printStackTrace();
        }
    }

    public static List<Thing> getAllThievesThings() {
        return allThievesThings;
    }

    public String getThiefName() {
        return thiefName;
    }

    public Backpack getBackpack() {
        return backpack;
    }
}
