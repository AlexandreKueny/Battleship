package kueny.alexandre;

import java.util.Arrays;
import java.util.Random;

public abstract class AI extends Player {

    protected Coordinate firstHitCoord, secondHitCoord;
	protected Coordinate lastHitCoord;
    protected Coordinate lastLastHitCoord;
    protected boolean lastShotResult;
    protected Coordinate lastShot;

    AI() {
        super();
    }

	AI(String name) {
		super(name);
		reset();
	}

	public final void reset() {
        super.reset();
        initialize();
    }

    public void setLastHitCoord(Coordinate lastHitCoord) {
	    if (firstHitCoord == null) {
	        firstHitCoord = lastHitCoord;
        } else if (secondHitCoord == null) {
	        secondHitCoord = lastHitCoord;
        }
	    lastLastHitCoord = this.lastHitCoord;
        this.lastHitCoord = lastHitCoord;
    }

    public void setLastShotResult(boolean lastShotResult) {
        this.lastShotResult = lastShotResult;
    }

    @Override
    public Coordinate[] askShipCoords(String name, int size) {
        Coordinate start;
        Coordinate ends[];
        int i;
		Random random = new Random();
        boolean[] is = new boolean[4];
        boolean blocked;
		do {
		    blocked = false;
		    for (int j = 0; j < 4; j++) is[j] = false;
            start = Coordinate.randomCoordinate();
            ends = start.CoordinatesAt(size - 1);
            i = random.nextInt(4);
            while (!ends[i].isValid() && !blocked) {
                i = random.nextInt(4);
                is[i] = true;
                blocked = true;
                for (boolean b : is) if (!b) blocked = false;
            }
        } while (blocked);
		Coordinate[] coords = new Coordinate[] {start, ends[i]};
		Arrays.sort(coords);
		return coords;
	}

	public final void initialize() {
        firstHitCoord = null;
        secondHitCoord = null;
        lastHitCoord = null;
        lastLastHitCoord = null;
        lastShot = null;
        lastShotResult = false;
    }
}
