package edu.estg.container;

import edu.maen.core.enumerations.WasteType;
import edu.maen.core.interfaces.IContainer;

public class Container implements IContainer {

    private final String code;
    private final double capacity;
    private final WasteType type;

    public Container(String code, double capacity, WasteType type) {
        this.code = code;
        this.capacity = capacity;
        this.type = type;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public double getCapacity() {
        return this.capacity;
    }

    @Override
    public WasteType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {

        // Se o objeto é exatamente igual a este objeto
        // Compara o endereço de memoria
        if (o == this) {
            return true;
        }

        // Verificar se o objecto é um Container
        if (!(o instanceof Container)) {
            return false;
        }

        Container container = (Container) o;

        return container.code.equals(this.code);
    }
}
