package controller;

import model.OwnerBuilder;
import model.ParticipantEnum;
import model.Thief;
import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ApartmentActionPlace {
    private static final Logger LOGGER = LogManager.getLogger();

    /* Количество участников (потоков)*/
    public static final int AMOUNT_THREADS_OWNER = 1000;
    public static final int AMOUNT_THREADS_THIEF = 1000;

    /* Коллекция хранения вещей. Куда хозяин будет класть, а вор брать*/
    private final List<Thing> thingList = Collections.synchronizedList(new ArrayList<>());

    private boolean isOwnerInHome;
    private boolean isThiefInHome;
    /* Подсчет хозяев и воров, чтобы последний распечатал итоги (сколько вещей осталось в квартире)
     * Счетчик хозяев будет увеличиваться из разных потоков - сделаем переменную атомарной */
    private AtomicInteger numberOfOwners = new AtomicInteger(0);
    private int numberOfThieves;


    public void put(Thing thing, String ownerName) throws InterruptedException {
        LOGGER.info(ownerName + " кладет вещь в квартиру!");
        Thread.sleep(50);
        synchronized (thingList) {
            thingList.add(thing);
        }
    }

    public void setOwnerInHome(boolean ownerInHome) {
        isOwnerInHome = ownerInHome;
    }

    public void setThiefInHome(boolean thiefInHome) {
        isThiefInHome = thiefInHome;
    }

    public AtomicInteger getNumberOfOwners() {
        return numberOfOwners;
    }

    public int getNumberOfThieves() {
        return numberOfThieves;
    }

    public void setNumberOfThieves(int numberOfThieves) {
        this.numberOfThieves = numberOfThieves;
    }

    public List<Thing> getThingList() {
        return thingList;
    }

    public void isLastParticipant() {
        if (numberOfOwners.get() == AMOUNT_THREADS_OWNER && numberOfThieves == AMOUNT_THREADS_THIEF) {
            LOGGER.info("Всего у хозяев было вещей: " + OwnerBuilder.getAllOwnerThings().size());
            LOGGER.info("Всего в квартире осталось вещей: " + thingList.size());
            LOGGER.info("Всего воры украли: " + Thief.getAllThievesThings().size());

            if (OwnerBuilder.getAllOwnerThings().size() == thingList.size() + Thief.getAllThievesThings().size()) {
                LOGGER.info("Все вещи посчитались корректно!");
            } else {
                LOGGER.error("Вещи потерялись!");
            }
        }
    }

    public boolean isLastOwner() {
        return numberOfOwners.get() == AMOUNT_THREADS_OWNER;
    }

    /* Хозяин выклдавает вещи только если нет воров, вор ворует только если нет других воров и хозяев
    Участнику для начала своих действий нужен от метода false */
    public synchronized boolean checkWhoIsInHome(ParticipantEnum participantEnum) {
        if (participantEnum == ParticipantEnum.OWNER) {
            return isThiefInHome;
        } else {
            return isThiefInHome || isOwnerInHome;
        }
    }
}
