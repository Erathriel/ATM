package fr.ufc.l3info.oprog;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;

public class appliTest {
    private ATM atm;
    private Card card;
    private Account account;

    @Before
    public void setUp() {
        atm = new ATM();
        account = new Account(100);
        card = new MyCard();
    }

    /** Insertion d'une carte null */
    @Test(expected=ATM.NullCardException.class)
    public void testInsertCard0() throws ATM.NullCardException {
        atm.insertCard(null);
    }

    /** Insertion d'une carte alors qu'une carte est déjà insérée. */
    @Test
    public void testInsertCard1() throws ATM.NullCardException {
        int r = atm.insertCard(card);
        assertEquals(0, r);
        r = atm.insertCard(card);
        assertEquals(-1, r);
    }

    /** Insertion d'une carte bloquée */
    @Test
    public void testInsertCard2() throws ATM.NullCardException {
        // definition du comportement voulu : isBlocked doit retourner true
        Mockito.when(card.isBlocked()).thenReturn(true);
        int r = atm.insertCard(card);
        assertEquals(-2, r);
    }

    /** Saisie d'un code pin correct pour une carte bloquée */
    @Test
    public void testInsertCard3() throws ATM.NullCardException {
        // insert la carte
        int r = atm.insertCard(card);
        assertEquals(0, r);
        // definition du comportement voulu : isBlocked doit retourner true
        Mockito.when(card.isBlocked()).thenReturn(true);
        // tape le code PIN
        r = atm.inputPin(4321);
        assertEquals(-3, r);
    }

    /** Saisie d'un code pin au mauvais moment du cycle de l'ATM */
    @Test
    public void testInputPin0() {
        int r = atm.inputPin(1234);
        assertEquals(-1, r);
    }

    /** Saisie d'un code pin correct */
    @Test
    public void testInputPin1() throws ATM.NullCardException {
        int r = atm.insertCard(card);
        assertEquals(0, r);
        r = atm.inputPin(1234);
        assertEquals(0, r);
    }

    /** Saisie d'un code pin incorrect */
    @Test
    public void testInputPin2() throws ATM.NullCardException {
        int r = atm.insertCard(card);
        assertEquals(0, r);
        r = atm.inputPin(4321);
        assertEquals(-2, r);
    }

    /** Demande de retrait au mauvais moment du cycle de l'ATM */
    @Test
    public void testChooseAmount0() {
        int r = atm.chooseAmount(10);
        assertEquals(-1, r);
    }

    /** Demande de retrait d'une somme invalide, puis annulation et
     *  récupération de la carte et non-récupération de billets.
     */
    @Test
    public void testChooseAmount1() throws ATM.NullCardException {
        int r = atm.insertCard(card);
        assertEquals(0, r);
        r = atm.inputPin(1234);
        assertEquals(0, r);
        r = atm.chooseAmount(10);
        assertEquals(-2, r);
        r = atm.chooseAmount(0);
        assertEquals(0, r);
        Card c = atm.takeCard();
        assertEquals(card, c);
        int[] b = atm.takeBills();
        assertEquals(null, b);
    }

    @Test
    public void testChooseAmount2() throws ATM.NullCardException {
        testChooseAmountPUT(20);
    }
    @Test
    public void testChooseAmount3() throws ATM.NullCardException {
        testChooseAmountPUT(30);
    }
    @Test
    public void testChooseAmount4() throws ATM.NullCardException {
        testChooseAmountPUT(40);
    }
    @Test
    public void testChooseAmount5() throws ATM.NullCardException {
        testChooseAmountPUT(50);
    }
    @Test
    public void testChooseAmount6() throws ATM.NullCardException {
        testChooseAmountPUT(80);
    }
    @Test
    public void testChooseAmount7() throws ATM.NullCardException {
        testChooseAmountPUT(100);
    }

    /** Demande de retrait d'une somme non autorisée */
    @Test
    public void testChooseAmount8() throws ATM.NullCardException {
        Mockito.when(account.canWithdraw(anyInt())).thenReturn(false);
        int r = atm.insertCard(card);
        assertEquals(0, r);
        r = atm.inputPin(1234);
        assertEquals(0, r);
        r = atm.chooseAmount(20);
        assertEquals(-3, r);
    }

    /**
     * Test unitaire paramétré par le montant à retirer.
     * PUT = Paramaterized Unit Test
     * Réalise une séquence valide jusqu'à la récupération de l'argent.
     */
    private void testChooseAmountPUT(int a) throws ATM.NullCardException {
        int r = atm.insertCard(card);
        assertEquals(0, r);
        r = atm.inputPin(1234);
        assertEquals(0, r);
        r = atm.chooseAmount(a);
        assertEquals(0, r);
        Card c = atm.takeCard();
        assertEquals(card, c);
        int[] b = atm.takeBills();
        assertFalse(b == null);
        int total = 0;
        for (int i=0; i < b.length; i++) {
            total += b[i];
        }
        assertEquals(a, total);
    }

    /** Tentative de reprendre la carte sans même l'avoir insérée */
    @Test
    public void testChooseTakeCard0() {
        Card r = atm.takeCard();
        assertEquals(null, r);
    }
}
