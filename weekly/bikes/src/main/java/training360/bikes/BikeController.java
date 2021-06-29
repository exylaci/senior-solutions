package training360.bikes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BikeController {

    private BikeService bikeService;

    private BikeController(BikeService bikeService){
        this.bikeService=bikeService;
    }

    @GetMapping("/")
    public String getStart(){
        return "Üdv a biciklikölcsönzőben!";
    }

    @GetMapping("/history")
    public List<Bike> getBikes(){
//        return List.of(new Bike("bike","user", LocalDateTime.of(2000,12,31,17,45),345.3));
        return bikeService.getBikes();
    }

    @GetMapping("/users")
    public List<String> getUsers(){
        return bikeService.getUsers();
    }
}
