import com.ruslan.pricelist.utility.StringComparisonUtility;
import org.junit.Test;

/**
 * Created by Ruslan on 1/29/2017.
 */
public class StringComparisonTest {
    @Test
    public void test() {

        String a = "Айкрол гл. капли 4% 50мл №1", b = "Айкрол глаз.кап 4%  10мл №1";
        String numberOnly1 = a.replaceAll("[^0-9]", "");
        System.out.println(numberOnly1);
        String numberOnly2 = b.replaceAll("[^0-9]", "");
        System.out.println(numberOnly2);

        if (StringComparisonUtility.isSame(a, b)) {
            if (numberOnly1.equals(numberOnly2))
                System.out.println("Strings are same!!!");
            else System.out.println("Strings are not same!!!");
        }else System.out.println("Strings are not same!!!");

    }
}
