package reversi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import reversi.controller.Player;

/**
 * An implementation that utilizes Reversi interface. This version
 * uses a hex-shaped board where the size is defined in the startGame() function.
 * The cells on the board can be located as where the 0th arraylist inside cellGrid is the top row
 * and the 0th element of an arrayList (row), is the most left cell in that row. The main function
 * to move pieces is the makeMove(int row, int col) function.
 * The cell is set up as a 2D list of Players. The outermost list contains lists of Players
 * each representing a horizontal row in the hexagonal grid. The lengths of these lists change,
 * with the length of the center row being the longest and the first and last list being the
 * shortest. Each inner list is indexed based on the column the cell is within that row.
 *
 */
public class HexReversi implements Reversi {
  /**
   * For this implementation, we decided to use a 2-D array
   * to represent the cells in our grid. We did not see a better
   * way of representing the cells using any other data structure.
   * We considered keeping track of cell coordinates with 3 dimensions, 1 for each line along the
   * hex grid, but quickly tossed the idea due to it being difficult to maintain and keep
   * consistent with itself. Also, although the shape of the board is a hexagon,
   * traversing through the grid using the ideas of rows and column was easy since it followed
   * a consistent pattern.
   */

  // 0-th arraylist inside cellGrid is the top row
  // 0-th element of an arrayList (row), is the most left cell
  private List<List<PlayerPiece>> cellGrid;
  
  // True is game is started
  // False is game not yet started
  private boolean gameStarted;
  
  // States which player's turn it is
  /** invariant * : currentTurn is never EMPTY. */
  private PlayerPiece currentTurn;

  // true if the last player passed their turn.
  private int consecutivePasses;

  private final List<ModelFeatures> features;

  protected Map<Player, PlayerPiece> playerMap;

  /**
   * Constructor for HexReversi. The board is initialized to the hexagon grid.
   * @param boardSize The length of an edge of the board to set to.
   */
  public HexReversi(int boardSize) {
    gameStarted = false;
    features = new ArrayList<>();
    this.playerMap = new HashMap<>();

    if (boardSize < 2) {
      throw new IllegalArgumentException("Board size is too small.");
    }

    // # of rows = 2 * boardSize - 1
    this.cellGrid = new ArrayList<>(2 * boardSize - 1);

    for (int rowSize = boardSize; rowSize < 2 * boardSize - 1; rowSize++) {
      this.cellGrid.add(new ArrayList<>(Collections.nCopies(rowSize, PlayerPiece.EMPTY)));
    }

    for (int rowSize = 2 * boardSize - 1; rowSize >= boardSize; rowSize--) {
      this.cellGrid.add(new ArrayList<>(Collections.nCopies(rowSize, PlayerPiece.EMPTY)));
    }
  }

  /**
   * Continues game based on the given state of the another board and the current player.
   *
   * @param currentBoard board to continue game from
   * @param currentPlayer current player that is up to make a move
   * @throws IllegalArgumentException if the provided player is EMPTY or null, or if the
   *                                  current board is invalid.
   * @throws IllegalStateException  if the game has already started
   */
  public void continueGame(List<List<PlayerPiece>> currentBoard, PlayerPiece currentPlayer)
          throws IllegalArgumentException {
    if (this.gameStarted) {
      throw new IllegalStateException("Game has already started");
    }
    this.validateBoard(currentBoard);
    if (currentPlayer == null || currentPlayer == PlayerPiece.EMPTY) {
      throw new IllegalArgumentException("Player cannot be null or empty");
    }
    this.cellGrid = currentBoard;
    this.consecutivePasses = 0;
    this.currentTurn = currentPlayer;
    this.gameStarted = true;
  }

