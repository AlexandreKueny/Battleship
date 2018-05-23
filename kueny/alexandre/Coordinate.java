package kueny.alexandre;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Coordinate implements Cloneable, Comparable<Coordinate> {

    static private String separator = "(?<=\\D)(?=\\d)";
    private int x;
    private int y;

    private static int GRID_SIZE = 10;

    Coordinate(String coordinate) {
        try {
            x = (int) coordinate.split(separator)[0].toUpperCase().charAt(0) - 64;
            y = Integer.parseInt(coordinate.split(separator)[1]);
        } catch (Exception e) {
            return;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static int getGridSize() {
        return GRID_SIZE;
    }

    public boolean isValid() {
        return this.getX() >= 1 && this.getX() <= GRID_SIZE && this.getY() >= 1 && this.getY() <= GRID_SIZE;
    }

    static public int getDist(Coordinate start, Coordinate end) {
        return (int)Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2)) + 1;
    }

    public boolean equals(Object other) {
        Coordinate o = (Coordinate) other;
        return this.getX() == o.getX() && this.getY() == o.getY();
    }

    public Coordinate clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return (Coordinate) o;
    }

    static public Coordinate randomCoordinate() {
        Random random = new Random();
        char x = (char)(random.nextInt(GRID_SIZE) + 1 + 64);
        int y = random.nextInt(GRID_SIZE) + 1;
        return new Coordinate("" + x + y);
    }

    public Coordinate[] CoordinatesAt(int dist) {
        Coordinate top = new Coordinate("" + (char)(this.getX() + 64) + ( (this.getY() - dist) < 0 ? 0 : (this.getY() - dist) ));
        Coordinate right = new Coordinate("" + (char)(this.getX() - dist + 64) + this.getY());
        Coordinate bottom = new Coordinate("" + (char)(this.getX() + 64) + (this.getY() + dist));
        Coordinate left = new Coordinate("" + (char)(this.getX() + dist + 64) + this.getY());
        return new Coordinate[] {top, right, bottom, left};
    }

    static public List<Coordinate> getSpots(Coordinate start, Coordinate end) {

        if ( (start.getX() == end.getX() && end.getY() < start.getY() ) || ( start.getY() == end.getY() && end.getX() < start.getX() ) ) {
            Coordinate tmp = start.clone();
            start = end;
            end = tmp;
        }

        List<Coordinate> list = new ArrayList<>();
        Coordinate coord = start.clone();
        list.add(coord.clone());
        if (Coordinate.getDist(start, end) == 1) return list;
        if (start.getX() == end.getX()) {
            do {
                coord.setY(coord.getY() + 1);
                list.add(coord.clone());
            } while (!coord.equals(end));
        } else if (start.getY() == end.getY()) {
            do {
                coord.setX(coord.getX() + 1);
                list.add(coord.clone());
            } while (!coord.equals(end));
        }
        return list;
    }

    public boolean isOnSameColAs(Coordinate other) {
        return this.getX() == other.getX();
    }

    public boolean isOnSameRowAs(Coordinate other) {
        return this.getY() == other.getY();
    }

    @Override
    public String toString() {
        return (char)(x+64) + "" + y;
    }

    private String toFormattedString() {
        return (char)(x+64) + "" + String.format("%02d", y);
    }

    @Override
    public int compareTo(Coordinate o) {
        return this.toFormattedString().compareTo(o.toFormattedString());
    }
}
