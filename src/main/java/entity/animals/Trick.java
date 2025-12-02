package entity.animals;

public enum Trick {
    SIT("sitt"),
    SPEAK("tala"),
    LIEDOWN("Ligg ner"),
    PLAYDEAD("spela död"),
    HISS("fräs"),
    GROWL("morra"),
    OPENMOUTH("öppna mun"),
    ROLL("rulla runt"),
    BEHELD("bli hållen"),
    FETCH("hämta"),
    PAW("vacker tass"),
    GUARD("vakta"),
    SPIN("snurra"),
    HIGHFIVE("high five"),
    WAIT("vänta"),
    FOLLOW("följ"),
    GOAHEAD("varsågod");

    private final String swedish;

    Trick(String swedish) {
        this.swedish = swedish;
    }

    @Override
    public String toString() {
        return swedish;
    }
}
