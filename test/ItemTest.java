import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    /**
     * Test of the getLoanPeriod method for a Book object.
     */
    @Test
    void getLoanPeriodForBook() {
        // Arrange
        Book book = new Book("550999147", "Book", "Author", "testTitle", 2020, "867041559-2");
        int expectedBookLoanPeriod = 4; // book loan period is 4
        // Act
        int actualLoanPeriod = book.getLoanPeriod();
        // Assert
        assertEquals(expectedBookLoanPeriod, actualLoanPeriod, "Loan period for books should be 4 weeks");
    }

    /**
     * Test of the getLoanPeriod method for a Multimedia object.
     */
    @Test
    void getLoanPeriodForMultimediaItem() {
        // Arrange
        Multimedia multimedia  = new Multimedia("550999147", "Multimedia", "Author", "testTitle", 2020, "234541559-2");
        int expectedMultimediaLoanPeriod = 1; // multimedia loan period is 1 week
        // Act
        int actualMultimediaLoanPeriod = multimedia.getLoanPeriod();
        // Assert
        assertEquals(expectedMultimediaLoanPeriod, actualMultimediaLoanPeriod, "Loan period for multimedia items should be 1 week");
    }

}
