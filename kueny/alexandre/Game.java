package kueny.alexandre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private Player currentPlayer, otherPlayer, firstPlayer;
    private int mode, level1, level2;

    static private HashMap<String, Integer> shipSizes = new HashMap<String, Integer>() {{
        put("Carrier", 5);
        put("Battleship", 4);
        put("Cruiser", 3);
        put("Submarine", 3);
        put("Destroyer", 2);
    }};

    public Game(String name1, String name2) {
        currentPlayer = new Player(name1);
        otherPlayer = new Player(name2);
        firstPlayer = currentPlayer;
        mode = 1;
    }

    public Game(String name1, int level) {
        currentPlayer = new Player(name1);
        switch (level) {
            case 1:
                otherPlayer = new BeginnerAI("AI");
                break;
            case 2:
                otherPlayer = new IntermediateAI("AI");
                break;
            case 3:
                otherPlayer = new ExpertAI("AI");
                break;
        }
        firstPlayer = currentPlayer;
        mode = 2;
        level1 = level;
    }

    public Game(int level1, int level2) {
        switch (level1) {
            case 1:
                currentPlayer = new BeginnerAI("AI 1");
                break;
            case 2:
                currentPlayer = new IntermediateAI("AI 1");
                break;
            case 3:
                currentPlayer = new ExpertAI("AI 1");
                break;
        }
        switch (level2) {
            case 1:
                otherPlayer = new BeginnerAI("AI 2");
                break;
            case 2:
                otherPlayer = new IntermediateAI("AI 2");
                break;
            case 3:
                otherPlayer = new ExpertAI("AI 2");
                break;
        }
        firstPlayer = currentPlayer;
        mode = 3;
        this.level1 = level1;
        this.level2 = level2;
    }

    public void reset() {
        if (currentPlayer == firstPlayer) {
            currentPlayer = otherPlayer;
            otherPlayer = firstPlayer;
        }
        currentPlayer.reset();
        otherPlayer.reset();
    }

    public void play() {
        for (Map.Entry<String, Integer> entry : shipSizes.entrySet()) {
            String name = entry.getKey();
            int size = entry.getValue();
            Coordinate coords[];
            do {
                coords = getCurrentPlayer().askShipCoords(name, size);
            } while (!isValid(coords[0], coords[1]) || Coordinate.getDist(coords[0], coords[1]) != size);

            Ship s = new Ship(name, coords[0], coords[1]);
            getCurrentPlayer().addShip(s);
        }
        nextTurn();

        for (Map.Entry<String, Integer> entry : shipSizes.entrySet()) {
            String name = entry.getKey();
            int size = entry.getValue();
            Coordinate coords[];
            do {
                coords = getCurrentPlayer().askShipCoords(name, size);
            } while (!isValid(coords[0], coords[1]) || Coordinate.getDist(coords[0], coords[1]) != size);

            Ship s = new Ship(name, coords[0], coords[1]);
            getCurrentPlayer().addShip(s);
        }
        nextTurn();

        while (!isDone()) {
            System.out.println("\nTour de " + getCurrentPlayer().getName());

            Coordinate whereToShoot = getCurrentPlayer().askShootCoords();

            if (!getCurrentPlayer().canShootAt(whereToShoot) || !whereToShoot.isValid()) {
                do {
                    System.out.println(whereToShoot + " Tir non valide.");
                    whereToShoot = getCurrentPlayer().askShootCoords();
                } while (!getCurrentPlayer().canShootAt(whereToShoot) || !whereToShoot.isValid());
            }


            boolean[] result = getCurrentPlayer().shoot(getOtherPlayer(), whereToShoot);
            boolean hit = result[0];
            boolean sank = result[1];

            System.out.println(whereToShoot);

            if (result[0]) getCurrentPlayer().addHit(whereToShoot);
            if (getCurrentPlayer() instanceof AI) {
                ((AI)getCurrentPlayer()).setLastShotResult(result[0]);
                if (result[0]) ((AI)getCurrentPlayer()).setLastHitCoord(whereToShoot);
                if (result[1]) ((AI)getCurrentPlayer()).initialize();
            }

            System.out.println("touché ? : " + hit);
            System.out.println("coulé ? : " + sank);

            nextTurn();
        }
        System.out.println("Partie terminée. " + winner().getName() + " gagne");
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    private boolean isDone() {
        return currentPlayer.hasLost() || otherPlayer.hasLost();
    }

    public Player winner() {
        return currentPlayer.hasLost() ? otherPlayer : currentPlayer;
    }

    private void nextTurn() {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    private boolean isOverlapping(Coordinate start, Coordinate end) {
        boolean result = false;
        List<Coordinate> placedSpots = new ArrayList<>();

        for (Ship ship : currentPlayer.getShips()) {
            placedSpots.addAll(ship.getSpots());
        }

        for (Coordinate coord : Coordinate.getSpots(start, end)) {
            if (placedSpots.contains(coord)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean isValid(Coordinate start, Coordinate end) {
        return start.isValid() && end.isValid() && !isOverlapping(start, end) && (start.getX() == end.getX() || start.getY() == end.getY());
    }

}
