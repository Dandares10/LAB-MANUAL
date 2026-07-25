//EX. A:

//Design and implement a Chess Game using Object-Oriented Programming principles such as inheritance, polymorphism, and encapsulation to model pieces, moves, and game rules.

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// ==========================================
// 1. POSITION VECTOR CLASS
// ==========================================
class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

// ==========================================
// 2. ABSTRACT BASE PIECE CLASS
// ==========================================
abstract class Piece {
    private final String color;
    private final String symbol;
    private boolean hasMoved;

    public Piece(String color, String symbol) {
        this.color = color;
        this.symbol = symbol;
        this.hasMoved = false;
    }

    public String getColor() { return color; }
    public String getSymbol() { return symbol; }
    public boolean hasMoved() { return hasMoved; }
    public void setHasMoved(boolean hasMoved) { this.hasMoved = hasMoved; }

    public abstract List<Position> getValidMoves(Position currentPos, Board board);

    protected List<Position> getLinearMoves(Position currentPos, Board board, int[][] directions) {
        List<Position> validMoves = new ArrayList<>();
        
        for (int[] dir : directions) {
            int r = currentPos.getRow() + dir[0];
            int c = currentPos.getCol() + dir[1];
            
            while (true) {
                Position targetPos = new Position(r, c);
                if (!targetPos.isValid()) break;
                
                Piece targetPiece = board.getPiece(targetPos);
                if (targetPiece == null) {
                    validMoves.add(targetPos);
                } else {
                    if (!targetPiece.getColor().equals(this.color)) {
                        validMoves.add(targetPos);
                    }
                    break; 
                }
                r += dir[0];
                c += dir[1];
            }
        }
        return validMoves;
    }
}

// ==========================================
// 3. CONCRETE PIECE IMPLEMENTATIONS
// ==========================================
class Rook extends Piece {
    public Rook(String color, String symbol) { super(color, symbol); }
    @Override
    public List<Position> getValidMoves(Position currentPos, Board board) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        return getLinearMoves(currentPos, board, directions);
    }
}

class Bishop extends Piece {
    public Bishop(String color, String symbol) { super(color, symbol); }
    @Override
    public List<Position> getValidMoves(Position currentPos, Board board) {
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        return getLinearMoves(currentPos, board, directions);
    }
}

class Queen extends Piece {
    public Queen(String color, String symbol) { super(color, symbol); }
    @Override
    public List<Position> getValidMoves(Position currentPos, Board board) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        return getLinearMoves(currentPos, board, directions);
    }
}

class Knight extends Piece {
    public Knight(String color, String symbol) { super(color, symbol); }
    @Override
    public List<Position> getValidMoves(Position currentPos, Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] deltas = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        
        for (int[] d : deltas) {
            Position targetPos = new Position(currentPos.getRow() + d[0], currentPos.getCol() + d[1]);
            if (targetPos.isValid()) {
                Piece targetPiece = board.getPiece(targetPos);
                if (targetPiece == null || !targetPiece.getColor().equals(this.getColor())) {
                    validMoves.add(targetPos);
                }
            }
        }
        return validMoves;
    }
}

class King extends Piece {
    public King(String color, String symbol) { super(color, symbol); }
    @Override
    public List<Position> getValidMoves(Position currentPos, Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] deltas = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        
        for (int[] d : deltas) {
            Position targetPos = new Position(currentPos.getRow() + d[0], currentPos.getCol() + d[1]);
            if (targetPos.isValid()) {
                Piece targetPiece = board.getPiece(targetPos);
                if (targetPiece == null || !targetPiece.getColor().equals(this.getColor())) {
                    validMoves.add(targetPos);
                }
            }
        }

