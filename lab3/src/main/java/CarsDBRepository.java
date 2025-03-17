import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStm = con.prepareStatement("select * from CarDB where manufacturer = ?")){
            preStm.setString(1,manufacturerN);
            try(ResultSet res = preStm.executeQuery()){
                while(res.next()){
                    int id = res.getInt("id");
                    String manu = res.getString("manufacturer");
                    String model = res.getString("model");
                    int year = res.getInt("year");
                    Car car = new Car(manu,model,year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }

        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStm = con.prepareStatement("select * from CarDB where year < ? and year > ?")){
            preStm.setInt(1, max);
            preStm.setInt(2, min);
            try(ResultSet res = preStm.executeQuery()){
                while(res.next()){
                    int id = res.getInt("id");
                    String manu = res.getString("manufacturer");
                    String model = res.getString("model");
                    int year = res.getInt("year");
                    Car car = new Car(manu,model,year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving task {}");
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStm = con.prepareStatement("insert into CarDB (manufacturer, model, year) values (?,?,?)")){
            preStm.setString(1,elem.getManufacturer());
            preStm.setString(2,elem.getModel());
            preStm.setInt(3,elem.getYear());
            int result = preStm.executeUpdate();
            logger.trace("Saved {} instances", result);
        }
        catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStm = con.prepareStatement("UPDATE carDB SET manufacturer = ?, model = ? , year = ? WHERE id = ?")){
            preStm.setString(1, elem.getManufacturer());
            preStm.setString(2, elem.getModel());
            preStm.setInt(3,elem.getYear());
            preStm.setInt(4,integer);
            preStm.executeUpdate();
        }catch(SQLException ex){
          logger.error(ex);
          System.err.println("Update error" + ex);
        }
        logger.traceExit();
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStm = con.prepareStatement("select * from CarDB")){
            try(ResultSet res = preStm.executeQuery()){
                while(res.next()){
                    int id = res.getInt("id");
                    String manu = res.getString("manufacturer");
                    String model = res.getString("model");
                    int year = res.getInt("year");
                    Car car = new Car(manu,model,year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }

	    logger.traceExit(cars);
        return cars;
    }
}
