import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Main {
  /**
   * Solves a solitaire boarg game using width search.
   * 
   * @return End state which is a solution.
   */
  private static String solve() {
    long time = System.currentTimeMillis();
    AiSolver deck = new Deck();
    HashSet<AiSolver> duplicates = new HashSet<AiSolver>();
    ArrayList<AiSolver> todo = new ArrayList<AiSolver>();
    ArrayList<AiSolver> children = new ArrayList<AiSolver>();
    todo.add(deck);
    while (!todo.isEmpty()) {
      AiSolver current = todo.get(0);
      todo.remove(0);
      if (current.isSolution()) {
        current.printSolution();
        double diff = (System.currentTimeMillis() - time) / 1000.0;
        System.out.println("Duration: " + diff + "s");
        return current.toString();
      } else {
        duplicates.add(current);
        children.addAll(current.expand());
        Iterator<AiSolver> iter = children.iterator();
        while (iter.hasNext()) {
          AiSolver currentChildDeck = iter.next();
          if (!duplicates.contains(currentChildDeck)) {
            todo.add(currentChildDeck);
          }
        }
        duplicates.addAll(children);
        children.clear();
      }
    }
    return "No solution was found";
  }

  public static void main(String[] args) {
    try {
      System.out.println("Solution: " + '\n');
      System.out.println(solve());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
