import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SILab2Test {

    private List<Item> itemList;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        itemList = new ArrayList<>();
    }

    @Test
    public void testNullItemsList() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("allItems list can't be null!");
        SILab2.checkCart(null, 100);
    }

    @Test
    public void testItemNameNullOrEmpty() {
        itemList.add(new Item(null, "123456", 100, 0));
        assertTrue(SILab2.checkCart(itemList, 100));

        itemList.clear();
        itemList.add(new Item("", "123456", 100, 0));
        assertTrue(SILab2.checkCart(itemList, 100));
    }

    @Test
    public void testItemBarcodeNull() {
        itemList.add(new Item("Item1", null, 100, 0));
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("No barcode!");
        SILab2.checkCart(itemList, 100);
    }

    @Test
    public void testInvalidBarcode() {
        itemList.add(new Item("Item1", "123a456", 100, 0));
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Invalid character in item barcode!");
        SILab2.checkCart(itemList, 100);
    }

    @Test
    public void testValidBarcodeWithDiscount() {
        itemList.add(new Item("Item1", "123456", 100, 0.1f));
        assertTrue(SILab2.checkCart(itemList, 90));
    }

    @Test
    public void testPriceDiscountReduction() {
        itemList.add(new Item("Item1", "012345", 400, 0.1f));
        assertTrue(SILab2.checkCart(itemList, 330));
    }

    @Test
    public void testTotalPriceLessThanOrEqualPayment() {
        itemList.add(new Item("Item1", "123456", 100, 0));
        itemList.add(new Item("Item2", "654321", 50, 0.2f));
        assertTrue(SILab2.checkCart(itemList, 110));
    }

    @Test
    public void testTotalPriceGreaterThanPayment() {
        itemList.add(new Item("Item1", "123456", 100, 0));
        itemList.add(new Item("Item2", "654321", 50, 0.2f));
        assertFalse(SILab2.checkCart(itemList, 100));
    }

    @Test
    public void testItemNameMultipleConditions() {
        itemList.add(new Item(null, "123456", 100, 0));
        assertTrue(SILab2.checkCart(itemList, 100));

        itemList.clear();
        itemList.add(new Item("", "123456", 100, 0));
        assertTrue(SILab2.checkCart(itemList, 100));

        itemList.clear();
        itemList.add(new Item("Item1", "123456", 100, 0));
        assertTrue(SILab2.checkCart(itemList, 100));
    }

    @Test
    public void testInvalidCharacterInBarcode() {
        itemList.add(new Item("Item1", "123a456", 100, 0));
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Invalid character in item barcode!");
        SILab2.checkCart(itemList, 100);
    }

    @Test
    public void testDiscountAndBarcodeCondition() {
        itemList.add(new Item("Item1", "012345", 100, 0.1f));
        assertTrue(SILab2.checkCart(itemList, 100));

        itemList.clear();
        itemList.add(new Item("Item1", "112345", 100, 0.1f));
        assertTrue(SILab2.checkCart(itemList, 90));

        itemList.clear();
        itemList.add(new Item("Item1", "012345", 100, 0));
        assertTrue(SILab2.checkCart(itemList, 100));

        itemList.clear();
        itemList.add(new Item("Item1", "112345", 100, 0));
        assertTrue(SILab2.checkCart(itemList, 100));
    }
}
