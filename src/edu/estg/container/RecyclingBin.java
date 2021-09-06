package edu.estg.container;

import edu.maen.core.enumerations.WasteType;
import edu.maen.core.exceptions.ContainerException;
import edu.maen.core.exceptions.RecyclingBinException;
import edu.maen.core.interfaces.IContainer;
import edu.maen.core.interfaces.IGeographicCoordinates;
import edu.maen.core.interfaces.IPath;
import edu.maen.core.interfaces.IRecyclingBin;

public class RecyclingBin implements IRecyclingBin {

    private String code;
    private String zone;
    private String refLocal;
    private IPath[] paths;
    private IGeographicCoordinates coordinates;
    private IContainer[] containers;
    private int containerSize = 10;
    private int containerIndex = 0;

    public RecyclingBin(String code, String zone, String refLocal, IGeographicCoordinates coordinates) {
        this.code = code;
        this.zone = zone;
        this.refLocal = refLocal;
        this.coordinates = coordinates;
        this.containers = new IContainer[containerSize];
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getZone() {
        return this.zone;
    }

    @Override
    public String getRefLocal() {
        return this.refLocal;
    }

    @Override
    public String getType() {
        return "A RecyclingBin can have more than one type";
    }

    @Override
    public IPath getDistanceAndDuration(IRecyclingBin irb) throws RecyclingBinException {

        if (irb == null) {
            throw new RecyclingBinException("RecyclingBin is null.");
        }

        for (int i = 0; i < this.paths.length; i++) {
            if (irb.equals(paths[i].getTo())) {
                return paths[i];
            }
        }
        return null;

    }

    @Override
    public IGeographicCoordinates getCoordinates() {
        return this.coordinates;
    }


    @Override
    public boolean addContainer(IContainer container) throws ContainerException {
        if (container == null) {
            throw new ContainerException("Container is null");
        }

        // Verificar se o container ja existe


        //criar o container exists - URGENTE
        if (!containerExists(container)) {
            return false;
        }

        // Expandinr o array
        if (this.containerSize == this.containerIndex) {
            // A capacidade esgotou
            expandContainerArray();
        }

        this.containers[this.containerIndex++] = container;

        return false;
    }


    @Override
    public IContainer getContainer(WasteType wt) throws ContainerException {
        for (int i = 0; i < containers.length; i++) {
            if (containers[i].getType() == wt) {
                return containers[i];
            }
        }
        throw new ContainerException("Container not found");
    }

    @Override
    public IContainer[] getContainers() {
        return new IContainer[0];
    }

    @Override
    public boolean addPath(IPath path) throws RecyclingBinException {
        if (path == null) {
            throw new RecyclingBinException("Path is null");
        }

        if (path.getTo() == null) {
            throw new RecyclingBinException("The destination is null");
        }

        if (path.getDuration() < 0 || path.getDistance() < 0) {
            throw new RecyclingBinException("Can't have a negative value");
        }

        if (verifyAddPath(path)) {
            for (int i = 0; i < paths.length; i++) {
                if (paths[i] == null) {
                    paths[i] = path;
                    return true;
                }
            }
        }
        return false;
    }

    private void expandContainerArray() {
        // Passo 1 Criar um novo array
        IContainer[] resizedArray = new IContainer[containerSize *= 2];

        // Passo 2 Copiar os elementos do array antigo para o novo array
        for (int i = 0; i < this.containerIndex; i++) {
            resizedArray[i] = this.containers[i];
        }

        // Passo 3 Trocar os apontadores de memoria
        this.containers = resizedArray;
    }

    public boolean verifyAddContainer(IContainer container) throws ContainerException {

        for (int i = 0; i < containers.length; i++) {
            if (container.getType() == containers[i].getType()) {
                throw new ContainerException("Container with same waste type already exists");
            }
            if (container.equals(containers[i])) {
                return false;
            }
        }

        return true;
    }

    public boolean containerExists(IContainer iContainer) {
        Container container = (Container) iContainer;

        for (int i = 0; i < this.containerIndex; i++) {
            if (container.equals(this.containers[i])) {
                return true;
            }
        }

        return false;
    }

    public boolean verifyAddPath(IPath path) {
        for (int i = 0; i < paths.length; i++) {
            if (paths[i].equals(path)) {
                return false;
            }
        }
        return true;
    }

}