  private void validateBoard(List<List<PlayerPiece>> currentBoard) throws IllegalArgumentException {
    if (currentBoard == null) {
      throw new IllegalArgumentException("Board cannot be null");
    }
    if (currentBoard.size() % 2 == 0) {
      throw new IllegalArgumentException("Invalid board size");
    }
    int boardSize = (currentBoard.size() + 1) / 2;

    for (int row = 0; row < currentBoard.get(0).size(); row ++) {
      if (currentBoard.get(row).size() != currentBoard.size() - Math.abs(row - boardSize + 1)) {
        throw new IllegalArgumentException("Invalid board size");
      }
      for (int col = 0; col < currentBoard.get(row).size(); col++) {
        if (currentBoard.get(row).get(col) == null) {
          throw new IllegalArgumentException("Cells cannot be null in board");
        }
      }
    }
  }

  /**
   * Starts a game of Reversi with a hex-shaped board. This method adds the initial pieces onto the
   * board and notifies the players that the game has started.
   * The invariant that the currentPlayer is not EMPTY is maintained because currentTurn is
   * initialized to BLACK when startGame() is called. An exception is thrown when any other method
   * is called before startGame().
   *
   * @throws IllegalArgumentException if board size is invalid
   * @throws IllegalStateException if game has already started
   */
  @Override
  public void startGame() throws IllegalArgumentException, IllegalStateException {
    if (this.gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    this.gameStarted = true;
    this.consecutivePasses = 0;

    //Initialize players on grid
    initPlayersOnGrid();

    //Black player moves first
    this.currentTurn = PlayerPiece.BLACK;
    for (ModelFeatures listeners: this.features) {
      listeners.yourTurn();
    }
  }

  @Override
  public List<List<PlayerPiece>> copyBoard() {
    this.verifyGameStarted();

    return new ArrayList<>(cellGrid);
  }

  /**
   * Initializes the board with appropriate pieces in starting locations.
   */
  private void initPlayersOnGrid() {
    //Starting from top row going down
    int center = (this.getBoardHeight() - 1) / 2;

    //Row above middle
    this.cellGrid.get(center - 1).set(center - 1, PlayerPiece.BLACK); //left
    this.cellGrid.get(center - 1).set(center, PlayerPiece.WHITE); //right

    //Middle row
    this.cellGrid.get(center).set(center - 1, PlayerPiece.WHITE); //left
    this.cellGrid.get(center).set(center + 1, PlayerPiece.BLACK); //right

    //Row below middle
    this.cellGrid.get(center + 1).set(center - 1, PlayerPiece.BLACK); //left
    this.cellGrid.get(center + 1).set(center, PlayerPiece.WHITE); //right
  }

  private void verifyGameStarted() throws IllegalStateException {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game hasn't started yet.");
    }
  }

  /**
   * Helper method for ensuring coordinates are in-bounds.
   *
   * @param coord the coordinates containing information of the row and col
   * @throws IllegalArgumentException if either of the values are out-of-bounds.
   */
  private void verifyCoordinates(Coord coord) throws IllegalArgumentException {
    if (coord.row < 0 || coord.row >= this.getBoardHeight()) {
      throw new IllegalArgumentException("Row number out-of-range.");
    }
    if (coord.col < 0 || coord.col >= this.getRowWidth(coord.row)) {
      throw new IllegalArgumentException("Column number out-of-range on given row");
    }
  }

  /**
   * Gives the depth of the next piece of the same color of the player in a certain direction.
   *
   * @param player  The color of the pieces being checked for.
   * @param coord   the coordinates containing information of the row and col
   * @param dir     a number representing the direction with 0 representing directly left
   *                and subsequent numbers rotating clockwise.
   * @return  the depth of the next piece of the same color, or -1 if there is a gap or no piece of
   *          the same color.
   */
  private int samePieceInDirection(PlayerPiece player, Coord coord, int dir) {
    int depth = 1;
    int current_row = coord.row;
    int current_col = coord.col;
    while (depth < this.getBoardHeight()) {
      // This switch statement aids in navigating our coordinate system since adjacency between
      //  cells changes after the halfway point.
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
      PlayerPiece player_at_cell;
      try {
        player_at_cell = this.getPlayerAtCell(Coord.coordAt(current_row, current_col));
      }
      catch (IllegalArgumentException e) {
        return -1;
      }
      if (player_at_cell == PlayerPiece.EMPTY) {
        return -1;
      }
      if (player == player_at_cell) {
        return depth;
      }
      depth++;
    }
    return -1;
  }

