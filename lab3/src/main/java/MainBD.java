import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainBD {
    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        CarRepository carRepo=new CarsDBRepository(props);

        //carRepo.add(new Car("Tesla","Model S", 2019));

        System.out.println("Toate masinile din db");
        for(Car car:carRepo.findAll())
            System.out.println(car);

        String manufacturer="Tesla";
        System.out.println("Masinile produse de "+manufacturer);
        for(Car car:carRepo.findByManufacturer(manufacturer))
            System.out.println(car);

        //carRepo.update(6, new Car("Dacia", "Sandero", 2015));
        carRepo.update(6, new Car("Tesla", "Model Y", 2013));

        int min = 2005;
        int max = 2015;
        System.out.println("Masinile dintre anii " + min + " si " + max);
        for(Car car : carRepo.findBetweenYears(min, max)){
            System.out.println(car);
        }
    }
}
