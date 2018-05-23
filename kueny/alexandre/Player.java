package kueny.alexandre;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Arrays;

public class Player {
    private String name;
    private List<Ship> ships;
    protected List<Coordinate> shots;
    private List<Coordinate> shootedAt;
    private List<Coordinate> hits;

    Player() {
    }

    Player(String name) {
        this.name = name;
        reset();
    }

    public void reset() {
        ships = new ArrayList<>();
        shots = new ArrayList<>();
        shootedAt = new ArrayList<>();
        hits = new ArrayList<>();
    }

    public void addHit(Coordinate hit) {
        hits.add(hit);
    }

    public String getName() {
        return name;
    }

    public boolean[] shoot(Player player, Coordinate coord) {
        addShot(coord);
        return player.shootAt(coord);
    }

    public boolean[] shootAt(Coordinate coord) {
        shootedAt.add(coord);
        boolean hit = false, sank = false;
        ListIterator<Ship> s = ships.listIterator();
        while (!hit && s.hasNext()) {
            Ship ship = s.next();
            hit = shootAtHit(ship, coord);
            if (hit) sank = shootAtSunk(ship);
        }
        return new boolean[]{hit, sank};
    }

    private boolean shootAtHit(Ship ship, Coordinate coord) {
        boolean hit;
        hit = ship.isHit(coord);
        return hit;
    }

    private boolean shootAtSunk(Ship ship) {
        boolean sank;
        sank = ship.isDestroyed();
        return sank;
    }

    public void addShot(Coordinate coord) {
        shots.add(coord);
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public boolean canShootAt(Coordinate coord) {
        return !shots.contains(coord);
    }

    public boolean hasLost() {
        boolean lost = true;
        if (ships.size() == 0) lost = false;
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                lost = false;
                break;
            }
        }
        return lost;
    }

    public String showGrid() {
        List<Coordinate> placedCoords = new ArrayList<>();
        for (Ship s : ships) {
            placedCoords.addAll(s.getSpots());
        }

        String output = "\n   ";
        for (int x = 65; x < 65 + 10; x++) {
            output += (char) x + "  ";
        }
        output += "             ";
        for (int x = 65; x < 65 + 10; x++) {
            output += (char) x + "  ";
        }
        output += "\n";
        for (int y = 1; y <= 10; y++) {
            output += "" + y + (y < 10 ? "  " : " ");
            for (int x = 1; x <= 10; x++) {
                Coordinate c = new Coordinate("" + (char)(x+64) + y);
                if (shots.contains(c) && hits.contains(c)) output += "V  ";
                else if (shots.contains(c)) output += "X  ";
                else output += "-  ";
            }
            output += "          ";
            output += "" + y + (y < 10 ? "  " : " ");
            for (int x = 1; x <= 10; x++) {
                Coordinate c = new Coordinate("" + (char)(x+64) + y);
                if (shootedAt.contains(c) && placedCoords.contains(c)) output += "Ø  ";
                else if (shootedAt.contains(c)) output += "X  ";
                else {
                    if (placedCoords.contains(c)) output += "O  ";
                    else output += "-  ";
                }
            }
            output += "\n";
        }
        return output;
    }

    public Coordinate[] askShipCoords(String name, int size) {
        Scanner sc = new Scanner(System.in);
        Coordinate start;
        Coordinate end;
        if (this.getShips().size() > 0) {
            System.out.println("Placed ships coordinates:");
            for (Ship ship : this.getShips()) {
                System.out.println(ship);
            }
        }
        System.out.println(this.getName() + " enter " + name + " start coordinates. size : " + size);
        start = new Coordinate(sc.nextLine());
        System.out.println(this.getName() + " enter " + name + " end coordinates. size : " + size);
        end = new Coordinate(sc.nextLine());
        Coordinate[] coords = new Coordinate[] {start, end};
        Arrays.sort(coords);
        return coords;
    }

    public Coordinate askShootCoords() {
        Scanner sc = new Scanner(System.in);
        System.out.println(showGrid());
        System.out.println("Où tirer ?");
        return new Coordinate(sc.nextLine());
    }
}
