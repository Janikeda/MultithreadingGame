package controller;

import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class ApartmentActionPlace {
    private static final Logger LOGGER = LogManager.getLogger();
    /* Коллекция хранения вещей. Куда хозяин будет класть, а вор брать*/
    private List<Thing> thingList = Collections.synchronizedList(new ArrayList<>());
    /* Знак для вора, когда хозяина нет в квартире. 0 - в квартире хозяин, 1 - в квартире вор*/
    private static AtomicInteger flag = new AtomicInteger(0);


    public void put(Thing thing) throws InterruptedException {
        Thread.sleep(120);
        thingList.add(thing);
    }

    public synchronized Thing steal() {
        LOGGER.info("Вор ворует вещь!");
        int randomIndex = new Random().nextInt(thingList.size());

        Thing thing = thingList.get(randomIndex);
        thingList.remove(randomIndex);
        /* Сигнал для других воров, что они могут воровать*/
        notifyAll();
        return thing;
    }

    public void setFlag(int flag) {
        ApartmentActionPlace.flag = new AtomicInteger(flag);
    }

    public int getFlag() {
        return flag.get();
    }
}
