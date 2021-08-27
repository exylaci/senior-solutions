package nav;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Interval {
    private final LocalDateTime begin;
    private final LocalDateTime end;

    public Interval(LocalDateTime begin, LocalDateTime end) {
        if (begin.isAfter(end)) {
            throw new IllegalAppointmentException("End cannot be earlier than the beginning!");
        }
        this.begin = begin;
        this.end = end;
    }
}