import controller.ApartmentActionPlace;
import model.ParticipantEnum;
import model.Thief;
import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ThiefThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ApartmentActionPlace actionPlace;
    private Thief thief;
    private String thiefName = "\"Вор потока " + this.getName().replaceAll("\\D+", "") + "\"";
    private ReentrantLock reentrantLock;

    ThiefThread(ApartmentActionPlace actionPlace, Thief thief, ReentrantLock reentrantLock) {
        this.actionPlace = actionPlace;
        this.thief = thief;
        this.reentrantLock = reentrantLock;
    }

    public void run() {
        steal();
    }

    private synchronized void steal() {
        reentrantLock.lock();
        try {
            while (actionPlace.checkWhoIsInHome(ParticipantEnum.THIEF)) {
                if (reentrantLock.isHeldByCurrentThread()) {
                    reentrantLock.unlock();
                }
                synchronized (actionPlace) {
                    actionPlace.wait();
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }

        actionPlace.setThiefInHome(true);

        LOGGER.info(thiefName + " в доме!");

        List<Thing> thingList;
        List<String> resultInfo;

        thief.putIntoBackpack(thiefName);

        thingList = thief.getBackpackListThing();
        resultInfo = calculateResult(thingList);

        LOGGER.info("Итого " + thiefName + " украл вещей: " + thingList.size() + ". На общую ценность: " + resultInfo.get(1) + ". На общий вес: " + resultInfo.get(0) +
                ". Предельный размер рюкзака: " + thief.getBackpackMaxWeight() + " кг.");

        actionPlace.setNumberOfThieves(actionPlace.getNumberOfThieves() + 1);

        actionPlace.setThiefInHome(false);
        if (reentrantLock.isHeldByCurrentThread()) {
            reentrantLock.unlock();
        }

        LOGGER.info(thiefName + " вышел из дома!");

        actionPlace.isLastParticipant();

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
