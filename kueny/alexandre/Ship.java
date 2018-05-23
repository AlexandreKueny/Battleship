package kueny.alexandre;

import java.util.List;
import java.util.Arrays;

public class Ship {
    private String name;
    private Coordinate startCoord, endCoord;
    private int size, touched;

    Ship(String name, Coordinate startCoord, Coordinate endCoord) {
        this.name = name;
        touched = 0;
        Coordinate[] coords = new Coordinate[]{startCoord, endCoord};
        Arrays.sort(coords);
        this.startCoord = coords[0];
        this.endCoord = coords[1];
        size = Coordinate.getDist(startCoord, endCoord);
    }

    public int getSize() {
        return size;
    }

    public List<Coordinate> getSpots() {
        return Coordinate.getSpots(startCoord, endCoord);
    }

    public boolean isHit(Coordinate missileCoord) {
        if (startCoord.getX() <= missileCoord.getX() && missileCoord.getX() <= endCoord.getX() && startCoord.getY() <= missileCoord.getY() && missileCoord.getY() <= endCoord.getY()) {
            touched++;
            return true;
        } else return false;
    }

    public boolean isDestroyed() {
        return touched == size;
    }

    public String toString() {
        return String.format("Ship [name : %s, start : %s, end : %s, size : %d]", name, startCoord, endCoord, size);
    }
}
