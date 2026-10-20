package puzzles.fillapix.rules;

import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.fillapix.Fillapix;
import edu.rpi.legup.puzzle.fillapix.FillapixBoard;
import edu.rpi.legup.puzzle.fillapix.FillapixCell;
import edu.rpi.legup.puzzle.fillapix.FillapixCellType;
import edu.rpi.legup.puzzle.fillapix.rules.FinishWithBlackDirectRule;
import edu.rpi.legup.save.InvalidFileFormatException;
import java.awt.*;
import legup.MockGameBoardFacade;
import legup.TestUtilities;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FinishWithBlackDirectRuleTest {
    private static final FinishWithBlackDirectRule RULE = new FinishWithBlackDirectRule();
    private static Fillapix fillapix;

    @BeforeClass
    public static void setUp() {
        MockGameBoardFacade.getInstance();
        fillapix = new Fillapix();
    }

    /**
     * Tests the Finish With Black Direct Rule for a value square that needs 9 black squares where it's surrounded
     * by black squares and the value square is colored black in the middle of the 3x3 board.
     */
    @Test
    public void FinishWithBlackDirectRuleTest_3x3FillBlackTest() throws InvalidFileFormatException {
        TestUtilities.importTestBoard(
                "puzzles/fillapix.rules/FinishWithBlackDirectRule/3x3FillBlack", fillapix );
        TreeNode rootNode = fillapix.getTree().getRootNode();
        TreeTransition transition = rootNode.getChildren().get(0);
        transition.setRule(RULE);

        FillapixBoard board = (FillapixBoard) transition.getBoard(); // Reset Board
        // loop through all cells within the board
        for ( int i = 0; i < 3; i++ ) {
            for (int j = 0; j < 3; j++) {
                FillapixCell cell = board.getCell(i, j); // set the current cell
                Assert.assertNotNull(cell);

                cell.setCellType(FillapixCellType.BLACK); // 1: BLACK
                board.addModifiedData(cell);

                // Test it at final row and col
//                if ( i == 2 && j == 2 ) {
//                    Assert.assertNull(RULE.checkRule(transition));
//                }
//                else {
//                    Assert.assertNotNull(RULE.checkRule(transition));
//                }
            }
        }

        // Test once gone through entire board
        Assert.assertNull(RULE.checkRule(transition));
    }
}
