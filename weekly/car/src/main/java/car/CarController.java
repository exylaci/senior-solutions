package car;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<Car> carList(){
        return carService.getCars();
    }

    @GetMapping("/types")
    public List<String> typeList(){
        return carService.getTypes();
    }
}
