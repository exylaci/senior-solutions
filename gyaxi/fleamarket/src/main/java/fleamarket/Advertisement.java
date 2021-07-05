package fleamarket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement {
    private Long id;
    private LumberCategory lumberCategory;
    private String text;
    private LocalDateTime timeStamp;

    public Advertisement(Long id, CreateAdvertisementCommand command) {
        this.id = id;
        this.lumberCategory = command.getLumberCategory();
        this.text = command.getText();
        timeStamp = LocalDateTime.now();
    }

    public void setText(String text) {
        this.text = text;
        timeStamp = LocalDateTime.now();
    }
}