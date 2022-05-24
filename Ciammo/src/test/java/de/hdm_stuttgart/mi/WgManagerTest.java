package de.hdm_stuttgart.mi;

import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Users.UserManager;
import de.hdm_stuttgart.mi.Wgs.Wg;
import de.hdm_stuttgart.mi.Wgs.WgManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class WgManagerTest {

    private static Wg wg1 = new Wg("Wg1", "Asd123!?");
    private static Wg wg2 = new Wg("Wg2", "uxV67%");
    private static Wg wg3 = new Wg("Wg3", "0uA-1");
    private static Wg wg4 = new Wg("Wg4", "uAsid_2");
    private static Wg wg5 = null;

    private static User user1 = new User("rolf123", "asdfA12&", "Rolf", "rolf@mail.com", "30.04.1998");
    private static User user2 = new User("HUGO", "%-HuGo1", "Hugo", "hugo@gmail.com", "24.06.1980");
    private static User user3 = new User("rudi", "%-ruGo1", "Rudolf", "rudi@gmail.com", "12.06.1980");

    private static WgManager wgManager;
    private static UserManager userManager;

    @Before
    public void setup() {
        wgManager = WgManager.getWgInstance();
        wgManager.addWg(wg1);
        wgManager.addWg(wg2);
        wgManager.addWg(wg3);
    }

    @After
    public void teardown() {
        //wgManager = null;
        wgManager.setWgInstanceNull();
        if (userManager != null) {
            userManager.setInstanceNull();
        }
    }

    public void setupWithUsers() {
        userManager = UserManager.getInstance();
        userManager.addUser(user1);
        userManager.addUser(user2);
        userManager.addUser(user3);
        wgManager.getWgs().get(0).addUser(user1); // add user to wg1
        wgManager.getWgs().get(1).addUser(user2); // add user to wg2
        wgManager.getWgs().get(2).addUser(user3); // add user to wg3
    }

    @Test
    public void getWgsTest() {
        ArrayList<Wg> wgArrayList = new ArrayList<>();
        wgArrayList.add(wg1);
        wgArrayList.add(wg2);
        wgArrayList.add(wg3);
        Assert.assertEquals(wgArrayList, wgManager.getWgs());

        wgArrayList.add(wg4);
        Assert.assertNotEquals(wgArrayList, wgManager.getWgs());

        wgArrayList.remove(wg4);
        wgArrayList.remove(wg3);
        Assert.assertNotEquals(wgArrayList, wgManager.getWgs());
    }

    @Test
    public void addWgTest() {
        Assert.assertTrue(wgManager.addWg(wg4));
        Assert.assertEquals("Wg1, Wg2, Wg3, Wg4", wgManager.toString());

        Assert.assertFalse(wgManager.addWg(wg5));
        Assert.assertNotEquals("Wg1, Wg2, Wg3, Wg4, Wg5", wgManager.toString());
    }

    @Test
    public void deleteWgTest() {
        setupWithUsers();

        wgManager.deleteWg(wg1);
        Assert.assertEquals(0, userManager.getUser("rolf123").getWgID());
        Assert.assertEquals("Wg2, Wg3", wgManager.toString());

        wgManager.deleteWg(wg2);
        Assert.assertNotEquals("Wg2, Wg3", wgManager.toString());
    }

    @Test
    public void removeUserFromWgTest() {
        setupWithUsers();

        wgManager.removeUserFromWg(wg2, user2);
        Assert.assertNotEquals(wg2.getWgID(), userManager.getUser("HUGO").getWgID());

        wgManager.removeUserFromWg(wg3, user3);
        wgManager.removeUserFromWg(wg3, user3);
        Assert.assertEquals(0, userManager.getUser("rudi").getWgID());
        Assert.assertEquals(new ArrayList<User>(), wgManager.getWgs().get(2).getUsers());
    }

    @Test
    public void loginTest() {
        Assert.assertTrue(wgManager.login(wg1.getWgID(), wg1.getWgPassword(), user2));
        ArrayList<User> test = new ArrayList<>();
        test.add(user2);
        Assert.assertEquals(test, wgManager.getWgs().get(0).getUsers());

        Assert.assertFalse(wgManager.login(wg1.getWgID(), wg2.getWgPassword(), user1));
        Assert.assertFalse(wgManager.login(wg2.getWgID(), wg1.getWgPassword(), user1));
        Assert.assertFalse(wgManager.login(wg4.getWgID(), wg4.getWgPassword(), user1));
    }
}