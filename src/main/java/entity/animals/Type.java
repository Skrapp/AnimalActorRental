package entity.animals;

public enum Type {
    BIRD("Fågel"),
    CAT("Katt"),
    DOG("Hund"),
    HORSE("Häst");

    String swedish;

    Type(String swedish) {
        this.swedish = swedish;
    }

    public String getSwedish() {
        return swedish;
    }

    public void setSwedish(String swedish) {
        this.swedish = swedish;
    }
}
