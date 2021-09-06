package edu.estg.route;

import edu.maen.core.interfaces.IPath;
import edu.maen.core.interfaces.IRecyclingBin;

public class Path implements IPath {

    private IRecyclingBin to;
    private int distance;
    private int duration;

    public Path(IRecyclingBin to, int distance, int duration) {
        this.to = to;
        this.distance = distance;
        this.duration = duration;
    }

    @Override
    public IRecyclingBin getTo() {
        return this.to;
    }

    @Override
    public int getDistance() {
        return this.distance;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }
}
