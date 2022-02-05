package lucas.robs.entrega2;

public class OpenBooking {
    public String idFriendPet = "";
    public boolean hasRequestBooking = false;
    public String idPetOwner = "";

    public OpenBooking(String idFriendPet) {
        this.idFriendPet = idFriendPet;
    }

    public void setIdFriendPet(String idFriendPet) {
        this.idFriendPet = idFriendPet;
    }

    public void setIdPetOwner(String idPetOwner) {
        this.idPetOwner = idPetOwner;
    }

    public void setHasRequestBooking(boolean hasRequestBooking) {
        this.hasRequestBooking = hasRequestBooking;
    }

    public String getIdFriendPet() {
        return idFriendPet;
    }

    public boolean isHasRequestBooking() {
        return hasRequestBooking;
    }

    public String getIdPetOwner() {
        return idPetOwner;
    }

    @Override
    public String toString() {
        return "OpenBooking{" +
                "idFriendPet='" + idFriendPet + '\'' +
                ", hasRequestBooking=" + hasRequestBooking +
                ", idPetOwner='" + idPetOwner + '\'' +
                '}';
    }
}
