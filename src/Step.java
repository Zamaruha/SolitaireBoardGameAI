
public class Step {
  Field source;
  Field target;

  public Step() {
    // TODO Auto-generated constructor stub
  }

  public Step(Field source, Field target) {
    this.source = source;
    this.target = target;
    // System.out.println(this);
  }

  public Field getSource() {
    return source;
  }

  public Field getTarget() {
    return target;
  }

  public void setSource(Field source) {
    this.source = source;
  }

  public void setTarget(Field target) {
    this.target = target;
  }

  @Override
  public String toString() {
    return "Step [source=" + source + ", target=" + target + "]";
  }

}
