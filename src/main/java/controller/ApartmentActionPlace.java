package controller;

import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ApartmentActionPlace {
    private static final Logger LOGGER = LogManager.getLogger();
    /* Коллекция хранения вещей. Куда хозяин будет класть, а вор брать*/
    private List<Thing> thingList = Collections.synchronizedList(new ArrayList<>());
    /* Знак для вора, когда хозяина нет в квартире. 0 - в квартире хозяин, 1 - в квартире вор*/
    private int flag = 0;
    private AtomicBoolean isOwnerInHome = new AtomicBoolean(false);
    private AtomicBoolean isThiefInHome = new AtomicBoolean(false);


    public void put(Thing thing, String ownerName) throws InterruptedException {
//        while (flag == 1) {
//            condition.await();
//        }
        LOGGER.info(ownerName + " кладет вещь в квартиру!");
        Thread.sleep(120);
        thingList.add(thing);
        //condition.signal();
    }

    public synchronized Thing steal() {
        LOGGER.info("Вор ворует одну вещь!");
        int randomIndex = new Random().nextInt(thingList.size());
        while (getFlag() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
        Thing thing = thingList.get(randomIndex);
        thingList.remove(randomIndex);
        /* Сигнал для других воров, что они могут воровать*/
        //notifyAll();
        return thing;
    }

    public List<Thing> stealCurrentApartment() {
        LOGGER.info("Вор ворует все вещи!");
        List<Thing> thingList = this.thingList.stream()
                .sorted((v1, v2) -> Integer.compare(v2.getValue(), v1.getValue()))

                .collect(Collectors.toList());
        this.thingList.removeAll(thingList);

        return thingList;
    }

    public synchronized void getBackForThief(List<Thing> thingList) {
        this.thingList.addAll(thingList);
    }

    public int getAmountOfThings() {
        return thingList.size();
    }

//    public void signalForOwner() {
//        isThiefDone.signal();
//    }
//
//    public void signalForThief() {
//        isOwnerDone.signal();
//    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public AtomicBoolean getIsOwnerInHome() {
        return isOwnerInHome;
    }

    public void setIsOwnerInHome(AtomicBoolean isOwnerInHome) {
        this.isOwnerInHome = isOwnerInHome;
    }

    public AtomicBoolean getIsThiefInHome() {
        return isThiefInHome;
    }

    public void setIsThiefInHome(AtomicBoolean isThiefInHome) {
        this.isThiefInHome = isThiefInHome;
    }
}
