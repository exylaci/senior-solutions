package nav;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntervalDTO {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime begin;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
}
