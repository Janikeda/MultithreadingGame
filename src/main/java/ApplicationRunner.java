import controller.ApartmentActionPlace;
import model.Owner;
import model.Thief;

public class ApplicationRunner {

    public static void main(String[] args) {
        ApartmentActionPlace actionPlace = new ApartmentActionPlace();
        new OwnerThread(actionPlace, new Owner()).start();
        new ThiefThread(actionPlace, new Thief()).start();
    }
}