package kueny.alexandre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ExpertAI extends AI {

    ExpertAI(String name) {
        super(name);
    }

    @Override
    public Coordinate askShootCoords() {
        Coordinate coord;
        Random random = new Random();
        if (lastHitCoord == null) {
            initialize();
            coord = Coordinate.randomCoordinate();
        } else {
            if (lastLastHitCoord == null) {
                List<Coordinate> l = new ArrayList<>();
                for (Coordinate c : lastHitCoord.CoordinatesAt(1)) l.add(c);
                l.removeIf(c -> !c.isValid());

                if (shots.containsAll(l)) {
                    initialize();
                    coord = Coordinate.randomCoordinate();
                } else {
                    do {
                        coord = lastHitCoord.CoordinatesAt(1)[random.nextInt(4)];
                    } while (!coord.isValid());
                }
            } else {
                if (!Arrays.asList(lastHitCoord.CoordinatesAt(1)).contains(lastLastHitCoord)) {
                    initialize();
                    coord = Coordinate.randomCoordinate();
                } else {


                    if (!lastShotResult) {
                        if (firstHitCoord.equals(lastLastHitCoord)) {
                            lastLastHitCoord = lastHitCoord;
                        }
                        lastHitCoord = firstHitCoord;
                        lastLastHitCoord = secondHitCoord;
                    }

                    if (lastHitCoord == null || lastLastHitCoord == null) {
                        return lastHitCoord;
                    }


                    if (lastHitCoord.isOnSameColAs(lastLastHitCoord)) {
                        coord = lastHitCoord.clone();
                        coord.setY(lastHitCoord.getY() - 1);
                        Coordinate top = coord.clone();
                        coord.setY(lastHitCoord.getY() + 1);
                        Coordinate bottom = coord.clone();
                        if ((!top.isValid() || !canShootAt(top) && (!bottom.isValid() || !canShootAt(bottom)))) {
                            initialize();
                            coord = Coordinate.randomCoordinate();
                        } else {
                            do {
                                coord = lastHitCoord.clone();
                                if (lastHitCoord.compareTo(lastLastHitCoord) > 0) {
                                    coord.setY(lastHitCoord.getY() + 1);
                                } else {
                                    coord.setY(lastHitCoord.getY() - 1);
                                }
                                if (!this.canShootAt(coord)) {
                                    if (firstHitCoord.equals(lastLastHitCoord)) {
                                        lastLastHitCoord = lastHitCoord;
                                    }
                                    lastHitCoord = firstHitCoord;
                                    if (lastHitCoord.compareTo(lastLastHitCoord) > 0) {
                                        coord.setY(lastHitCoord.getY() + 1);
                                    } else {
                                        coord.setY(lastHitCoord.getY() - 1);
                                    }
                                }
                            } while (!coord.isValid() && coord.getY() > 0 && coord.getY() <= Coordinate.getGridSize());
                            if (!coord.isValid()) {
                                if (firstHitCoord.compareTo(lastHitCoord) > 0) {
                                    lastHitCoord = firstHitCoord;
                                    lastLastHitCoord = secondHitCoord;
                                    coord.setY(firstHitCoord.getY() + 1);
                                } else {
                                    lastHitCoord = firstHitCoord;
                                    lastLastHitCoord = secondHitCoord;
                                    coord.setY(firstHitCoord.getY() - 1);
                                }
                            }
                        }

                    } else {
                        coord = lastHitCoord.clone();
                        coord.setX(lastHitCoord.getX() - 1);
                        Coordinate left = coord.clone();
                        coord.setX(lastHitCoord.getX() + 1);
                        Coordinate right = coord.clone();
                        if ((!left.isValid() || !canShootAt(left) && (!right.isValid() || !canShootAt(right)))) {
                            initialize();
                            coord = Coordinate.randomCoordinate();
                        } else {
                            do {
                                coord = lastHitCoord.clone();
                                if (lastHitCoord.compareTo(lastLastHitCoord) > 0) {
                                    coord.setX(lastHitCoord.getX() + 1);
                                } else {
                                    coord.setX(lastHitCoord.getX() - 1);
                                }
                                if (!this.canShootAt(coord)) {
                                    if (firstHitCoord.equals(lastLastHitCoord)) {
                                        lastLastHitCoord = lastHitCoord;
                                    }
                                    lastHitCoord = firstHitCoord;
                                    if (lastHitCoord.compareTo(lastLastHitCoord) > 0) {
                                        coord.setX(lastHitCoord.getX() + 1);
                                    } else {
                                        coord.setX(lastHitCoord.getX() - 1);
                                    }
                                }
                            } while (!coord.isValid() && coord.getX() > 0 && coord.getX() <= Coordinate.getGridSize());
                            if (!coord.isValid()) {
                                if (firstHitCoord.compareTo(lastHitCoord) > 0) {
                                    lastHitCoord = firstHitCoord;
                                    lastLastHitCoord = secondHitCoord;
                                    coord.setX(firstHitCoord.getX() + 1);
                                } else {
                                    lastHitCoord = firstHitCoord;
                                    lastLastHitCoord = secondHitCoord;
                                    coord.setX(firstHitCoord.getX() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }
        lastShot = coord;
        return coord;
    }
}
