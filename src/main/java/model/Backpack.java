package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Backpack {
    /* Рандомный размер рюкзака: от 65 до 45*/
    private int maxBackpackWeight;
    /* Коллекция для хранения вещей */
    private List<Thing> backpackList = new ArrayList<>();

    public Backpack() {
        maxBackpackWeight = new Random().nextInt(65 - 45 + 1) + 45;
    }


    public int getMaxWeight() {
        return maxBackpackWeight;
    }

    public List<Thing> getBackpackList() {
        return backpackList;
    }

    public void setBackpackList(List<Thing> backpackList) {
        this.backpackList = backpackList;
    }
}
