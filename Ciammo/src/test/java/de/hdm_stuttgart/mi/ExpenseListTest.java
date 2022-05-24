package de.hdm_stuttgart.mi;

import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Wgs.ExpenseList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ExpenseListTest {

    private static User user1 = new User("rolf123", "asdfA12&", "Rolf", "rolf@mail.com", "30.04.1998");
    private static User user2 = new User(" ", "hanna123D!", "Hanna", "hanna@web.de", "02.09.2004");
    private static ExpenseList expenseList;

    @Before
    public void setup() {
        expenseList = ExpenseList.getExpenseListInstance();
        expenseList.add("Bananen", 3.99, user1);
        expenseList.add("Tomaten", 1.50, user1);
    }

    @After
    public void teardown() {
        expenseList.setExpenseListInstanceNull();
    }

    @Test
    public void addTest() {
        expenseList.add("Trauben", 4.99, user1);
        Assert.assertEquals("Trauben", expenseList.getObjects().get(2).getNote());
        Assert.assertEquals(4.99, expenseList.getObjects().get(2).getValue(), 0);
        Assert.assertEquals(user1, expenseList.getObjects().get(2).getUser());
    }

    @Test
    public void filterUserExpensesTest() {
        Assert.assertEquals(5.49, expenseList.filterUserExpenses(user1.getUsername()), 0);

        expenseList.add("Trauben", 4.99, user1);
        Assert.assertNotEquals(5.49, expenseList.filterUserExpenses(user1.getUsername()), 0);

        Assert.assertEquals(-1, expenseList.filterUserExpenses(user2.getUsername()), 0);
    }

    @Test
    public void removeTest() {
        expenseList.remove(expenseList.getObjects().get(0));
        Assert.assertNotEquals("Bananen", expenseList.getObjects().get(0).getNote());
        Assert.assertEquals("Tomaten", expenseList.getObjects().get(0).getNote());
        Assert.assertEquals(1.50, expenseList.getObjects().get(0).getValue(), 0);
        Assert.assertEquals(user1, expenseList.getObjects().get(0).getUser());
    }

    @Test
    public void isListSuccessfullyDeletedTest() {
        Assert.assertTrue(expenseList.isListSuccessfullyDeleted());
        Assert.assertEquals(new ArrayList<>(), expenseList.getObjects());

        Assert.assertFalse(expenseList.isListSuccessfullyDeleted());
        Assert.assertEquals(new ArrayList<>(), expenseList.getObjects());
    }
}
