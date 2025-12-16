package entity.animals;

public enum AnimalType {
    BIRD("Fågel"),
    CAT("Katt"),
    DOG("Hund"),
    HORSE("Häst");

    String swedish;

    AnimalType(String swedish) {
        this.swedish = swedish;
    }

    public String getSwedish() {
        return swedish;
    }

    public void setSwedish(String swedish) {
        this.swedish = swedish;
    }
}
