import controller.ApartmentActionPlace;
import model.Backpack;
import model.OwnerBuilder;
import model.ThiefBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ApplicationRunner {

    public static void main(String[] args) {
        List<Thread> ownersAndThieves = new ArrayList<>();

        ApartmentActionPlace actionPlace = new ApartmentActionPlace();
        ReentrantLock reentrantLock = new ReentrantLock();

        OwnerThread ownerThread;
        ThiefThread thiefThread;

        for (int i = 0; i < ApartmentActionPlace.AMOUNT_THREADS_OWNER; i++) {
            ownerThread = new OwnerThread(actionPlace, OwnerBuilder.createOwner(new Backpack()), reentrantLock);
            ownersAndThieves.add(ownerThread);
        }
        for (int i = 0; i < ApartmentActionPlace.AMOUNT_THREADS_THIEF; i++) {
            thiefThread = new ThiefThread(actionPlace, ThiefBuilder.createThief(new Backpack(), actionPlace), reentrantLock);
            ownersAndThieves.add(thiefThread);
        }

        Collections.shuffle(ownersAndThieves);
        for (Thread thread : ownersAndThieves) {
            thread.start();
        }
    }
}