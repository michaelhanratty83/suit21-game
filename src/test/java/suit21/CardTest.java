package suit21;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testCardCreation() {
        Card card = new Card("♥", "A");
        assertEquals("♥", card.getSuit());
        assertEquals("A", card.getRank());
    }

    @Test
    void testCardValues() {
        assertEquals(10, new Card("♥", "K").getValue());
        assertEquals(10, new Card("♥", "Q").getValue());
        assertEquals(10, new Card("♥", "J").getValue());
        assertEquals(11, new Card("♥", "A").getValue());
        assertEquals(2, new Card("♥", "2").getValue());
        assertEquals(10, new Card("♥", "10").getValue());
    }

    @Test
    void testColorToggle() {
        Card card = new Card("♥", "A");
        Card.setUseColor(true);
        String colorString = card.toString();
        Card.setUseColor(false);
        String noColorString = card.toString();
        assertNotEquals(colorString, noColorString);
    }

    @Test
    void testToString() {
        Card.setUseColor(false);
        Card card = new Card("♥", "A");
        assertEquals("A♥", card.toString());
    }
}
