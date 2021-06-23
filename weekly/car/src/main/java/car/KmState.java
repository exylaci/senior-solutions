package car;

import java.time.LocalDate;

public class KmState {
    private LocalDate localDate;
    private int km;

    public KmState(LocalDate localDate, int km) {
        this.localDate = localDate;
        this.km = km;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public int getKm() {
        return km;
    }
}
