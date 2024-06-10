package Objects;

public class Room {
    private int roomNumber;
    private boolean isVacant;
    private String reservedBy;
    private String reservedUntil;

    public Room() {
        this.roomNumber = 0;
        this.isVacant = false;
    }

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isVacant = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isVacant() {
        return isVacant;
    }

    public void setVacant(boolean vacant) {
        isVacant = vacant;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    public String getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(String reservedUntil) {
        this.reservedUntil = reservedUntil;
    }
}
