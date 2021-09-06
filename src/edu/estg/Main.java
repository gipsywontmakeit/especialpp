package edu.estg;

import edu.estg.json.Importer;
import edu.estg.route.City;
import edu.maen.core.exceptions.CityException;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        City city = new City("Felgueiras");

        Importer jsonImporter = new Importer();
        try {
            jsonImporter.importData(city, "D:\\Javatp\\resources\\ecopontos.json");
        } catch (IOException | CityException e) {
            throw new Exception(Arrays.toString(e.getStackTrace()));
        }
    }
}
