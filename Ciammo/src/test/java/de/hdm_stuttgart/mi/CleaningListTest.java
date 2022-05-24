package de.hdm_stuttgart.mi;

import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Wgs.CleaningList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CleaningListTest {
    private static User user1 = new User("rolf123", "asdfA12&", "Rolf", "rolf@mail.com", "30.04.1998");
    private static User user2 = new User("HUGO", "%-HuGo1", "Hugo", "hugo@gmail.com", "24.06.1980");
    private static User user3 = new User("hanna99", "hanna123D!", "Hanna", "hanna@web.de", "02.09.2004");
    private static CleaningList cleaningList;

    @Before
    public void setup() {
        cleaningList = CleaningList.getCleaningListInstance();
        cleaningList.add("Küche",23.5, user1);
        cleaningList.add("Bad",1.5, user2);
        cleaningList.add("Flur",15.0, user3);
    }

    @After
    public void teardown() {
        cleaningList.setCleaningListInstanceNull();
    }

    @Test
    public void addTest() {
        cleaningList.add("Küche",20.0, user3);
        Assert.assertEquals("Küche", cleaningList.getObjects().get(3).getNote());
        Assert.assertEquals(20.0, cleaningList.getObjects().get(3).getValue(), 0);
        Assert.assertEquals(user3, cleaningList.getObjects().get(3).getUser());
    }

    @Test
    public void removeTest() {
        cleaningList.remove(cleaningList.getObjects().get(0));
        Assert.assertNotEquals("Küche", cleaningList.getObjects().get(0).getNote());
        Assert.assertEquals("Bad", cleaningList.getObjects().get(0).getNote());
        Assert.assertEquals(1.5, cleaningList.getObjects().get(0).getValue(), 0);
        Assert.assertEquals(user2, cleaningList.getObjects().get(0).getUser());
    }

    @Test
    public void isListSuccessfullyDeletedTest() {
        Assert.assertTrue(cleaningList.isListSuccessfullyDeleted());
        Assert.assertEquals(new ArrayList<>(), cleaningList.getObjects());

        Assert.assertFalse(cleaningList.isListSuccessfullyDeleted());
        Assert.assertEquals(new ArrayList<>(), cleaningList.getObjects());
    }
}
