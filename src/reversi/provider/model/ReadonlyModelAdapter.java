package reversi.provider.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import reversi.controller.HumanPlayer;
import reversi.model.Coord;
import reversi.model.ReadonlyReversi;
import reversi.model.PlayerPiece;
import reversi.provider.controller.ModelStatusListener;
import reversi.controller.Player;

public class ReadonlyModelAdapter implements ReadonlyReversiModel {

  private final ReadonlyReversi model;

  public ReadonlyModelAdapter(ReadonlyReversi reversi) {
    this.model = reversi;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public ProviderPlayer getPlayer1() {
    for (Map.Entry<Player, PlayerPiece> entry : model.getPlayerMap().entrySet()) {
      if (entry.getValue().equals(PlayerPiece.BLACK)) {
        if (entry.getKey() instanceof HumanPlayer) {
          return (HumanProviderPlayerAdapter)entry.getKey();
        } else {
          return (MachineProviderPlayerAdapter)entry.getKey();
        }
      }
    }
    throw new IllegalStateException("no players added yet.");
  }

  @Override
  public ProviderPlayer getPlayer2() {
    for (Map.Entry<Player, PlayerPiece> entry : model.getPlayerMap().entrySet()) {
      if (entry.getValue().equals(PlayerPiece.WHITE)) {
        if (entry.getKey() instanceof HumanPlayer) {
          return (HumanProviderPlayerAdapter)entry.getKey();
        } else {
          return (MachineProviderPlayerAdapter)entry.getKey();
        }
      }
    }
    throw new IllegalStateException("no players added yet.");
  }

  @Override
  public List<List<Disc>> getBoard() {
    List<List<PlayerPiece>> pieceBoard = model.copyBoard();
    List<List<Disc>> discBoard = new ArrayList<>();
    for (int row = 0; row < pieceBoard.size(); row++) {
      List<Disc> discRow = new ArrayList<>();
      for (int col = 0; col < pieceBoard.get(row).size(); col++) {
        discRow.add(new Disc(Coord.coordAt(row, col), pieceBoard.get(row).get(col),
                this.getBoardSize()));
      }
      discBoard.add(discRow);
    }
    return discBoard;
  }

  @Override
  public DiscType getCurrentTurn() {
    return DiscConversion.toDiscType(model.getCurrentPlayer());
  }

  @Override
  public ProviderPlayer getWinner() {
    int scoreBlack = model.getPlayerScore(PlayerPiece.BLACK);
    int scoreWhite = model.getPlayerScore(PlayerPiece.WHITE);
    if (scoreWhite == scoreBlack) {
      throw new IllegalStateException("tie game");
    }
    return (scoreBlack > scoreWhite) ? this.getPlayer1() : this.getPlayer2();
  }

  @Override
  public int getPlayer1Score() {
    return model.getPlayerScore(PlayerPiece.BLACK);
  }

  @Override
  public int getPlayer2Score() {
    return model.getPlayerScore(PlayerPiece.WHITE);
  }

  @Override
  public boolean validMove(Disc clicked) {
    return model.isMoveLegal(clicked.getPiece(), clicked.getCoord());
  }

  @Override
  public int getBoardSize() {
    return model.getBoardHeight();
  }

  @Override
  public Disc findCLicked() {
    return null;
  }

  @Override
  public List<Disc> allMovesLeft(DiscType player) {
    List<Disc> ans = new ArrayList<>();
    List<List<PlayerPiece>> board = model.copyBoard();
    for (int row = 0; row < board.size(); row++) {
      for (int col = 0; col < board.get(row).size(); col++) {
        PlayerPiece piece = DiscConversion.toPlayerPiece(player);
        Coord cell = Coord.coordAt(row, col);
        if (model.isMoveLegal(piece, cell)) {
          ans.add(new Disc(cell, piece, this.getBoardSize()));
        }
      }
    }
    return ans;
  }

  @Override
  public int getPlayerScore(DiscType player) {
    return 0;
  }

  @Override
  public List<Disc> discsToFlip(List<Disc> discs, Disc clicked) {
    return null;
  }

  @Override
  public List<Disc> discsToFlipAll(Disc clicked) {
    return null;
  }

  @Override
  public DiscType playerToAddType() {
    return null;
  }

  @Override
  public List<ModelStatusListener> getListeners() {
    return null;
  }
}
