package fr.ufc.l3info.oprog;// JUnit

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

// Mockito
// Systeme sous test


@RunWith(MockitoJUnitRunner.class)
public class MyCardTest {

    private MyCard testCard;
    private Account mockedAccount;

    @Before
    public void setUp() {
        testCard = new MyCard();
        mockedAccount = Mockito.mock(Account.class);
    }

    /**
     *  Card personalization.
     */
    private void personalizeCard() {
        testCard.setAccount(mockedAccount);
        testCard.setPin(1234);
        testCard.endPersonalization();
    }

    /**
     * Tests de la phase de personnalisation de la carte
     */

    @Test
    public void testPerso0() {
        assertNull(testCard.getAccount());
    }

    @Test
    public void testPerso1() {
        assertFalse(testCard.setAccount(null));
    }

    @Test
    public void testPerso2() {
        assertTrue(testCard.setAccount(mockedAccount));
        assertEquals(mockedAccount, testCard.getAccount());
        assertFalse(testCard.endPersonalization());
    }

    @Test
    public void testPerso3() {
        assertFalse(testCard.setPin(-1));
    }

    @Test
    public void testPerso4() {
        assertTrue(testCard.setPin(1234));
        assertFalse(testCard.endPersonalization());
    }

    @Test
    public void testPerso5() {
        assertTrue(testCard.setAccount(mockedAccount));
        assertTrue(testCard.setPin(1234));
        assertTrue(testCard.endPersonalization());
    }

    @Test
    public void testPerso6() {
        Account a = Mockito.mock(Account.class);
        assertTrue(testCard.setAccount(mockedAccount));
        assertTrue(testCard.setAccount(a));
        assertTrue(testCard.setPin(1234));
        assertTrue(testCard.setPin(4321));
        assertTrue(testCard.endPersonalization());
        assertEquals(a, testCard.getAccount());
        assertTrue(testCard.checkPin(4321));
    }

    @Test
    public void testPerso7() {
        Account a = Mockito.mock(Account.class);
        assertTrue(testCard.setAccount(mockedAccount));
        assertTrue(testCard.setPin(1234));
        assertTrue(testCard.endPersonalization());
        assertFalse(testCard.setAccount(a));
        assertEquals(mockedAccount, testCard.getAccount());
        assertFalse(testCard.setPin(4321));
        assertFalse(testCard.checkPin(4321));
        assertTrue(testCard.checkPin(1234));
    }


    /**
     * Tests de la phase d'utilisation de la carte
     */

    @Test
    public void testUse0() {
        assertTrue(testCard.setPin(1234));
        assertFalse(testCard.checkPin(1234));
    }

    @Test
    public void testUse1() {
        personalizeCard();
        assertFalse(testCard.checkPin(-1));
    }

    @Test
    public void testUse2() {
        personalizeCard();
        assertTrue(testCard.checkPin(1234));
    }

    @Test
    public void testUse3() {
        personalizeCard();
        assertFalse(testCard.checkPin(4321));
        assertFalse(testCard.checkPin(4321));
        assertTrue(testCard.checkPin(1234));
    }


    /**
     * Tests de la phase de blocage de la carte
     */

    @Test
    public void testBlock0() {
        assertTrue(testCard.isBlocked());
        assertTrue(testCard.setPin(1234));
        assertTrue(testCard.isBlocked());
        assertTrue(testCard.setAccount(mockedAccount));
        assertTrue(testCard.isBlocked());
        assertTrue(testCard.endPersonalization());
        assertFalse(testCard.isBlocked());

    }

    @Test
    public void testBlock1() {
        personalizeCard();
        assertTrue(testCard.checkPin(1234));
        assertFalse(testCard.isBlocked());
        assertFalse(testCard.checkPin(4321));
        assertFalse(testCard.isBlocked());
        assertFalse(testCard.checkPin(4321));
        assertFalse(testCard.isBlocked());
        assertFalse(testCard.checkPin(4321));
        assertTrue(testCard.isBlocked());


    }
    @Test
    public void testBlock2() {

    }




}
