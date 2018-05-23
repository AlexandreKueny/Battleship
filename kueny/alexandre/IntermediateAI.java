package kueny.alexandre;

public class IntermediateAI extends AI {

    IntermediateAI(String name) {
        super(name);
    }

    @Override
    public Coordinate askShootCoords() {
        Coordinate coord;
        do {
            coord = Coordinate.randomCoordinate();
        } while (!canShootAt(coord));
        return coord;
    }
}
