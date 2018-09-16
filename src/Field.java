import java.io.Serializable;

@SuppressWarnings("serial")
public class Field implements Serializable {
  private int x;
  private int y;
  private Stone stone;

  public Field() {}

  public Field(int x, int y, Stone stone) {
    super();
    this.x = x;
    this.y = y;
    this.stone = stone;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Stone getStone() {
    return stone;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setStone(Stone stone) {
    this.stone = stone;
  }

  @Override
  public String toString() {
    return "Field [x=" + x + ", y=" + y + ", stone=" + stone + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((stone == null) ? 0 : stone.hashCode());
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Field other = (Field) obj;
    if (stone != other.stone)
      return false;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }


}
