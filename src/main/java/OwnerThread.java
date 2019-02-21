import controller.ApartmentActionPlace;
import model.Owner.Owner;
import model.Owner.OwnerFactory;
import model.ParticipantEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OwnerThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ApartmentActionPlace actionPlace;
    private Owner owner;
    private String ownerName = "\"Хозяин потока " + this.getName().replaceAll("\\D+", "") + "\"";

    OwnerThread(OwnerFactory ownerFactory) {
        this.owner = ownerFactory.createParticipant();
        this.actionPlace = ownerFactory.getActionPlace();
    }

    public void run() {
        try {
            while (!actionPlace.enterIfCan(ParticipantEnum.OWNER)) {
                synchronized (actionPlace) {
                    actionPlace.wait();
                }
            }

            while (!owner.isThingListEmpty()) {
                actionPlace.put(owner.getThing(), ownerName);
            }

            actionPlace.getIsOwnerInHome().decrementAndGet();
            LOGGER.info(ownerName + " выложил все вещи в квартиру!" );

            synchronized (actionPlace) {
                actionPlace.notify();
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
}
