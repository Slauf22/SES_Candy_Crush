package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.CheckNeighboursInGrid;
import be.kuleuven.candycrushjavafxproject.Candies.*;
import be.kuleuven.candycrushjavafxproject.GenericBoard.Board;
import javafx.geometry.Pos;
import javafx.scene.Node;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Model {

    ////////////////////
    //Member variables//
    ////////////////////

    private int userScore = 0;
    private String PlayerName;
    private final BoardSize boardSize;
    private final Board<Candy> candyBoard;
    // Voor maximizeScore
    private final HashMap<Integer, String> allSolutions = new HashMap<>();

    ///////////////
    //Constructor//
    ///////////////

    public Model(BoardSize boardSize, Board<Candy> candyBoard) {
        this.boardSize = boardSize;
        this.candyBoard = candyBoard;
    }

    ///////////////////////
    //Getters and Setters//
    ///////////////////////

    public int getWidth() {
        return boardSize.cols();
    }

    public int getHeight() {
        return boardSize.rows();
    }

    public int getUserScore() {
        return userScore;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    ////////////////////
    //Member Functions//
    ////////////////////


    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public Candy GenerateRandomCandy() {
        Random random = new Random();
        int randomNumber = random.nextInt(40);

        //Minder kans om een speciale snoep te genereren dan een normale
        if (randomNumber == 39) {
            return new rowDeleteCandy(4);
        }
        if (randomNumber == 38) {
            return new extraMoveCandy(5);
        }
        if (randomNumber == 37) {
            return new doublePointsCandy(6);
        }
        if (randomNumber == 36) {
            return new borderDeleteCandy(7);
        }

        return new normalCandy(random.nextInt(4));
    }

    public Position RxCToPosition(String gridPosition) {
        int row = Integer.parseInt(gridPosition.substring(0, 1));
        int col = Integer.parseInt(gridPosition.substring(2, 3));

        return new Position(row, col, boardSize);
    }

    public void IncreaseScore(int value) {
        userScore += value;
    }

    public Iterable<Position> getSameNeighbourPositions(Position position) {

        //Find the row of the index
        int indexRow = position.row();

        // Find the column of the index
        int indexCol = position.col();

        //Value (color) of element at index
        int valueIndex = candyBoard.getCellAt(position).getColor();

        //Array for saving neighbour positions
        ArrayList<Position> neighbours = new ArrayList<>();

        int rowCounter = 1;
        int colCounter = 1;

        // Loop over grid
        for (int i = 0; i < boardSize.rows() * boardSize.cols(); i++) {
            // We are in a neighbouring column, left or right. Can also be same column if element is one row above or below
            if (colCounter == indexCol - 1 || colCounter == indexCol + 1 || colCounter == indexCol) {
                // We are in a neighbouring row, above or below. Can also be same row if element is one col left or right
                if (rowCounter == indexRow - 1 || rowCounter == indexRow + 1 || rowCounter == indexRow) {
                    //If neighbour has same value, add index to arraylist. Also check if the loop isn't at the indexToCheck.
                    if ((candyBoard.getCellAt(Position.fromIndex(i, boardSize)).getColor() == valueIndex) && !(rowCounter == indexRow && colCounter == indexCol)) {
                        neighbours.add(RxCToPosition(Integer.toString(rowCounter) + "x" + Integer.toString(colCounter)));
                    }
                }
            }

            //Update row and col of element in grid
            if (colCounter == boardSize.cols()) {
                colCounter = 1;
                rowCounter++;
            } else {
                colCounter++;
            }
        }

        return neighbours;
    }

    boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions) {
        List<Position> positionList = positions.toList();

        if (positionList.size() < 2) {
            return false;
        }

        long nMatches = positionList.stream()
                .limit(2)
                .filter(position -> candyBoard.getCellAt(position).getColor() == candy.getColor())
                .count();

        return nMatches >= 2;
    }

    List<Position> longestMatchToRight(Position pos) {
        Stream<Position> rightPositions = pos.walkRight();

        Candy currentCandy = candyBoard.getCellAt(pos);
        List<Position> lst = rightPositions
                .takeWhile(position -> candyBoard.getCellAt(position).getColor() == currentCandy.getColor())
                .toList();

        return lst;
    }

    List<Position> longestMatchDown(Position pos) {
        Stream<Position> rightPositions = pos.walkDown();

        Candy currentCandy = candyBoard.getCellAt(pos);

        List<Position> lst = rightPositions
                .takeWhile(position -> candyBoard.getCellAt(position).getColor() == currentCandy.getColor())
                .toList();

        return lst;
    }

    Stream<Position> horizontalStartingPositions() {
        List<Position> positionList = new ArrayList<>();
        Position currentPosition;
        Candy currentCandy;

        for (int i = 0; i < boardSize.rows() * boardSize.cols(); i++) {
            currentPosition = Position.fromIndex(i, boardSize);
            List<Position> positionsLeft = currentPosition.walkLeft().toList();

            // Only enters when candy is on first column
            if (positionsLeft.size() < 2) {
                positionList.add(currentPosition);
                continue;
            }

            currentCandy = candyBoard.getCellAt(currentPosition);
            Stream<Position> positionsStream = positionsLeft.stream();

            if (!firstTwoHaveCandy(currentCandy, positionsStream)) {
                positionList.add(currentPosition);
            }
        }

        return positionList.stream();
    }

    Stream<Position> verticalStartingPositions() {
        List<Position> positionList = new ArrayList<>();
        Position currentPosition;
        Candy currentCandy;

        for (int i = 0; i < boardSize.rows() * boardSize.cols(); i++) {
            currentPosition = Position.fromIndex(i, boardSize);
            List<Position> positionsUp = currentPosition.walkUp().toList();

            // No combinations possible
            if (positionsUp.size() < 2) {
                positionList.add(currentPosition);
                continue;
            }

            currentCandy = candyBoard.getCellAt(currentPosition);
            Stream<Position> positionsStream = positionsUp.stream();

            if (!firstTwoHaveCandy(currentCandy, positionsStream)) {
                positionList.add(currentPosition);
            }
        }

        return positionList.stream();
    }

    Set<List<Position>> findAllMatches() {
        Stream<Position> verticalStream = verticalStartingPositions();
        Stream<Position> horizontalStream = horizontalStartingPositions();

        Set<List<Position>> matches = new HashSet<>();

        matches.addAll(verticalStream
                .filter(position -> candyBoard.getCellAt(position).getColor() != 99)
                .map(position -> longestMatchDown(position))
                .filter(positions -> positions.size() >= 3)
                .collect(Collectors.toSet()));

        matches.addAll(horizontalStream
                .filter(position -> candyBoard.getCellAt(position).getColor() != 99)
                .map(position -> longestMatchToRight(position))
                .filter(positions -> positions.size() >= 3)
                .collect(Collectors.toSet()));

        return matches;
    }

    void clearMatch(List<Position> match) {
        if (match.isEmpty()) return;

        // Increase score
        userScore++;

        // normal cnady color 99 is clear candy
        candyBoard.replaceCellAt(match.get(0), new normalCandy(99));
        clearMatch(match.subList(1, match.size()));
    }

    void fallDownTo(Position position) {
        // Passed position msut be empty candy
        if (candyBoard.getCellAt(position).getColor() != 99) {
            return;
        }

        if (position.row() == 1) {
            return;
        }

        Position p = new Position(position.row() - 1, position.col(), boardSize);

        while (p.row() >= 1) {
            //If not empty candy
            if (candyBoard.getCellAt(p).getColor() != 99) {
                candyBoard.replaceCellAt(position, candyBoard.getCellAt(p));
                candyBoard.replaceCellAt(p, new normalCandy(99));

                fallDownTo(new Position(position.row() - 1, position.col(), boardSize));
                break;
            }

            if (p.row() > 1) {
                p = new Position(p.row() - 1, p.col(), boardSize);
            } else {
                break;
            }
        }
    }

    boolean updateBoard() {
        Set<List<Position>> matches = findAllMatches().stream()
                .filter(match -> candyBoard.getCellAt(match.getFirst()).getColor() != 99)
                .collect(Collectors.toSet());

        if (matches.isEmpty()) {
            return false;
        }

        Iterator<List<Position>> matchesIterator = matches.iterator();

        // Eerst alles clearen dan alles laten vallen
        while (matchesIterator.hasNext()) {
            List<Position> match = matchesIterator.next();
            clearMatch(match);
        }
        matchesIterator = matches.iterator();
        while (matchesIterator.hasNext()) {
            List<Position> match = matchesIterator.next();
            Iterator<Position> positionIterator = match.iterator();
            while (positionIterator.hasNext()) {
                fallDownTo(positionIterator.next());
            }
        }

        updateBoard();

        return true;
    }

    Position nodeToPosition(Node nodeOfCandy) {
        String idRxC = nodeOfCandy.getId().substring(3);

        return new Position(((int) idRxC.charAt(0)) - 48, ((int) idRxC.charAt(2)) - 48, boardSize);
    }

    public void maximizeScore() {
        // Kopieer het bord in dit object en de score. Alle andere functies werken hierop
        Board<Candy> boardBackup = new Board<>(boardSize);
        candyBoard.copyTo(boardBackup);
        int score = userScore;

        findSolution("");

        // Na de oplossing zet de oorspronkelijke bord en score terug hoe ze waren.
        boardBackup.copyTo(candyBoard);
        userScore = score;

        // Print instructie voor hoogste score
        int maxKey = allSolutions.keySet().stream().max(Comparator.naturalOrder()).orElseThrow();

        System.out.println("Instruction for achieving the highest score with this board. Rows and columns start from 1 and go up to their size.");
        System.out.println("Format is ROWxCOLUMN followed by where to move that candy.");
        System.out.println("Score " + maxKey + ": " + allSolutions.get(maxKey));
    }

    public boolean findSolution(String currentInstruction) {
        // Base case
        if (!findPotentialCombinations()) {
            allSolutions.put(userScore,currentInstruction);
            return true;
        }

        // Iterate through each cell on the board
        for (int r = 1; r <= boardSize.rows(); r++) {
            for (int c = 1; c <= boardSize.cols(); c++) {

                // Swap with the candy to the left
                if (c > 1 && candyBoard.getCellAt(new Position(r,c - 1,boardSize)).getColor() != 99 && candyBoard.getCellAt(new Position(r,c,boardSize)).getColor() != 99) {
                    Board<Candy> backtrackBoard = new Board<>(boardSize);
                    candyBoard.copyTo(backtrackBoard);
                    int scoreBackup = userScore;

                    candyBoard.swapTwoPositions(new Position(r, c, boardSize), new Position(r, c - 1, boardSize));

                    // Ga enkel in recursie als er een match is ontstaan
                    if (!findAllMatches().isEmpty()){
                        updateBoard();

                        findSolution(currentInstruction + r + "x" + c + " Left | "); // Indicate the move direction in the instruction

                        backtrackBoard.copyTo(candyBoard); // Backtrack
                        userScore = scoreBackup;
                    }
                    else{
                        backtrackBoard.copyTo(candyBoard); // Backtrack
                        userScore = scoreBackup;
                    }
                }

                // Swap with the candy above
                if (r > 1 && candyBoard.getCellAt(new Position(r - 1,c,boardSize)).getColor() != 99 && candyBoard.getCellAt(new Position(r,c,boardSize)).getColor() != 99) {
                    Board<Candy> backtrackBoard = new Board<>(boardSize);
                    candyBoard.copyTo(backtrackBoard);
                    int scoreBackup = userScore;

                    candyBoard.swapTwoPositions(new Position(r, c, boardSize), new Position(r - 1, c, boardSize));

                    // Ga enkel in recursie als er een match is ontstaan
                    if (!findAllMatches().isEmpty()){
                        updateBoard();

                        findSolution(currentInstruction + r + "x" + c + " Up | "); // Indicate the move direction in the instruction

                        backtrackBoard.copyTo(candyBoard); // Backtrack
                        userScore = scoreBackup;
                    }
                    else{
                        backtrackBoard.copyTo(candyBoard); // Backtrack
                        userScore = scoreBackup;
                    }
                }
            }
        }
        return false; // Indicate no solution found from this state
    }

    // Returns true is there is at least 1 combination when a candy is moved correctly. Used for base case of maximize function
    public boolean findPotentialCombinations() {
        Board<Candy> backup = new Board<>(boardSize);
        candyBoard.copyTo(backup);

        // Iterate through each cell on the board
        for (int r = 1; r <= boardSize.rows(); r++) {
            for (int c = 1; c <= boardSize.cols(); c++) {
                // Swap with the candy to the right
                if (c < boardSize.cols() && candyBoard.getCellAt(new Position(r,c + 1,boardSize)).getColor() != 99) {
                    candyBoard.swapTwoPositions(new Position(r,c,boardSize),new Position(r,c + 1,boardSize));
                    if (!findAllMatches().isEmpty()){
                        backup.copyTo(candyBoard);
                        return true;
                    }
                    backup.copyTo(candyBoard);
                }

                // Swap with the candy to the left
                if (c > 1 && candyBoard.getCellAt(new Position(r,c - 1,boardSize)).getColor() != 99) {
                    candyBoard.swapTwoPositions(new Position(r,c,boardSize),new Position(r,c - 1,boardSize));
                    if (!findAllMatches().isEmpty()){
                        backup.copyTo(candyBoard);
                        return true;
                    }
                    backup.copyTo(candyBoard);
                }

                // Swap with the candy below
                if (r < boardSize.rows() && candyBoard.getCellAt(new Position(r + 1,c,boardSize)).getColor() != 99) {
                    candyBoard.swapTwoPositions(new Position(r,c,boardSize),new Position(r + 1,c,boardSize));
                    if (!findAllMatches().isEmpty()){
                        backup.copyTo(candyBoard);
                        return true;
                    }
                    backup.copyTo(candyBoard);
                }

                // Swap with the candy above
                if (r > 1 && candyBoard.getCellAt(new Position(r - 1,c,boardSize)).getColor() != 99) {
                    candyBoard.swapTwoPositions(new Position(r,c,boardSize),new Position(r - 1,c,boardSize));
                    if (!findAllMatches().isEmpty()){
                        backup.copyTo(candyBoard);
                        return true;
                    }
                    backup.copyTo(candyBoard);
                }
            }
        }
        return false;
    }
}

