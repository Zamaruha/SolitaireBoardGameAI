import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.HashSet;
import org.junit.Test;

class DeckTest {

  @Test
  void testEquals() {
    Deck d1 = new Deck();
    AiSolver d2 = new Deck(d1);
    assertEquals(d1, d2);
  }

  @Test
  void testEqualsAndHashCode() {
    Deck d1 = new Deck();
    Deck d2 = new Deck(d1);

    Field[] fields = d1.getFields();
    Field freeField = d1.getFreeField();
    int indexTarget = d1.getIndexOfField(freeField);
    Field source = d1.getFieldByCoordinates(2, 1);
    int indexSource = d1.getIndexOfField(source);
    Step step = d1.createStep(fields[indexSource], fields[indexTarget]);
    fields[indexSource] = step.getSource();
    fields[indexTarget] = step.getTarget();
    d1.setFields(fields);

    assertNotEquals(d1, d2);

    Field[] fields2 = d2.getFields();
    Field freeField2 = d2.getFreeField();
    int indexTarget2 = d2.getIndexOfField(freeField2);
    Field source2 = d2.getFieldByCoordinates(2, 1);
    int indexSource2 = d2.getIndexOfField(source2);
    Step step2 = d2.createStep(fields2[indexSource2], fields2[indexTarget2]);
    fields2[indexSource2] = step2.getSource();
    fields2[indexTarget2] = step2.getTarget();
    d2.setFields(fields2);

    assertEquals(d1, d2);

    HashSet<Deck> set = new HashSet<Deck>();
    set.add(d1);
    set.add(d2);

    assert (set.size() == 1);
  }

  @Test
  void testNeighboursOfNeighbours() {
    Deck deck = new Deck();
    System.out.println(deck);
    deck.createStep(deck.getFieldByCoordinates(0, 0), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(0, 1), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(0, 2), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(1, 0), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(1, 1), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(1, 2), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 5);
    deck.createStep(deck.getFieldByCoordinates(2, 0), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(2, 1), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 5);
    deck.createStep(deck.getFieldByCoordinates(2, 2), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 8);
    deck.createStep(deck.getFieldByCoordinates(2, 3), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 5);
    deck.createStep(deck.getFieldByCoordinates(2, 4), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(3, 2), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 5);
    deck.createStep(deck.getFieldByCoordinates(3, 3), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(3, 4), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(4, 2), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(4, 3), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
    deck.createStep(deck.getFieldByCoordinates(4, 4), deck.getFreeField());
    assert (deck.getAllStepSources().size() == 4);
  }
}
