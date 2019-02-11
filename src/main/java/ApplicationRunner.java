import controller.ApartmentActionPlace;
import model.Owner;
import model.Thief;

public class ApplicationRunner {

    public static void main(String[] args) {
        ApartmentActionPlace actionPlace = new ApartmentActionPlace();
        OwnerThread ownerThread;
        ThiefThread thiefThread;

        for (int i = 0; i < ApartmentActionPlace.AMOUNT_THREADS_OWNER; i++) {
            ownerThread = new OwnerThread(actionPlace, new Owner());
            ownerThread.start();
        }
        for (int i = 0; i < ApartmentActionPlace.AMOUNT_THREADS_THIEF; i++) {
            thiefThread = new ThiefThread(actionPlace, new Thief());
            thiefThread.start();
        }
    }
}