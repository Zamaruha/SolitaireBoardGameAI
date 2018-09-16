import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Deck implements AiSolver {
  private Field[] fields;
  private Deck parent;

  Deck() {
    createFields();
  }

  Deck(Deck deck) {
    this.parent = deck;
    // in this section a deep copy of the deck.fields is made
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
      outputStrm.writeObject(deck.fields);
      ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
      ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
      this.fields = (Field[]) objInputStream.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  Field[] getFields() {
    return fields;
  }

  public void setFields(Field[] fields) {
    this.fields = fields;
  }

  int getIndexOfField(Field field) {
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getX() == field.getX() && fields[i].getY() == field.getY()) {
        return i;
      }
    }
    throw new Error("No field was found");
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Deck)) {
      return false;
    }
    return Arrays.deepEquals(this.fields, ((Deck) obj).fields);
  }


  @Override
  public int hashCode() {
    return Arrays.deepHashCode(this.fields);
  }

  public HashSet<Deck> expand() {
    HashSet<Deck> children = new HashSet<Deck>();
    HashSet<Field> stepSources = getAllStepSources();
    Field freeField = getFreeField();
    Iterator<Field> iter = stepSources.iterator();
    int indexOfTarget = getIndexOfField(freeField);

    while (iter.hasNext()) {
      Field currentField = iter.next();
      Deck newDeck = new Deck(this);
      Field[] fields = newDeck.getFields();
      int indexOfSource = newDeck.getIndexOfField(currentField);
      ArrayList<Integer> indexesToNotBeChanged = indexesOfFieldsToNotBeChanged();
      if (!indexesToNotBeChanged.contains(indexOfSource)) {
        Step step = newDeck.createStep(fields[indexOfSource], fields[indexOfTarget]);
        fields[indexOfSource] = step.getSource();
        fields[indexOfTarget] = step.getTarget();
        checkForValidity(newDeck);
        children.add(newDeck);
      }
    }
    return children;
  }

  public void printSolution() {
    Deck s = this;
    StringBuilder solution = new StringBuilder();
    solution.insert(0, s.toString());
    s = s.parent;
    int i = 0;
    while (s != null) {
      solution.insert(0, s.toString() + " =>\n");
      i++;
      s = s.parent;
    }
    System.out.println(solution);
    System.out.println(i + " moves");
  }

  private void createFields() {
    fields = new Field[17];
    // Fill BLACK stones
    for (int fieldX = 0; fieldX < 3; fieldX++) {
      for (int fieldY = 0; fieldY < 3; fieldY++) {
        // Don't fill 2C stone
        Field field;
        if ((fieldX == 2 && fieldY == 2)) {
          field = new Field(fieldX, fieldY, Stone.NONE);
        } else {
          field = new Field(fieldX, fieldY, Stone.BLACK);
        }
        fields[fieldX * 3 + fieldY] = field;
      }
    }
    // Fill YELLOW stones
    for (int fieldX = 4; fieldX >= 2; fieldX--) {
      for (int fieldY = 4; fieldY >= 2; fieldY--) {
        // Don't fill 2C stone
        Field field;
        if ((fieldX == 2 && fieldY == 2)) {
          field = new Field(fieldX, fieldY, Stone.NONE);
        } else {
          field = new Field(fieldX, fieldY, Stone.YELLOW);
        }
        fields[fieldX * 3 + fieldY] = field;
      }
    }
  }

  @Override
  public String toString() {
    return "Deck [fields=" + Arrays.toString(fields) + "]";
  }

  Field getFreeField() {
    for (Field field : fields) {
      if (field.getStone() == Stone.NONE) {
        return field;
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see AiSolver#isSolution()
   */
  public boolean isSolution() {
    Field[] fields = this.getFields();
    for (int i = 0; i < fields.length; i++) {
      if (i < 8 && !(fields[i].getStone() == Stone.YELLOW)) {
        return false;
      }
      if (fields[8].getStone() != Stone.NONE) {
        return false;
      }
      if (i > 8 && !(fields[i].getStone() == Stone.BLACK)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Makes a step without no checks. Checks should be done before calling this method.
   * 
   * @param source a field from where a stone is taken.
   * @param target a field where the stone is placed.
   * @return Step instance that describe this action.
   */
  Step createStep(Field source, Field target) {
    Stone stone = source.getStone();
    source.setStone(Stone.NONE);
    target.setStone(stone);
    return new Step(source, target);
  }

  /**
   * Returns a field at given coordinates.
   *
   * @param x coordinate;
   * @param y coordinate;
   * @return Field instance that has this coordinates.
   */
  public Field getFieldByCoordinates(int x, int y) {
    Field output = null;
    for (Field field : fields) {
      if (field.getX() == x && field.getY() == y) {
        output = field;
      }
    }
    return output;
  }

  private HashSet<Field> getNeighboursOfField(Field field) {
    HashSet<Field> neighbourList = new HashSet<Field>();
    for (Field iterator : fields) {
      if (fieldsAreNeighbours(iterator, field)) {
        neighbourList.add(iterator);
      }
    }
    return neighbourList;
  }

  private HashSet<Field> getNeighboursOfNeigboursOfField(Field field) {
    HashSet<Field> neighboursOfNeighboursList = new HashSet<Field>();
    HashSet<Field> neighboursList = getNeighboursOfField(field);
    Iterator<Field> iter = neighboursList.iterator();
    while (iter.hasNext()) {
      Field currentField = iter.next();
      HashSet<Field> neighboursOfCurrentField = getNeighboursOfField(currentField);
      neighboursOfNeighboursList.addAll(neighboursOfCurrentField);
    }
    neighboursOfNeighboursList.remove(field);
    neighboursOfNeighboursList.removeAll(neighboursList);
    return neighboursOfNeighboursList;
  }

  private boolean fieldsHaveSameX(Field fieldA, Field fieldB) {
    return fieldA.getX() == fieldB.getX();
  }

  private boolean fieldsHaveSameY(Field fieldA, Field fieldB) {
    return fieldA.getY() == fieldB.getY();
  }

  private boolean fieldAreNeigboursByX(Field fieldA, Field fieldB) {
    return fieldsHaveSameX(fieldA, fieldB) && (Math.abs(fieldA.getY() - fieldB.getY()) == 1);
  }

  private boolean fieldAreNeigboursByY(Field fieldA, Field fieldB) {
    return (fieldsHaveSameY(fieldA, fieldB)) && (Math.abs(fieldA.getX() - fieldB.getX()) == 1);
  }

  private boolean fieldsAreNeighbours(Field fieldA, Field fieldB) {
    return (fieldAreNeigboursByX(fieldA, fieldB) || fieldAreNeigboursByY(fieldA, fieldB));
  }

  private HashSet<Field> getPerpendicularNeighboursOfNeighboursOfField(Field field) {
    HashSet<Field> neighboursOfNeighbours = getNeighboursOfNeigboursOfField(field);
    Iterator<Field> iter = neighboursOfNeighbours.iterator();
    while (iter.hasNext()) {
      Field currentNeighbourOfNeighbours = iter.next();
      if (!(fieldsHaveSameX(field, currentNeighbourOfNeighbours)
          || fieldsHaveSameY(field, currentNeighbourOfNeighbours))) {
        iter.remove();
      }
    }
    return neighboursOfNeighbours;
  }

  HashSet<Field> getAllStepSources() {
    HashSet<Field> stepSources = new HashSet<Field>();
    Field freeField = getFreeField();
    HashSet<Field> neighboursOfFreeField = getNeighboursOfField(freeField);
    HashSet<Field> perpendicularNeighboursOfNeighboursOfField =
        getPerpendicularNeighboursOfNeighboursOfField(freeField);
    stepSources.addAll(neighboursOfFreeField);
    stepSources.addAll(perpendicularNeighboursOfNeighboursOfField);
    return stepSources;
  }

  private void checkForValidity(Deck deck) {
    int countNone = 0;
    for (int i = 0; i < deck.getFields().length; i++) {
      if (deck.getFields()[i].getStone() == Stone.NONE) {
        countNone++;
      }
      if (countNone == 2) {
        throw new Error("Not valid state at index " + i + '\n' + deck);
      }
    }
  }

  /**
   * Gets indexes of fields that for sure should not be changed.
   * 
   * @return array list with indexes of fields that should not be changed.
   */
  private ArrayList<Integer> indexesOfFieldsToNotBeChanged() {
    ArrayList<Integer> correct = new ArrayList<Integer>();
    if (fields[0].getStone() == Stone.YELLOW) {
      correct.add(0);
      if (fields[1].getStone() == Stone.YELLOW && fields[3].getStone() == Stone.YELLOW) {
        correct.add(1);
        correct.add(3);
      }
    }
    if (fields[16].getStone() == Stone.BLACK) {
      correct.add(16);
      if (fields[13].getStone() == Stone.YELLOW && fields[15].getStone() == Stone.YELLOW) {
        correct.add(13);
        correct.add(15);
      }
    }
    return correct;
  }
}
