package puzzles.fillapix.rules;

import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.fillapix.Fillapix;
import edu.rpi.legup.puzzle.fillapix.FillapixBoard;
import edu.rpi.legup.puzzle.fillapix.FillapixCell;
import edu.rpi.legup.puzzle.fillapix.FillapixCellType;
import edu.rpi.legup.puzzle.fillapix.rules.MirrorDirectRule;
import edu.rpi.legup.save.InvalidFileFormatException;
import legup.MockGameBoardFacade;
import legup.TestUtilities;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MirrorDirectRuleTest {

    private static final MirrorDirectRule RULE = new MirrorDirectRule();
    private static Fillapix fillapix;

    @BeforeClass
    public static void setUp() {
        MockGameBoardFacade.getInstance();
        fillapix = new Fillapix();
    }

    /**
     * Tests the Mirror Direct Rule on a 5x4 board.
     * There are two value tiles of 6 in 2nd & 3rd columns of the middle row.
     * Currently, the left 6-Tile has 3 black tiles in range, so the right 6-Tile needs 3 black tiles as well.
     * This colors the right column of the board so it matches the precolored left column.
     */
    @Test
    public void MirrorDirectRuleTest_5x4Sides6Match () throws InvalidFileFormatException {
        TestUtilities.importTestBoard(
                "puzzles/fillapix/rules/MirrorDirectRule/5x4Sides6Match", fillapix );
        TreeNode rootNode = fillapix.getTree().getRootNode();
        TreeTransition transition = rootNode.getChildren().getFirst();
        transition.setRule(RULE);

        FillapixBoard board = (FillapixBoard) transition.getBoard(); // Reset Board

        // loop through all cells within the board. i = x, j = y
        for ( int i = 0; i < 4; i++ ) {
            for ( int j = 0; j < 5; j++ ) {
                FillapixCell cell = board.getCell(i, j); // set the current cell
                Assert.assertNotNull(cell);

                // only color black the 4th (last) column but not in 1st & last row
                if ( i == 3 && j != 0 && j != 4 ) {
                    cell.setCellType(FillapixCellType.BLACK); // 1: BLACK
                    board.addModifiedData(cell);
                }

            }
        }

        // Test once gone through entire board
        Assert.assertNull(RULE.checkRule(transition));
    }
}
