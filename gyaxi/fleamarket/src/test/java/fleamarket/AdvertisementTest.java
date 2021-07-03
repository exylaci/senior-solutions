package fleamarket;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AdvertisementTest {

    @Test
    void setText() throws InterruptedException {

        Advertisement advertisement = new Advertisement(1L,
                new CreateAdvertisementCommand(LumberCategory.CSETRES,"Jó drágán eladnám, ha lenne rá vevő."));
        LocalDateTime announced = advertisement.getTimeStamp();
        Thread.sleep(1);
        advertisement.setText("Vihetik ingyen is.");

        assertNotEquals(announced,advertisement.getTimeStamp());
    }
}