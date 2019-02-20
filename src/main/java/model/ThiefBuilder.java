package model;

import controller.ApartmentActionPlace;

public class ThiefBuilder {


    public static Thief createThief(Backpack backpack, ApartmentActionPlace actionPlace) {
        backpack.setMaxWeight(backpack.getMaxWeight());
        return new Thief(backpack, actionPlace);
    }
}
