package de.hdm_stuttgart.mi;

import de.hdm_stuttgart.mi.Helper.Utilities;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for Utilities
 */
public class UtilitiesTest {

    @Test
    public void firstLetterUpperCaseTest() {
        Assert.assertEquals("Sahne", Utilities.firstLetterUpperCase("sahne"));
        Assert.assertEquals("Milch", Utilities.firstLetterUpperCase("milch"));
        Assert.assertEquals("A", Utilities.firstLetterUpperCase("a"));
        Assert.assertNull(Utilities.firstLetterUpperCase(""));
    }

    @Test
    public void provePasswordConditionsTest_1_emptyOrUndersize() {
        Assert.assertFalse(Utilities.provePasswordConditions(""));
        Assert.assertFalse(Utilities.provePasswordConditions("A"));
        Assert.assertFalse(Utilities.provePasswordConditions("An"));
        Assert.assertFalse(Utilities.provePasswordConditions("Ann"));
        Assert.assertFalse(Utilities.provePasswordConditions("Anna"));
    }

    @Test
    public void provePasswordConditionsTest_2() {
        Assert.assertFalse(Utilities.provePasswordConditions("12345"));
        Assert.assertFalse(Utilities.provePasswordConditions("Mutter"));
        Assert.assertFalse(Utilities.provePasswordConditions("vater"));
        Assert.assertFalse(Utilities.provePasswordConditions("1design"));
        Assert.assertFalse(Utilities.provePasswordConditions("PAPA123-"));
        Assert.assertFalse(Utilities.provePasswordConditions("Test123."));
    }

    @Test
    public void provePasswordConditionsTest_3_PasswordIsTrue() {
        Assert.assertTrue(Utilities.provePasswordConditions("Test123&"));
        Assert.assertTrue(Utilities.provePasswordConditions("Test123!"));
        Assert.assertTrue(Utilities.provePasswordConditions("Ts12+34"));
        Assert.assertTrue(Utilities.provePasswordConditions("#Wow070"));
    }
}
