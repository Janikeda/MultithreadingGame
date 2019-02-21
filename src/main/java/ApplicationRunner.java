import controller.ApartmentActionPlace;
import model.Owner.OwnerFactory;
import model.Thief.ThiefFactory;
import util.Statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationRunner {
    /* Количество участников (потоков)*/
    private static final int AMOUNT_THREADS_OWNER = 1000;
    private static final int AMOUNT_THREADS_THIEF = 1000;

    public static void main(String[] args) {
        /* Иницилиация бизнес-данных */
        List<Thread> ownersAndThieves = new ArrayList<>();

        OwnerThread ownerThread;
        ThiefThread thiefThread;

        for (int i = 0; i < AMOUNT_THREADS_OWNER; i++) {
            ownerThread = new OwnerThread(new OwnerFactory());
            ownersAndThieves.add(ownerThread);

        }
        for (int i = 0; i < AMOUNT_THREADS_THIEF; i++) {
            thiefThread = new ThiefThread(new ThiefFactory());
            ownersAndThieves.add(thiefThread);
        }

        /* Запуск потоков и привязка к main thread */
        Collections.shuffle(ownersAndThieves);
        for (Thread thread : ownersAndThieves) {
            thread.start();
        }
        for (Thread thread : ownersAndThieves) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* Сбор и вывод статистики */
        Statistics.showAllResults(ApartmentActionPlace.getInstance());
    }
}