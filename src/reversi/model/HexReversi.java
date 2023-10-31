package reversi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An implementation that utilizes Reversi interface. This version
 * uses a hex-shaped board where the size is defined in the startGame() function.
 * The cells on the board can be located as where the 0th arraylist inside cellGrid is the top row
 * and the 0th element of an arrayList (row), is the most left cell in that row. The main function
 * to move pieces is the makeMove(int row, int col) function.
 *
 */
public class HexReversi implements Reversi {
  //0-th arraylist inside cellGrid is the top row
  //0-th element of an arrayList (row), is the most left cell
  List<List<Player>> cellGrid;
  
  //True is game is started
  //False is game not yet started
  boolean gameStarted;
  
  //States which player's turn it is
  Player currentTurn;

  /**
   * Constructor for HexReversi.
   */
  public HexReversi() {
    gameStarted = false;
  }

  /**
   * Starts a game of Reversi with a hex-shaped board. Takes in a integer
   * to specify the length of each side in the board.
   *
   * @param boardSize The length of an edge of the board to set to.
   * @throws IllegalArgumentException if board size is invalid
   * @throws IllegalStateException if game has already started
   */
  @Override
  public void startGame(int boardSize) throws IllegalArgumentException, IllegalStateException {
    if (boardSize < 2) {
      throw new IllegalArgumentException("Board size is too small.");
    }
    if (this.gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    // # of rows = 2 * boardSize - 1
    this.cellGrid = new ArrayList<>(2 * boardSize - 1);

    for (int rowSize = boardSize; rowSize < 2 * boardSize - 1; rowSize++) {
      this.cellGrid.add(new ArrayList<>(Collections.nCopies(rowSize, Player.EMPTY)));
    }
    for (int rowSize = 2 * boardSize - 1; rowSize >= boardSize; rowSize--) {
      this.cellGrid.add(new ArrayList<>(Collections.nCopies(rowSize, Player.EMPTY)));
    }

    this.gameStarted = true;

    //Initialize players on grid
    initPlayersOnGrid();

    //Black player moves first
    this.currentTurn = Player.BLACK;
  }

  /**
   * Initializes the board with appropriate pieces in starting locations.
   */
  private void initPlayersOnGrid() {
    //Starting from top row going down
    int center = (this.getBoardHeight() - 1) / 2;

    //Row above middle
    this.cellGrid.get(center - 1).set(center - 1, Player.BLACK); //left
    this.cellGrid.get(center - 1).set(center, Player.WHITE); //right

    //Middle row
    this.cellGrid.get(center).set(center - 1, Player.WHITE); //left
    this.cellGrid.get(center).set(center + 1, Player.BLACK); //right

    //Row below middle
    this.cellGrid.get(center + 1).set(center - 1, Player.BLACK); //left
    this.cellGrid.get(center + 1).set(center, Player.WHITE); //right
  }

  private void verifyGameStarted() throws IllegalStateException {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game hasn't started yet.");
    }
  }

