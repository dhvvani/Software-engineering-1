package business;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import objects.Car;
import business.CarHandler;
public class CarFilterManager {
    private final CarHandler data;
    private final List<Car> cars;

    public CarFilterManager(CarHandler data) {
        this.data = data;
        this.cars = data.getAllCars();
    }

    public ArrayList<String> getDistinctMakes() {
        return new ArrayList<>(cars.stream()
                .map(Car::getMake)
                .distinct()
                .collect(Collectors.toList()));
    }

    public ArrayList<String> getDistinctTransmissions() {
        return new ArrayList<>(cars.stream()
                .map(Car::getTrans)
                .distinct()
                .collect(Collectors.toList()));
    }

    public ArrayList<String> getDistinctFuels() {
        return new ArrayList<>(cars.stream()
                .map(Car::getFuel)
                .distinct()
                .collect(Collectors.toList()));
    }

    public ArrayList<String> getModelsForMake(String make) {
        return new ArrayList<>(cars.stream()
                .filter(car -> car.getMake().equals(make))
                .map(Car::getModel)
                .distinct()
                .collect(Collectors.toList()));
    }
}
