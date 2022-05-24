package de.hdm_stuttgart.mi;

import de.hdm_stuttgart.mi.Wgs.ShoppingList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ShoppingListTest {

    private static ShoppingList shoppingList;

    @Before
    public void setup() {
        shoppingList = ShoppingList.getShoppingListInstance();
        shoppingList.add("Zitronen");
        shoppingList.add("Apfel");
        shoppingList.add("Bananen");
    }

    @After
    public void teardown() {
        shoppingList.setShoppingListInstanceNull();
    }

    @Test
    public void addTest() {
        shoppingList.add("Erdbeeren");
        Assert.assertEquals("[Zitronen, Apfel, Bananen, Erdbeeren]", shoppingList.getNotes().toString());
    }

    @Test
    public void removeTest() {
        shoppingList.remove("Zitronen");
        Assert.assertEquals("[Apfel, Bananen]", shoppingList.getNotes().toString());
    }

    @Test
    public void removeAllTest() {
        shoppingList.removeAll();
        Assert.assertEquals(new ArrayList<String>(), shoppingList.getNotes());
    }

    @Test
    public void sortTest() {
        shoppingList.sort();
        Assert.assertEquals("[Apfel, Bananen, Zitronen]", shoppingList.getNotes().toString());

        shoppingList.add("Ananas");
        shoppingList.sort();
        Assert.assertEquals("[Ananas, Apfel, Bananen, Zitronen]", shoppingList.getNotes().toString());

        shoppingList.add("Trauben");
        shoppingList.sort();
        Assert.assertNotEquals("[Ananas, Apfel, Bananen, Zitronen, Trauben]", shoppingList.getNotes().toString());
    }
}
