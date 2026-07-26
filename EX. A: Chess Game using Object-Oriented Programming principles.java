//EX. A:

//Design and implement a Chess Game using Object-Oriented Programming principles such as inheritance, polymorphism, and encapsulation to model pieces, moves, and game rules.

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// ==========================================
// 1. POSITION VECTOR CLASS (ALGEBRAIC NOTATION)
// ==========================================
class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Create position from algebraic notation (e.g., "e2")
    public static Position fromAlgebraic(String notation) throws IllegalArgumentException {
        if (notation == null || notation.length() != 2) {
            throw new IllegalArgumentException("Invalid notation: " + notation);
        }
        char colChar = notation.charAt(0);
        char rowChar = notation.charAt(1);
        
        if (colChar < 'a' || colChar > 'h' || rowChar < '1' || rowChar > '8') {
            throw new IllegalArgumentException("Invalid notation: " + notation);
        }
        
        int col = colChar - 'a';
        int row = 8 - (rowChar - '0');
        return new Position(row, col);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // Convert position to algebraic notation
    public String toAlgebraic() {
        char colChar = (char) ('a' + col);
        char rowChar = (char) ('8' - row);
        return "" + colChar + rowChar;
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
        return toAlgebraic();
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
        // Setup Black back row (row 0)
        grid[0][0] = new Rook("Black", "r");
        grid[0][1] = new Knight("Black", "n");
        grid[0][2] = new Bishop("Black", "b");
        grid[0][3] = new Queen("Black", "q");
        grid[0][4] = new King("Black", "k");
        grid[0][5] = new Bishop("Black", "b");
        grid[0][6] = new Knight("Black", "n");
        grid[0][7] = new Rook("Black", "r");
        for (int i = 0; i < 8; i++) grid[1][i] = new Pawn("Black", "p");

        // Setup White back row (row 7)
        for (int i = 0; i < 8; i++) grid[6][i] = new Pawn("White", "P");
        grid[7][0] = new Rook("White", "R");
        grid[7][1] = new Knight("White", "N");
        grid[7][2] = new Bishop("White", "B");
        grid[7][3] = new Queen("White", "Q");
        grid[7][4] = new King("White", "K");
        grid[7][5] = new Bishop("White", "B");
        grid[7][6] = new Knight("White", "N");
        grid[7][7] = new Rook("White", "R");
    }

    public void display() {
        System.out.println();
        for (int r = 0; r < 8; r++) {
            System.out.print((8 - r) + " ");
            for (int c = 0; c < 8; c++) {
                Piece piece = grid[r][c];
                System.out.print((piece != null ? piece.getSymbol() : ".") + " ");
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
        System.out.println();
    }

    // Find the King of a given color
    public Position findKing(String color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p instanceof King && p.getColor().equals(color)) {
                    return new Position(r, c);
                }
            }
        }
        return null;
    }

    // Check if a given position is under attack by opponent
    public boolean isUnderAttack(Position pos, String byColor) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.getColor().equals(byColor)) {
                    List<Position> attackMoves = p.getValidMoves(new Position(r, c), this);
                    if (attackMoves.contains(pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Check if a color is in check
    public boolean isInCheck(String color) {
        Position kingPos = findKing(color);
        if (kingPos == null) return false;
        
        String opponentColor = color.equals("White") ? "Black" : "White";
        return isUnderAttack(kingPos, opponentColor);
    }

    // Check if a color is in checkmate
    public boolean isInCheckmate(String color) {
        if (!isInCheck(color)) return false;
        
        // Try all possible moves for the color
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.getColor().equals(color)) {
                    Position currentPos = new Position(r, c);
                    List<Position> validMoves = p.getValidMoves(currentPos, this);
                    
                    for (Position targetPos : validMoves) {
                        // Simulate move
                        Piece capturedPiece = getPiece(targetPos);
                        movePiece(currentPos, targetPos);
                        
                        // Check if still in check after move
                        boolean stillInCheck = isInCheck(color);
                        
                        // Undo move
                        movePiece(targetPos, currentPos);
                        if (capturedPiece != null) {
                            setPiece(targetPos, capturedPiece);
                        }
                        
                        if (!stillInCheck) {
                            return false; // Found a legal move
                        }
                    }
                }
            }
        }
        return true; // No legal moves and in check = checkmate
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

    public boolean playTurn(String startNotation, String endNotation) {
        try {
            Position start = Position.fromAlgebraic(startNotation);
            Position end = Position.fromAlgebraic(endNotation);

            if (!start.isValid() || !end.isValid()) {
                System.out.println("Error: Coordinates out of bounds!");
                return false;
            }

            Piece piece = board.getPiece(start);
            if (piece == null) {
                System.out.println("Error: No piece at " + start + ".");
                return false;
            }

            if (!piece.getColor().equals(currentTurn)) {
                System.out.println("Error: You must move a " + currentTurn + " piece.");
                return false;
            }

            List<Position> validMoves = piece.getValidMoves(start, board);
            if (!validMoves.contains(end)) {
                System.out.println("Error: " + piece.getSymbol() + " cannot move to " + end + ".");
                return false;
            }

            // 1. En Passant Capture Cleanup
            if (piece instanceof Pawn && end.equals(board.getEnPassantTarget())) {
                int direction = piece.getColor().equals("White") ? -1 : 1;
                Position capturedPawnPos = new Position(end.getRow() - direction, end.getCol());
                board.setPiece(capturedPawnPos, null);
                System.out.println("En passant capture!");
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
                    System.out.println("Kingside castling!");
                } else if (end.getCol() == 2) {
                    board.movePiece(
                        new Position(start.getRow(), 0),
                        new Position(start.getRow(), 3)
                    );
                    System.out.println("Queenside castling!");
                }
            }

            // Execute core piece move
            board.movePiece(start, end);
            System.out.println(piece.getSymbol() + " moved from " + start + " to " + end + ".");

            // 4. Handle Promotions
            if (piece instanceof Pawn && (end.getRow() == 0 || end.getRow() == 7)) {
                char promoSymbol = piece.getColor().equals("White") ? 'Q' : 'q';
                board.setPiece(end, new Queen(piece.getColor(), String.valueOf(promoSymbol)));
                System.out.println("Pawn promoted to Queen!");
            }

            // Switch turns
            currentTurn = currentTurn.equals("White") ? "Black" : "White";
            
            // Check for check/checkmate
            if (board.isInCheck(currentTurn)) {
                System.out.println("\nCheck! " + currentTurn + "'s King is in check!");
                if (board.isInCheckmate(currentTurn)) {
                    System.out.println("Checkmate! " + (currentTurn.equals("White") ? "Black" : "White") + " wins the game!");
                    return false; // End game
                }
            }
            
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void startLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== Chess Game (Console Version) ==\n");
        System.out.println("Initial Board Setup:");
        board.display();
        System.out.println(currentTurn + "'s turn.\n");

        boolean gameActive = true;
        while (gameActive) {
            System.out.print("Enter move (e.g., e2 e4): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("q")) {
                System.out.println("Game ended.");
                break;
            }

            String[] tokens = input.split("\\s+");
            if (tokens.length != 2) {
                System.out.println("Error: Enter exactly 2 positions (e.g., e2 e4).\n");
                continue;
            }

            boolean moveSuccessful = playTurn(tokens[0], tokens[1]);
            board.display();
            
            if (moveSuccessful) {
                System.out.println(currentTurn + "'s turn.\n");
            } else {
                // Check for checkmate - game ended
                if (board.isInCheckmate(currentTurn)) {
                    gameActive = false;
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.startLoop();
    }
}
