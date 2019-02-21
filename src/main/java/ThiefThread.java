import controller.ApartmentActionPlace;
import model.ParticipantEnum;
import model.Thief.Thief;
import model.Thief.ThiefFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Statistics;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThiefThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ApartmentActionPlace actionPlace;
    private Thief thief;
    private static String sdf = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));

    ThiefThread(ThiefFactory thiefFactory) {
        this.actionPlace = thiefFactory.getActionPlace();
        this.thief = thiefFactory.createParticipant();
    }

    public void run() {
        steal();
    }

    private void steal() {
        try {
            while (!actionPlace.enterIfCan(ParticipantEnum.THIEF)) {
                synchronized (actionPlace) {
                    actionPlace.wait();
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }

        LOGGER.info(sdf + " " +thief.getThiefName() + " в доме!");

        thief.snatchFromApartment(actionPlace.getThingList());

        actionPlace.getIsThiefInHome().decrementAndGet();

        Statistics.showThiefResult(thief);
        LOGGER.info(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())) + " " + thief.getThiefName() + " вышел из дома!");

        synchronized (actionPlace) {
            actionPlace.notify();
        }
    }
}
