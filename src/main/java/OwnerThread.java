import controller.ApartmentActionPlace;
import model.Owner;
import model.ParticipantEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class OwnerThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ApartmentActionPlace actionPlace;
    private Owner owner;
    private String ownerName = "\"Хозяин потока " + this.getName().replaceAll("\\D+", "") + "\"";
    private ReentrantLock reentrantLock;

    OwnerThread(ApartmentActionPlace actionPlace, Owner owner, ReentrantLock reentrantLock) {
        this.actionPlace = actionPlace;
        this.owner = owner;
        this.reentrantLock = reentrantLock;
    }

    public void run() {
        reentrantLock.lock();
        try {
            while (actionPlace.checkWhoIsInHome(ParticipantEnum.OWNER)) {
                synchronized (actionPlace) {
                    actionPlace.wait();
                }
            }
            actionPlace.setOwnerInHome(true);
            reentrantLock.unlock();

            while (!owner.isThingListEmpty()) {
                actionPlace.put(owner.getThing(), ownerName);
            }

            actionPlace.getNumberOfOwners().incrementAndGet();
            LOGGER.info(ownerName + (owner.getAmountOfThings() == 0 ? " выложил все вещи в квартиру" : " имеет еще вещи " + owner.getAmountOfThings()));

            if (actionPlace.isLastOwner()) {
                actionPlace.setOwnerInHome(false);
                actionPlace.isLastParticipant();
                LOGGER.info("Вышел последний хозяин!!!!!!!");
                synchronized (actionPlace) {
                    actionPlace.notify();
                }
            }

        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
}
