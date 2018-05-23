package kueny.alexandre;

public class BeginnerAI extends AI {

    BeginnerAI(String name) {
        super(name);
    }

    @Override
    public boolean[] shoot(Player player, Coordinate coord) {
        return player.shootAt(coord);
    }

    @Override
    public Coordinate askShootCoords() {
        return Coordinate.randomCoordinate();
    }
}
