import controller.ApartmentActionPlace;
import model.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OwnerThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ApartmentActionPlace actionPlace;
    private Owner owner;

    OwnerThread(ApartmentActionPlace actionPlace, Owner owner) {
        this.actionPlace = actionPlace;
        this.owner = owner;
    }

    public void run() {
        LOGGER.info("Запуск потока Хозяина");

        synchronized (actionPlace) {
            try {
                while (!owner.isThingListEmpty()) {
                    actionPlace.put(owner.getThing());
                }
                actionPlace.setFlag(1);
                actionPlace.notify();
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
            LOGGER.info("Хозяин выложил все вещи в квартиру");
        }
    }
}
