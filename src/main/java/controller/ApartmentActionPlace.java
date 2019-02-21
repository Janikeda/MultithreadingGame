package controller;

import model.ParticipantEnum;
import model.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ApartmentActionPlace {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ApartmentActionPlace instance;

    /* Коллекция хранения вещей. Куда хозяин будет класть, а вор брать*/
    private final List<Thing> thingList;

    private AtomicInteger isOwnerInHome;
    private AtomicInteger isThiefInHome;


    public static ApartmentActionPlace getInstance() {
        if (instance == null) {
            instance = new ApartmentActionPlace();
        }
        return instance;
    }

    private ApartmentActionPlace() {
        isOwnerInHome = new AtomicInteger(0);
        isThiefInHome = new AtomicInteger(0);
        thingList = Collections.synchronizedList(new ArrayList<>());
    }

    public void put(Thing thing, String ownerName) throws InterruptedException {
        LOGGER.info(ownerName + " кладет вещь в квартиру!");
        //Thread.sleep(50);
        synchronized (thingList) {
            thingList.add(thing);
        }
    }

    public AtomicInteger getIsOwnerInHome() {
        return isOwnerInHome;
    }

    public AtomicInteger getIsThiefInHome() {
        return isThiefInHome;
    }

    public List<Thing> getThingList() {
        return thingList;
    }

    /* Хозяин выкладывает вещи только если нет воров, вор ворует только если нет других воров и хозяев */
    public synchronized boolean enterIfCan(ParticipantEnum participantEnum) {
        if (participantEnum == ParticipantEnum.OWNER) {
            if (isThiefInHome.get() == 0) {
                isOwnerInHome.incrementAndGet();
                return true;
            } else return false;
        }
        else {
            if (isOwnerInHome.get() == 0 && isThiefInHome.get() == 0) {
                isThiefInHome.incrementAndGet();
                return true;
            } else return false;
        }
    }
}
