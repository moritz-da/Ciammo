package de.hdm_stuttgart.mi;

import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Users.UserManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class UserManagerTest {

    private static User user1 = new User("rolf123", "asdfA12&", "Rolf", "rolf@mail.com", "30.04.1998");
    private static User user2 = new User("HUGO", "%-HuGo1", "Hugo", "hugo@gmail.com", "24.06.1980");
    private static User user3 = new User("hanna99", "hanna123D!", "Hanna", "hanna@web.de", "02.09.2004");
    private static User user4 = new User("juli", "juli_39", "Julia", "julia@email.de", "01.01.2013");
    private static User user5 = null;
    private static UserManager userManager;

    @Before
    public void setup() {
        userManager = UserManager.getInstance();
        userManager.addUser(user1);
        userManager.addUser(user2);
        userManager.addUser(user3);
    }

    @After
    public void teardown() {
        //userManager = null;
        userManager.setInstanceNull();
    }

    @Test
    public void getUsersTest() {
        ArrayList<User> userArrayList = new ArrayList<>();
        userArrayList.add(user1);
        userArrayList.add(user2);
        userArrayList.add(user3);
        Assert.assertEquals(userArrayList, userManager.getUsers());

        userArrayList.add(user4);
        Assert.assertNotEquals(userArrayList, userManager.getUsers());

        userArrayList.remove(user4);
        userArrayList.remove(user3);
        Assert.assertNotEquals(userArrayList, userManager.getUsers());
    }

    @Test
    public void addUserTest() {
        Assert.assertTrue(userManager.addUser(user4));
        Assert.assertEquals(user4, userManager.getUser(user4.getUsername()));

        Assert.assertFalse(userManager.addUser(user5));
        Assert.assertNotEquals(5, userManager.getUsers().size());
    }

    @Test
    public void deleteUserTest() {
        Assert.assertTrue(userManager.deleteUser(user1));
        Assert.assertNull(userManager.getUser(user1.getUsername()));

        user4.setWgID(10);
        Assert.assertFalse(userManager.deleteUser(user4));
    }

    @Test
    public void resetWgIDTest() {
        user1.setWgID(5);
        userManager.resetWgID(user1);
        Assert.assertEquals(0, user1.getWgID());

        user2.setWgID(55);
        userManager.resetWgID(user2);
        Assert.assertNotEquals(55, user2.getWgID());
    }

    @Test
    public void isUsernameAvailableTest() {
        Assert.assertFalse(userManager.isUsernameAvailable(user1.getUsername()));
        Assert.assertTrue(userManager.isUsernameAvailable(user4.getUsername()));
    }

    @Test
    public void getUserTest() {
        Assert.assertEquals(user1, userManager.getUser(user1.getUsername()));
        Assert.assertNotEquals(user1, userManager.getUser(user2.getUsername()));
        Assert.assertNotEquals(user3, userManager.getUser(user2.getUsername()));
        Assert.assertNotEquals(user4, userManager.getUser(user2.getUsername()));
        Assert.assertNull(userManager.getUser(user4.getUsername()));
    }

    @Test
    public void loginTest() {
        Assert.assertTrue(userManager.login(user1.getUsername(), user1.getPassword()));
        Assert.assertFalse(userManager.login(user1.getUsername(), user2.getPassword()));
        Assert.assertFalse(userManager.login(user2.getUsername(), user1.getPassword()));
        Assert.assertFalse(userManager.login(user3.getUsername(), user4.getPassword()));
        Assert.assertFalse(userManager.login(user4.getUsername(), user3.getPassword()));
        Assert.assertFalse(userManager.login(user4.getUsername(), user4.getPassword()));
    }

    // TODO: Testmethode für loadUserManager
}
