package util;

import controller.ApartmentActionPlace;
import model.Owner.OwnerFactory;
import model.Thief.Thief;
import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private static final Logger LOGGER = LogManager.getLogger();


    public static void showAllResults(ApartmentActionPlace actionPlace) {
        LOGGER.info("Всего у хозяев было вещей: " + OwnerFactory.getAllOwnerThings().size());
        LOGGER.info("Всего в квартире осталось вещей: " + actionPlace.getThingList().size());
        LOGGER.info("Всего воры украли: " + Thief.getAllThievesThings().size());

        if (OwnerFactory.getAllOwnerThings().size() == actionPlace.getThingList().size() + Thief.getAllThievesThings().size()) {
            LOGGER.info("Все вещи посчитались корректно!");
        } else {
            LOGGER.error("Вещи потерялись!");
        }
    }

    public static void showThiefResult(Thief thief) {
        List<Thing> thingList = thief.getBackpack().getBackpackList();

        List<String> resultInfo = calculateThiefResult(thingList);
        LOGGER.info("Итого " + thief.getThiefName() + " украл вещей: " + thingList.size() + ". На общую ценность: " + resultInfo.get(1) + ". На общий вес: " + resultInfo.get(0) +
                ". Предельный размер рюкзака: " + thief.getBackpack().getMaxWeight() + " кг.");
    }

    private static List<String> calculateThiefResult(List<Thing> listThing) {
        /* Первое значение: инфо об общем весе, второе: инфо об общей стоимости*/
        List<String> list = new ArrayList<>();

        int totalWeight = 0;
        int totalValue = 0;

        for (Thing thing : listThing) {
            totalValue = totalValue + thing.getValue();
            totalWeight = totalWeight + thing.getWeight();
        }
        list.add(String.valueOf(totalWeight));
        list.add(String.valueOf(totalValue));

        return list;
    }
}
