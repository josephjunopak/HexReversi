package reversi.model;

import java.util.List;

/**
 * A model mock for hex reversi that keeps a transcript of moves that are deemed valid or invalid
 * by the real HexReversi model. It also forces the move (6, 3) to be the only valid move at all
 * times. This mock is primarily used to test the CaptureMax strategy, to ensure that it checks all
 * possible cells.
 */
public class MockReversi implements ReadonlyReversi {
  private final HexReversi realModel;
  private StringBuilder valid_moves;
  private StringBuilder invalid_moves;

  /**
   * The constructor for the mock which takes in a real model of reversi to delegate method calls
   * to. It also instantiates the string-builders for valid and invalid moves.
   * @param model
   */
  public MockReversi(HexReversi model) {
    this.realModel = model;
    this.valid_moves = new StringBuilder();
    this.invalid_moves = new StringBuilder();
  }

  /**
   * Returns whether the given spot is a legal move for the given player. A legal move
   * is defined as a move that must flip at least one opponent's piece.
   *
   * @param player the player that wants to place the piece
   * @param coord  Location on the grid to check
   * @return True if the given coord is a legal move for the given player
   */
  @Override
  public boolean isMoveLegal(Player player, Coord coord) {
    if (this.realModel.isMoveLegal(player, coord)) {
      valid_moves.append(coord.toString());
    }
    else {
      invalid_moves.append(coord.toString());
    }
    return (coord.equals(Coord.coordAt(6, 3)));
  }


  /**
   * Gives the current player turn.
   *
   * @return The color representing the current player's turn.
   * @throws IllegalStateException if the game hasn't started
   */
  @Override
  public Player getCurrentPlayer() throws IllegalStateException {
    return realModel.getCurrentPlayer();
  }

  /**
   * Returns the player occupying the cell or if the cell is empty.
   *
   * @param coord The coordinates containing information of the row and col.
   * @return The player in the cell or if the cell is empty at the given coordinates.
   * @throws IllegalStateException    if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  @Override
  public Player getPlayerAtCell(Coord coord) throws IllegalArgumentException, IllegalStateException {
    return realModel.getPlayerAtCell(coord);
  }

  /**
   * Returns whether the cell at the given row and column is empty or not.
   *
   * @param coord The coordinates containing information of the row and col.
   * @return true if the cell at the given coordinates is empty or false if it is occupied.
   * @throws IllegalStateException    if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  @Override
  public boolean isCellEmpty(Coord coord) throws IllegalArgumentException, IllegalStateException {
    return this.realModel.isCellEmpty(coord);
  }

  /**
   * Signals if the game is over if there are no more moves to make.
   *
   * @return true is game is over, false is not
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {
    return this.realModel.isGameOver();
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
    return this.realModel.canPlayerMove(player);
  }

  /**
   * \
   * Gives the number of rows in the board.
   *
   * @return the number of rows in the cell grid.
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public int getBoardHeight() throws IllegalStateException {
    return this.realModel.getBoardHeight();
  }

  /**
   * Returns the number of cells in the row specified.
   *
   * @param row
   * @return the number of cells in row specified
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    return this.realModel.getRowWidth(row);
  }

  /**
   * Returns the score of the given player.
   *
   * @param player The player whose score is being requested.
   * @return The number of pieces the given player has on the board.
   * @throws IllegalArgumentException If the given player is empty or null.
   * @throws IllegalStateException    If the game hasn't started.
   */
  @Override
  public int getPlayerScore(Player player) throws IllegalArgumentException, IllegalStateException {
    return this.realModel.getPlayerScore(player);
  }

  /**
   * Returns a copy of the current game board of Reversi.
   */
  @Override
  public List<List<Player>> copyBoard() {
    return this.realModel.copyBoard();
  }

  public String getValidTranscript() {
    return this.valid_moves.toString();
  }

  public String getInvalidTranscript() {
    return this.invalid_moves.toString();
  }
}