        // --- Castling Logic ---
        if (!this.hasMoved()) {
            int row = currentPos.getRow();
            
            // King-side (Column 7)
            Piece rookK = board.getPiece(new Position(row, 7));
            if (rookK instanceof Rook && !rookK.hasMoved()) {
                if (board.getPiece(new Position(row, 5)) == null && board.getPiece(new Position(row, 6)) == null) {
                    validMoves.add(new Position(row, 6));
                }
            }
            
            // Queen-side (Column 0)
            Piece rookQ = board.getPiece(new Position(row, 0));
            if (rookQ instanceof Rook && !rookQ.hasMoved()) {
                if (board.getPiece(new Position(row, 1)) == null && 
                    board.getPiece(new Position(row, 2)) == null && 
                    board.getPiece(new Position(row, 3)) == null) {
                    validMoves.add(new Position(row, 2));
                }
            }
        }
        return validMoves;
    }
}

class Pawn extends Piece {
    public Pawn(String color, String symbol) { super(color, symbol); }
    @Override
    public List<Position> getValidMoves(Position currentPos, Board board) {
        List<Position> validMoves = new ArrayList<>();
        int direction = this.getColor().equals("White") ? -1 : 1;

        // One step forward
        Position oneStep = new Position(currentPos.getRow() + direction, currentPos.getCol());
        if (oneStep.isValid() && board.getPiece(oneStep) == null) {
            validMoves.add(oneStep);
            
            // Two steps forward
            int startRow = this.getColor().equals("White") ? 6 : 1;
            Position twoStep = new Position(currentPos.getRow() + 2 * direction, currentPos.getCol());
            if (currentPos.getRow() == startRow && board.getPiece(twoStep) == null) {
                validMoves.add(twoStep);
            }
        }

        // Standard captures & En Passant
        for (int dc : new int[]{-1, 1}) {
            Position attackPos = new Position(currentPos.getRow() + direction, currentPos.getCol() + dc);
            if (attackPos.isValid()) {
                Piece targetPiece = board.getPiece(attackPos);
                if (targetPiece != null && !targetPiece.getColor().equals(this.getColor())) {
                    validMoves.add(attackPos);
                } else if (attackPos.equals(board.getEnPassantTarget())) {
                    validMoves.add(attackPos);
                }
            }
        }
        return validMoves;
    }
}

// ==========================================
// 4. BOARD COMPONENT LAYER
// ==========================================
class Board {
    private final Piece[][] grid;
    private Position enPassantTarget;

    public Board() {
        this.grid = new Piece[8][8];
        this.enPassantTarget = null;
        setupPieces();
    }

    public Piece getPiece(Position pos) { return grid[pos.getRow()][pos.getCol()]; }
    public void setPiece(Position pos, Piece piece) { grid[pos.getRow()][pos.getCol()] = piece; }
    public Position getEnPassantTarget() { return enPassantTarget; }
    public void setEnPassantTarget(Position pos) { this.enPassantTarget = pos; }

    public void movePiece(Position start, Position end) {
        Piece piece = getPiece(start);
        setPiece(end, piece);
        setPiece(start, null);
        if (piece != null) {
            piece.setHasMoved(true);
        }
    }

    private void setupPieces() {
        String[] bBack = {"♜", "♞", "♝", "♛", "♚", "♝", "♞", "♜"};
        String[] wBack = {"♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖"};

        // Setup Black back row
        grid[0][0] = new Rook("Black", bBack[0]);
        grid[0][1] = new Knight("Black", bBack[1]);
        grid[0][2] = new Bishop("Black", bBack[2]);
        grid[0][3] = new Queen("Black", bBack[3]);
        grid[0][4] = new King("Black", bBack[4]);
        grid[0][5] = new Bishop("Black", bBack[5]);
        grid[0][6] = new Knight("Black", bBack[6]);
        grid[0][7] = new Rook("Black", bBack[7]);
        for (int i = 0; i < 8; i++) grid[1][i] = new Pawn("Black", "♟");

        // Setup White back row
        for (int i = 0; i < 8; i++) grid[6][i] = new Pawn("White", "♙");
        grid[7][0] = new Rook("White", wBack[0]);
        grid[7][1] = new Knight("White", wBack[1]);
        grid[7][2] = new Bishop("White", wBack[2]);
        grid[7][3] = new Queen("White", wBack[3]);
        grid[7][4] = new King("White", wBack[4]);
        grid[7][5] = new Bishop("White", wBack[5]);
        grid[7][6] = new Knight("White", wBack[6]);
        grid[7][7] = new Rook("White", wBack[7]);
       }

