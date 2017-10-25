package fr.ufc.l3info.oprog;// JUnit
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

// Mockito
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

public class accountTest {
    
    Account accountUnderTest = null;
    
    @Before 
    public void setUp() {
        accountUnderTest = new Account(1000);
    }
    
    @Test
    public void testCredit1() {
        assertFalse(accountUnderTest.canWithdraw(1200));
        accountUnderTest.credit(200);
        assertTrue(accountUnderTest.canWithdraw(1200));
    }

    private void assertTrue(boolean b) {
    }

    @Test
    public void testDebit1() {
        boolean ok = accountUnderTest.debit(500);
        assertTrue(ok);
    }

    @Test
    public void testDebit2() {
        boolean ok = accountUnderTest.debit(1000);
        assertEquals(ok, true);
    }

    @Test
    public void testDebit3() {
        boolean nok = accountUnderTest.debit(1001);
        assertEquals(nok, false);
    }

}
