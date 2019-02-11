import controller.ApartmentActionPlace;
import model.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OwnerThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ApartmentActionPlace actionPlace;
    private Owner owner;
    private String ownerName = "\"Хозяин потока " + this.getName().replaceAll("\\D+", "") + "\"";

    OwnerThread(ApartmentActionPlace actionPlace, Owner owner) {
        this.actionPlace = actionPlace;
        this.owner = owner;
    }

    public void run() {
        LOGGER.info("Старт потока: " + ownerName + " Всего вещей: " + owner.getAmountOfThings());

        try {
            while (actionPlace.getIsThiefInHome().get()) {
                synchronized (actionPlace) {
                    actionPlace.wait();
                }
            }
            actionPlace.getIsOwnerInHome().set(true);

            while (!owner.isThingListEmpty()) {
                actionPlace.put(owner.getThing(), ownerName);
            }

            LOGGER.info(ownerName + (owner.getAmountOfThings() == 0 ? " выложил все вещи в квартиру" : "имеет еще вещей " + owner.getAmountOfThings()));
            actionPlace.getNumberOfOwners().incrementAndGet();

            actionPlace.getIsOwnerInHome().set(false);

            if (actionPlace.isLastParticipant()) {
                LOGGER.info("Всего в квартире осталось вещей: " + actionPlace.getAmountOfThings());
            }

            synchronized (actionPlace) {
                actionPlace.notify();
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
}
