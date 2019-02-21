package model.Thief;

import model.ParticipantFactory;

public class ThiefFactory extends ParticipantFactory {

    @Override
    public Thief createParticipant() {
        return new Thief();
    }
}
