import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoanTest {
    private Loan myLoan;
    /**
     * Set up the test environment by initializing a new Loan object before the test methods.
     */
    @BeforeEach
    void setUp() {
        // Initialising a new Loan object before the test
        myLoan = new Loan("340096334", "B00447489", "27/04/2024", "22/06/2024", 0);
    }
    @AfterEach
    void tearDown() {
        myLoan = null;  // This cleans up resources after each test
    }
    /**
     * Test of the incrementRenews method of the Loan class.
     */
    @Test
    void testIncrementRenews() {
        // Arrange
        int expResult = myLoan.getNumberOfRenews() + 1;
        // Act
        myLoan.incrementRenews();
        int actualResult = myLoan.getNumberOfRenews();

        // Assert
        assertEquals(expResult, actualResult, "Number of renews should be incremented by 1");
    }
}


