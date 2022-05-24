package de.hdm_stuttgart.mi;

import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Wgs.Wg;
import de.hdm_stuttgart.mi.Wgs.WgManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WgTest {

    private static User user1 = new User("rolf123", "asdfA12&", "Rolf", "rolf@mail.com", "30.04.1998");
    private static Wg wg1 = new Wg("Wg1", "Asd123!?");
    private static WgManager wgManager;

    @Before
    public void setup() {
        wgManager = WgManager.getWgInstance();
        wgManager.addWg(wg1);
    }

    @After
    public void teardown() {

    }

    @Test
    public void addUserTest() {
        wg1.addUser(user1);
        Assert.assertEquals( wg1.getWgID(), user1.getWgID());
    }

    @Test
    public void removeUserTest() {
        wg1.removeUser(user1);
        Assert.assertEquals(0,user1.getWgID());
    }

}
