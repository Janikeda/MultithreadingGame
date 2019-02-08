import controller.ApartmentActionPlace;
import model.Owner;
import model.Thief;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationRunner {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int AMOUNT_THREADS_OWNER = 1;
    private static final int AMOUNT_THREADS_THIEF = 1;


    public static void main(String[] args) {
        ApartmentActionPlace actionPlace = new ApartmentActionPlace();

        for (int i = 0; i < AMOUNT_THREADS_OWNER; i ++) {
            new OwnerThread(actionPlace, new Owner()).start();
        }
        for (int i = 0; i < AMOUNT_THREADS_THIEF; i ++) {
            new ThiefThread(actionPlace, new Thief()).start();
        }

        //LOGGER.info("Всего в квартире осталось вещей: " + actionPlace.getAmountOfThings());
    }
}