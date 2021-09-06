package edu.estg.route;

import edu.estg.container.RecyclingBin;
import edu.maen.core.enumerations.WasteType;
import edu.maen.core.exceptions.ContainerException;
import edu.maen.core.exceptions.MeasurementException;
import edu.maen.core.exceptions.RecyclingBinException;
import edu.maen.core.interfaces.ICity;
import edu.maen.core.interfaces.IContainer;
import edu.maen.core.interfaces.IMeasurement;
import edu.maen.core.interfaces.IRecyclingBin;

public class City implements ICity {

    private String name;
    private IRecyclingBin[] irb;
    private IMeasurement[] im;
    private int binCont = 0;

    private static int MAX_RECYCLING_BIN_ARRAY = 10;


    public City(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean addRecyclingBin(IRecyclingBin recyclingBin) throws RecyclingBinException {

        if (this.binCont == this.irb.length) {
            this.addRecyclingBinArray();
        }

        if (recyclingBin == null) {
            throw new RecyclingBinException("RecyclingBin is null!");
        }

        if (recyclingBin.getCode().length() != 10) {
            throw new RecyclingBinException("That RecyclingBin code is not valid. Please insert a new one.");
        }

        RecyclingBin tempArray = (RecyclingBin) recyclingBin;

        return true;
    }

    @Override
    public boolean addMeasurement(IMeasurement iMeasurement, IContainer iContainer) throws ContainerException, MeasurementException {
        return false;
    }

    @Override
    public IRecyclingBin[] getRecyclingBin() {
        return this.irb;
    }

    @Override
    public IMeasurement[] getMeasurements(IRecyclingBin iRecyclingBin, WasteType wasteType) {
        /**
        IMeasurement[] typeOfMeasurement;
        int sizeOfArray = 0;

        for (int j = 0; j < irb.length; j++) {
            for (int i = 0; i < im.length; i++) {
                try {
                    if (irb[j].getContainer(wt) == im[i].getContainer()) {
                        sizeOfArray++;
                    }
                } catch (ContainerException ex) {
                    System.out.println("An error has occurred, quitting...");
                }
            }
        }

        typeOfMeasurement = new IMeasurement[sizeOfArray];

        for (int j = 0; j < irb.length; j++) {

            for (int i = 0; i < im.length; i++) {

                try {
                    if (irb[j].getContainer(wt) == im[i].getContainer()) {
                        for (int k = 0; k < typeOfMeasurement.length; k++) {
                            if (typeOfMeasurement[k] == null) {
                                typeOfMeasurement[k] = im[i];
                            }
                        }
                    }
                } catch (ContainerException ex) {
                    System.out.println("An error occurred, quitting...");
                }


            }
        }

        return typeOfMeasurement;
        **/

        return null;
    }

    public void addRecyclingBinArray () {

        IRecyclingBin[] tempArray = new IRecyclingBin[binCont + 10];

        for(int i = 0; i < MAX_RECYCLING_BIN_ARRAY; i++) {
            tempArray[i] = irb[i];
        }

        MAX_RECYCLING_BIN_ARRAY = tempArray.length;
        irb = tempArray;

    }



}

