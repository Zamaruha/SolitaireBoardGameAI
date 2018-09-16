import java.util.HashSet;


public interface AiSolver {

  /**
   *
   * @return true if current state is a solution.
   */
  boolean isSolution();

  /**
   * 
   * @return HashSet with all possible next steps.
   */
  HashSet<Deck> expand();

  /**
   * Prints solution.
   */
  void printSolution();
}
