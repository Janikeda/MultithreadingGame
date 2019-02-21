package model;

import controller.ApartmentActionPlace;

public abstract class ParticipantFactory {

    public abstract Participant createParticipant();

    public ApartmentActionPlace getActionPlace() {
        return ApartmentActionPlace.getInstance();
    }
}
