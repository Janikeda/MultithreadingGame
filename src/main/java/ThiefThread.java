import controller.ApartmentActionPlace;
import model.Thief;
import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThiefThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private ApartmentActionPlace actionPlace;
    private Thief thief;
    private String thiefName = "\"Вор потока " + this.getName().replaceAll("\\D+", "") + "\"";

    ThiefThread(ApartmentActionPlace actionPlace, Thief thief) {
        this.actionPlace = actionPlace;
        this.thief = thief;
    }

    public void run() {
        LOGGER.info("Старт потока: " + thiefName);
        this.steal();
    }

    private synchronized void steal() {
        List<Thing> thingList;
        List<Thing> thingsForReturn = Collections.emptyList();
        List<String> resultInfo;

        while (actionPlace.getIsOwnerInHome().get() || actionPlace.getIsThiefInHome().get() || actionPlace.getAmountOfThings() == 0) {
            try {
                synchronized (actionPlace) {
                    actionPlace.wait();
                }
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }

        actionPlace.getIsThiefInHome().set(true);
        LOGGER.info(thiefName + " в доме!");

        while (!thief.isFull()) {
            thingsForReturn = thief.putIntoBackpack(actionPlace.stealCurrentApartment(thiefName));
        }

        actionPlace.getBackForThief(thief.getListAfterChecking(thingsForReturn));
        thingList = thief.getBackpackListThing();
        resultInfo = calculateResult(thingList);

        LOGGER.info("Итого " + thiefName + " украл вещей: " + thingList.size() + ". На общую ценность: " + resultInfo.get(1) + ". На общий вес: " + resultInfo.get(0) +
                ". Предельный размер рюкзака: " + thief.getBackpackMaxWeight() + " кг.");

        actionPlace.getNumberOfThieves().incrementAndGet();

        actionPlace.getIsThiefInHome().set(false);
        LOGGER.info(thiefName + " вышел из дома!");

        if (actionPlace.isLastParticipant()) {
            LOGGER.info("Всего в квартире осталось вещей: " + actionPlace.getAmountOfThings());
        }

        synchronized (actionPlace) {
            actionPlace.notify();
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