  /**
   * Returns whether the given spot is a legal move for the given player. A legal move
   * is defined as a move that must flip at least one opponent's piece.
   *
   * @param player the player that wants to place the piece
   * @param coord Location on the grid to check
   * @return True if the given coord is a legal move for the given player
   */
  public boolean isMoveLegal(PlayerPiece player, Coord coord) {
    if (!this.isCellEmpty(coord)) {
      return false;
    }
    for (int dir = 0; dir < 6; dir++) {
      if (samePieceInDirection(player, coord, dir) > 1) {
        return true;
      }
    }
    return false;
  }

  @Override
  public PlayerPiece getPiece(Player player) {
    if (this.playerMap.containsKey(player)) {
      return this.playerMap.get(player);
    }
    throw new IllegalArgumentException("Invalid player given.");
  }

  /**
   * Helper function for flipping pieces in a direction from the original placement position.
   *
   * @param player the player that placed the piece.
   * @param coord  the coordinates containing information of the row and col
   * @param depth  the number of pieces needed to be flipped in the direction specified
   * @param dir    the direction on the hex grid to flip pieces in
   *               left and incrementing clockwise.
   */
  private void flipPiecesInDirection(PlayerPiece player, Coord coord, int depth, int dir) {
    int current_row = coord.row;
    int current_col = coord.col;
    while (depth-- > 0) {
      // This switch statement aids in navigating our coordinate system since adjacency between
      //  cells changes after the halfway point.
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
   * The invariant that the currentPlayer is not EMPTY is maintained because it can only be set to
   * either BLACK or WHITE in this method.
   *
   * @param coord   The coordinates containing information of the row and col.
   *                The row is the top-down oriented row the 0th row is the 1st row.
   *                The col is the left-right oriented column
   *                where the 0th col is the left-most cell.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   * @throws IllegalStateException if there have already been two consecutive passes
   *                               (the game already ended)
   */
  @Override
  public void makeMove(Coord coord) throws IllegalArgumentException, IllegalStateException {
    this.verifyGameStarted();
    this.verifyCoordinates(coord);

    if (this.consecutivePasses >= 2) {
      throw new IllegalStateException("Game has ended");
    }

    if (!isMoveLegal(this.currentTurn, coord)) {
      throw new IllegalStateException("Move is not allowed");
    }

    this.cellGrid.get(coord.row).set(coord.col, this.currentTurn);

    for (int dir = 0; dir < 6; dir++) {
      int depth = this.samePieceInDirection(this.currentTurn, coord, dir);
      if (depth > 1) {
        this.flipPiecesInDirection(this.currentTurn, coord, depth, dir);
      }
    }

    this.currentTurn = (this.currentTurn == PlayerPiece.BLACK)
            ? PlayerPiece.WHITE : PlayerPiece.BLACK;
    this.consecutivePasses = 0;
    for (ModelFeatures listeners: this.features) {
      listeners.yourTurn();
    }
  }

  /**
   * Gives the current player turn.
   *
   * @return The color representing the current player's turn.
   * @throws IllegalStateException if the game hasn't started
   */
  @Override
  public PlayerPiece getCurrentPlayer() throws IllegalStateException {
    this.verifyGameStarted();
    return this.currentTurn;
  }

  /**
   * Returns the player occupying the cell or if the cell is empty.
   *
   * @param coord   The coordinates containing information of the row and col.
   *                The row is the top-down oriented row the 0th row is the 1st row.
   *                The col is the left-right oriented column
   *                where the 0th col is the left-most cell.
   * @return The player in the cell or if the cell is empty at the given coordinates.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  @Override
  public PlayerPiece getPlayerAtCell(Coord coord)
          throws IllegalArgumentException, IllegalStateException {
    this.verifyGameStarted();
    this.verifyCoordinates(coord);

    return this.cellGrid.get(coord.row).get(coord.col);
  }

  /**
   * Returns whether the cell at the given row and column is empty or not.
   *
   * @param coord   The coordinates containing information of the row and col.
   *                The row is the top-down oriented row the 0th row is the 1st row.
   *                The col is the left-right oriented column
   *                where the 0th col is the left-most cell.
   * @return true if the cell at the given coordinates is empty or false if it is occupied.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  @Override
  public boolean isCellEmpty(Coord coord)
          throws IllegalArgumentException, IllegalStateException {
    this.verifyGameStarted();
    this.verifyCoordinates(coord);

    return getPlayerAtCell(coord) == PlayerPiece.EMPTY;
  }

  /**
   * Signals if the game is over if there are no more moves to make.
   *
   * @return true is game is over, false is not
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {
    return !(canPlayerMove(PlayerPiece.BLACK) || canPlayerMove(PlayerPiece.WHITE))
            || this.consecutivePasses >= 2;
  }

  /**
   * Returns whether the current player has a legal move or not.
   *
   * @param player the player whose move availability is being checked.
   * @return true is player can move, false if player cannot move
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public boolean canPlayerMove(PlayerPiece player) throws IllegalStateException {
    this.verifyGameStarted();
    for (int row = 0; row < this.getBoardHeight(); row++) {
      for (int col = 0; col < this.getRowWidth(row); col++) {
        if (this.isMoveLegal(player, Coord.coordAt(row, col))) {
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
    if (row < 0 || row > this.getBoardHeight()) {
      throw new IllegalArgumentException("Row number out-of-range.");
    }
    return this.cellGrid.get(row).size();
  }

  @Override
  public int getPlayerScore(PlayerPiece player)
          throws IllegalArgumentException, IllegalStateException {
    this.verifyGameStarted();
    if (player == null || player == PlayerPiece.EMPTY) {
      throw new IllegalArgumentException("Invalid player given.");
    }

    int score = 0;
    for (List<PlayerPiece> tiles : this.cellGrid) {
      for (PlayerPiece value : tiles) {
        if (value == player) {
          score += 1;
        }
      }
    }

    return score;
  }

  /**
   * Passes the current players turn to the other player.
   * The invariant that the currentPlayer is not EMPTY is maintained because it can only be set to
   * either BLACK or WHITE in this method.
   *
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalStateException if there have already been two consecutive passes.
   */
  @Override
  public void passTurn() throws IllegalStateException {
    this.verifyGameStarted();
    if (this.consecutivePasses >= 2 || isGameOver()) {
      throw new IllegalStateException("Game is already over");
    }
    this.currentTurn = (this.currentTurn == PlayerPiece.BLACK)
            ? PlayerPiece.WHITE : PlayerPiece.BLACK;
    this.consecutivePasses += 1;
    for (ModelFeatures listeners: this.features) {
      listeners.yourTurn();
    }
  }

  /**
   * Adds a feature listener to the model, which will be notified when the game turn changes.
   *
   * @param features the listener which will be notified once the game turn changes.
   */
  @Override
  public void addFeatures(ModelFeatures features) {
    this.features.add(features);
  }

  /**
   * Adds a player to the game, and assigns the player a piece. This method also ensures that there
   * are a maximum of 2 players per game.
   *
   * @param player  The player which participates in this game.
   * @throws IllegalStateException if the game already has 2 players.
   */

  @Override
  public void addPlayer(Player player) throws IllegalStateException {
    if (this.playerMap.isEmpty()) {
      this.playerMap.put(Objects.requireNonNull(player), PlayerPiece.BLACK);
    }
    else if (this.playerMap.size() == 1) {
      this.playerMap.put(Objects.requireNonNull(player), PlayerPiece.WHITE);
    }
    else {
      throw new IllegalStateException("Game is already full.");
    }
  }
}
