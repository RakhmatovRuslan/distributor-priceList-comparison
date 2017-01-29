import com.ruslan.pricelist.beans.Nomenclature;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ruslan on 1/29/2017.
 */
public class SorterTest {
    @Test
    public void test(){
        List list  = new ArrayList<Nomenclature>();
        list.addAll(Arrays.asList(
                new Nomenclature(1L,"Ruslan"),
                new Nomenclature(2L,"Auslan"),
                new Nomenclature(3L,"uslan")
                ));
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
    }
}
