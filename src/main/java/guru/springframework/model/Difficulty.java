package guru.springframework.model;

public enum Difficulty {
    EASY("Easy"),
    MODERATE("Moderate"),
    KIND_OF_HARD("Kind of hard"),
    HARD("Hard");

    Difficulty(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
