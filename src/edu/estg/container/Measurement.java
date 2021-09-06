package edu.estg.container;

import edu.maen.core.interfaces.IContainer;
import edu.maen.core.interfaces.IMeasurement;
import edu.maen.core.interfaces.IRecyclingBin;

import java.time.LocalDateTime;

public class Measurement implements IMeasurement {

    private double value;
    private LocalDateTime time;
    private int id;
    private IContainer container;
    private IRecyclingBin recyclingBin;

    public Measurement(double value, LocalDateTime time, int id, IContainer container, IRecyclingBin recyclingBin) {
        this.value = value;
        this.time = time;
        this.id = id;
        this.container = container;
        this.recyclingBin = recyclingBin;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public IContainer getContainer() {
        return this.container;
    }

    @Override
    public IRecyclingBin getRecyclingBin() {
        return this.recyclingBin;
    }

    @Override
    public LocalDateTime getDate() {
        return this.time;
    }

    @Override
    public double getValue() {
        return this.value;
    }
}
