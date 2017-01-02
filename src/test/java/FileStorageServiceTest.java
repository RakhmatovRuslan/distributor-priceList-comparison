
import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.exception.DistributorFileParsingException;
import com.ruslan.pricelist.utility.ExcelTemplateParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by Ruslan on 12/29/2016.
 */
public class FileStorageServiceTest {
    
    ExcelTemplateParser templateParser;
    @Before
    public void init(){
        templateParser = new ExcelTemplateParser();
    }
    
    @Test
    public void test()  {
        File file = new File(FileStorageServiceTest.class.getClassLoader().getResource("etalon.xlsx").getFile());
        try{
            Distributor distributor = templateParser.parseDistributorFile(file);
            Assert.assertEquals("Tatanya Farm",distributor.getName());
        }catch(DistributorFileParsingException de){
            System.out.println(de.getMessage());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