  /**
   * Helper method for ensuring coordinates are in-bounds.
   *
   * @param row the row value of the coordinates
   * @param col the column value of the coordinates
   * @throws IllegalArgumentException if either of the values are out-of-bounds.
   */
  private void verifyCoordinates(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.getBoardHeight()) {
      throw new IllegalArgumentException("Row number out-of-range.");
    }
    if (col < 0 || col >= this.getRowWidth(row)) {
      throw new IllegalArgumentException("Column number out-of-range on given row");
    }
  }

  /**
   * Gives the depth of the next piece of the same color of the player in a certain direction.
   * @param player  The color of the pieces being checked for.
   * @param row     the row where the piece would be placed
   * @param col     the column where the piece would be placed
   * @param dir     a number representing the direction with 0 representing directly left
   *                and subsequent numbers rotating clockwise.
   * @return  the depth of the next piece of the same color, or -1 if there is a gap or no piece of
   *          the same color.
   */
  private int samePieceInDirection(Player player, int row, int col, int dir) {
    int depth = 1;
    int current_row = row;
    int current_col = col;
    while (depth < this.getBoardHeight()) {
      switch (dir) {
        case 0: // left
          current_col--;
          break;
        case 1: // up left
          if (current_row <= this.getBoardHeight() / 2) {
            current_col--;
          }
          current_row--;
          break;
        case 2: // up right
          if (current_row > this.getBoardHeight() / 2) {
            current_col++;
          }
          current_row--;
          break;
        case 3: // right
          current_col++;
          break;
        case 4: // down right
          if (current_row < this.getBoardHeight() / 2) {
            current_col++;
          }
          current_row++;
          break;
        case 5: // down left
          if (current_row >= this.getBoardHeight() / 2) {
            current_col--;
          }
          current_row++;
          break;
        default:
          return -1;
      }
      Player player_at_cell;
      try {
        player_at_cell = this.getPlayerAtCell(current_row, current_col);
      }
      catch (IllegalArgumentException e) {
        return -1;
      }
      if (player_at_cell == Player.EMPTY) {
        return -1;
      }
      if (player == player_at_cell) {
        return depth;
      }
      depth++;
    }
    return -1;
  }

  private boolean isMoveLegal(Player player, int row, int col) {
    if (!this.isCellEmpty(row, col)) {
      return false;
    }
    for (int dir = 0; dir < 6; dir++) {
      if (samePieceInDirection(player, row, col, dir) > 1) {
        return true;
      }
    }
    return false;
  }

  /**
   * Helper function for flipping pieces in a direction from the original placement position.
   *
   * @param player the player that placed the piece.
   * @param row the 0-indexed row where the piece was placed
   * @param col the 0-indexed column where the piece was placed
   * @param depth the number of pieces needed to be flipped in the direction specified
   * @param dir the direction on the hex grid to flip pieces in, with 0 indicating directly to the
   *            left and incrementing clockwise.
   */
  private void flipPiecesInDirection(Player player, int row, int col, int depth, int dir) {
    int current_row = row;
    int current_col = col;
    while (depth-- > 0) {
      switch (dir) {
        case 0: // left
          current_col--;
          break;
        case 1: // up left
          if (current_row <= this.getBoardHeight() / 2) {
            current_col--;
          }
          current_row--;
          break;
        case 2: // up right
          if (current_row > this.getBoardHeight() / 2) {
            current_col++;
          }
          current_row--;
          break;
        case 3: // right
          current_col++;
          break;
        case 4: // down right
          if (current_row < this.getBoardHeight() / 2) {
            current_col++;
          }
          current_row++;
          break;
        case 5: // down left
          if (current_row >= this.getBoardHeight() / 2) {
            current_col--;
          }
          current_row++;
          break;
        default:
          break;
      }
      this.cellGrid.get(current_row).set(current_col, player);
    }
  }

  /**
   * Moves a piece to the requested spot based on the row and column for the current player.
   *
   * @param row The top-down oriented row where the 0th row is the 1st row.
   * @param col The left-right oriented column where the 0th col is the left-most cell
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  @Override
  public void makeMove(int row, int col) throws IllegalArgumentException, IllegalStateException {
    this.verifyGameStarted();
    this.verifyCoordinates(row, col);

    if (!isMoveLegal(this.currentTurn, row, col)) {
      throw new IllegalStateException("Move is not allowed");
    }

    this.cellGrid.get(row).set(col, this.currentTurn);

    for (int dir = 0; dir < 6; dir++) {
      int depth = this.samePieceInDirection(this.currentTurn, row, col, dir);
      if (depth > 1) {
        this.flipPiecesInDirection(this.currentTurn, row, col, depth, dir);
      }
    }

    this.currentTurn = (this.currentTurn == Player.BLACK) ? Player.WHITE : Player.BLACK;
  }

  /**
   * Gives the current player turn.
   *
   * @return The color representing the current player's turn.
   * @throws IllegalStateException if the game hasn't started
   */
  @Override
  public Player getCurrentPlayer() throws IllegalStateException {
    this.verifyGameStarted();
    return this.currentTurn;
  }

  /**
   * Returns the player occupying the cell or if the cell is empty.
   *
   * @param row The top-down oriented row where the 0th row is the 1st row.
   * @param col The left-right oriented column where the 0th col is the left-most cell
   * @return The player in the cell or if the cell is empty at the given coordinates.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  @Override
  public Player getPlayerAtCell(int row, int col)
          throws IllegalArgumentException, IllegalStateException {
    this.verifyGameStarted();
    this.verifyCoordinates(row, col);

    return this.cellGrid.get(row).get(col);
  }

  /**
   * Returns whether the cell at the given row and column is empty or not.
   *
   * @param row The top-down oriented row where the 0th row is the 1st row.
   * @param col The left-right oriented column where the 0th col is the left-most cell
   * @return true if the cell at the given coordinates is empty or false if it is occupied.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  @Override
  public boolean isCellEmpty(int row, int col)
          throws IllegalArgumentException, IllegalStateException {
    this.verifyGameStarted();
    this.verifyCoordinates(row, col);

    return getPlayerAtCell(row, col) == Player.EMPTY;
  }

  /**
   * Signals if the game is over if there are no more moves to make.
   *
   * @return true is game is over, false is not
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {
    return !(canPlayerMove(Player.BLACK) || canPlayerMove(Player.WHITE));
  }

  /**
   * Returns whether the current player has a legal move or not.
   *
   * @param player the player whose move availability is being checked.
   * @return true is player can move, false if player cannot move
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public boolean canPlayerMove(Player player) throws IllegalStateException {
    this.verifyGameStarted();
    for (int row = 0; row < this.getBoardHeight(); row++) {
      for (int col = 0; col < this.getRowWidth(row); col++) {
        if (this.isMoveLegal(player, row, col)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Gives the number of rows in the board.
   *
   * @return  the number of rows in the cell grid.
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public int getBoardHeight() throws IllegalStateException {
    this.verifyGameStarted();
    return this.cellGrid.size();
  }

  /**
   * Returns the number of cells in the row specified.
   *
   * @return the number of cells in row specified
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    this.verifyGameStarted();
    if (row < 0 || row > this.getBoardHeight()) {
      throw new IllegalArgumentException("Row number out-of-range.");
    }
    return this.cellGrid.get(row).size();
  }

  /**
   * Passes the current players turn to the other player.
   *
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public void passTurn() throws IllegalStateException {
    this.verifyGameStarted();
    this.currentTurn = (this.currentTurn == Player.BLACK) ? Player.WHITE : Player.BLACK;
  }
}
