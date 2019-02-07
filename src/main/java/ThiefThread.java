import controller.ApartmentActionPlace;
import model.Thief;
import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ThiefThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ApartmentActionPlace actionPlace;
    private Thief thief;

    ThiefThread(ApartmentActionPlace actionPlace, Thief thief) {
        this.actionPlace = actionPlace;
        this.thief = thief;
    }

    public void run() {
        LOGGER.info("Запуск потока Вора");
        LOGGER.info("Вор проверяет есть ли в квартире хозяин");

        try {
            synchronized (actionPlace) {
                List<Thing> thingList;
                List<String> resultInfo;
                while (actionPlace.getFlag() != 1) {
                    actionPlace.wait();
                }
                while (!thief.isFull()) {
                    thief.putIntoBackpack(actionPlace.steal());
                }
                actionPlace.setFlag(0);

                thingList = thief.getListAfterChecking();
                resultInfo = calculateResult(thingList);

                LOGGER.info("Итого вор украл вещей: " + thingList.size() + ". На общую ценность: " + resultInfo.get(1) + ". На общий вес: " + resultInfo.get(0) +
                        ". Предельный размер рюкзака: " + thief.getBackpackMaxWeight() + " кг.");
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }

    private List<String> calculateResult(List<Thing> listThing) {
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
