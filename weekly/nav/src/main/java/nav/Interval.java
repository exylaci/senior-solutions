package nav;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Interval {

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime begin;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime end;

    public Interval(LocalDateTime begin, LocalDateTime end) {
        if (begin.isAfter(end)) {
            throw new IllegalAppointmentException("End cannot be earlier than the beginning!");
//            throw new IllegalArgumentException("End cannot be earlier than the beginning!");
        }
        this.begin = begin;
        this.end = end;
    }
}
