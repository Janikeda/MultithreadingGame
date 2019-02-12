package controller;

import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ApartmentActionPlace {
    private static final Logger LOGGER = LogManager.getLogger();

    /* Количество участников (потоков)*/
    public static final int AMOUNT_THREADS_OWNER = 3;
    public static final int AMOUNT_THREADS_THIEF = 3;

    /* Коллекция хранения вещей. Куда хозяин будет класть, а вор брать*/
    private List<Thing> thingList = Collections.synchronizedList(new ArrayList<>());

    private AtomicBoolean isOwnerInHome = new AtomicBoolean(false);
    private AtomicBoolean isThiefInHome = new AtomicBoolean(false);
    /* Подсчет хозяев и воров, чтобы последний распечатал итоги (сколько вещей осталось в квартире)*/
    private AtomicInteger numberOfOwners = new AtomicInteger(0);
    private AtomicInteger numberOfThieves = new AtomicInteger(0);


    public void put(Thing thing, String ownerName) throws InterruptedException {
        LOGGER.info(ownerName + " кладет вещь в квартиру!");
        Thread.sleep(150);
        thingList.add(thing);
    }

    /* Вор ворует все текущие вещи в квартире один раз, собирает оптимальный набор в рюкзаке, лишние вещи оставляет в квартире для других воров и покидает квартиру*/
    public List<Thing> stealCurrentApartment(String thiefName) {
        LOGGER.info(thiefName + " ворует все вещи!");
        List<Thing> list = new ArrayList<>(this.thingList);
        this.thingList.removeAll(list);

        return list;
    }

    public void getBackForThief(List<Thing> thingList) {
        this.thingList.addAll(thingList);
    }

    public int getAmountOfThings() {
        return thingList.size();
    }

    public AtomicBoolean getIsOwnerInHome() {
        return isOwnerInHome;
    }

    public AtomicBoolean getIsThiefInHome() {
        return isThiefInHome;
    }

    public AtomicInteger getNumberOfOwners() {
        return numberOfOwners;
    }

    public AtomicInteger getNumberOfThieves() {
        return numberOfThieves;
    }

    public boolean isLastParticipant() {
        return numberOfOwners.get() == AMOUNT_THREADS_OWNER && numberOfThieves.get() == AMOUNT_THREADS_THIEF;
    }
}