    public void display() {
        System.out.println("\n  0 1 2 3 4 5 6 7");
        for (int r = 0; r < 8; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < 8; c++) {
                Piece piece = grid[r][c];
                System.out.print((piece != null ? piece.getSymbol() : ".") + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

// ==========================================
// 5. CHESS ENGINE CONTROL SYSTEM (MAIN ENTRY)
// ==========================================
public class ChessGame {
    private final Board board;
    private String currentTurn;

    public ChessGame() {
        this.board = new Board();
        this.currentTurn = "White";
    }

    public boolean playTurn(int startR, int startC, int endR, int endC) {
        Position start = new Position(startR, startC);
        Position end = new Position(endR, endC);

        if (!start.isValid() || !end.isValid()) {
            System.out.println("Coordinates out of bounds!");
            return false;
        }

        Piece piece = board.getPiece(start);
        if (piece == null || !piece.getColor().equals(currentTurn)) {
            System.out.println("Select a piece belonging to: " + currentTurn);
            return false;
        }

        List<Position> validMoves = piece.getValidMoves(start, board);
        if (!validMoves.contains(end)) {
            System.out.println("Invalid move pattern.");
            return false;
        }

        // 1. En Passant Capture Cleanup
        if (piece instanceof Pawn && end.equals(board.getEnPassantTarget())) {
            int direction = piece.getColor().equals("White") ? -1 : 1;
            Position capturedPawnPos = new Position(end.getRow() - direction, end.getCol());
            board.setPiece(capturedPawnPos, null);
        }

        // 2. Refresh En Passant Targets
        if (piece instanceof Pawn && Math.abs(end.getRow() - start.getRow()) == 2) {
            int direction = piece.getColor().equals("White") ? -1 : 1;
            board.setEnPassantTarget(
                new Position(start.getRow() + direction, start.getCol())
            );
        } else {
            board.setEnPassantTarget(null);
        }

        // 3. Move Rooks During Valid Castles
        if (piece instanceof King && Math.abs(end.getCol() - start.getCol()) == 2) {
            if (end.getCol() == 6) {
                board.movePiece(
                    new Position(start.getRow(), 7),
                    new Position(start.getRow(), 5)
                );
            } else if (end.getCol() == 2) {
                board.movePiece(
                    new Position(start.getRow(), 0),
                    new Position(start.getRow(), 3)
                );
            }
        }

        // Execute core piece move
        board.movePiece(start, end);

        // 4. Handle Promotions
        if (piece instanceof Pawn && (end.getRow() == 0 || end.getRow() == 7)) {
            String promoSymbol = piece.getColor().equals("White") ? "♕" : "♛";
            board.setPiece(end, new Queen(piece.getColor(), promoSymbol));
            System.out.println("Pawn promoted to Queen!");
        }

        currentTurn = currentTurn.equals("White") ? "Black" : "White";
        return true;
    }

    public void startLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("OOP Chess Engine Java Console Simulation!");

        while (true) {
            board.display();
            System.out.println("Active Turn: " + currentTurn);
            System.out.print("Enter (start_row start_col end_row end_col) or 'q' to quit: ");

            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("q")) {
                break;
            }

            try {
                String[] tokens = input.trim().split("\\s+");

                int sr = Integer.parseInt(tokens[0]);
                int sc = Integer.parseInt(tokens[1]);
                int er = Integer.parseInt(tokens[2]);
                int ec = Integer.parseInt(tokens[3]);

                playTurn(sr, sc, er, ec);

            } catch (Exception e) {
                System.out.println("Parsing error. Use 4 space-separated integers.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.startLoop();
    }
}
