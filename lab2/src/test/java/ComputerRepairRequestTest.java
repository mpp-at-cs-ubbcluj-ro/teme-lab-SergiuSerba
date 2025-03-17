import model.ComputerRepairRequest;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {
    @Test
    @DisplayName("First test")
    public void test1(){
        ComputerRepairRequest crr = new ComputerRepairRequest();
        assertEquals("", crr.getOwnerName());
        assertEquals("", crr.getPhoneNumber());
        assertEquals(0, crr.getID());
    }

    @Test
    @DisplayName("Second test")
    public void test2(){
       ComputerRepairRequest crr = new ComputerRepairRequest(1,"A A","Address A","072222","Asus","13/10/2020","Broken display");
        assertEquals(1,crr.getID());
        assertEquals("A A", crr.getOwnerName());
    }

      
}
