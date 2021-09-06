package edu.estg.json;

import edu.estg.container.Container;
import edu.estg.container.RecyclingBin;
import edu.estg.route.GeographicCoordinates;
import edu.maen.core.enumerations.WasteType;
import edu.maen.core.exceptions.CityException;
import edu.maen.core.exceptions.ContainerException;
import edu.maen.core.exceptions.RecyclingBinException;
import edu.maen.core.interfaces.ICity;
import edu.maen.io.interfaces.IImporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Importer implements IImporter {

    private boolean isDistancesFile(JSONArray jsonArray) {

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            if (!(jsonObject.containsKey("to") && jsonObject.containsKey("from"))) {
                return false;
            }
        }

        return true;
    }

    private boolean isBinsFile(JSONArray jsonArray) {

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            boolean jsonOk = jsonObject.containsKey("Codigo") &
                    jsonObject.containsKey("Ref. Localização") &
                    jsonObject.containsKey("Zona") &
                    jsonObject.containsKey("Latitude") &
                    jsonObject.containsKey("Longitude") &
                    jsonObject.containsKey("Contentores");

            if (!jsonOk) {
                return false;
            }
        }

        return true;
    }

    private boolean isReadingsFile(JSONArray jsonArray) {

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            boolean jsonOk = jsonObject.containsKey("contentor") &
                    jsonObject.containsKey("data") &
                    jsonObject.containsKey("valor");

            if (!jsonOk) {
                return false;
            }
        }

        return true;
    }

    /**
     * Método para associar o WasteType ao código do ecoponto*
     *
     * @param containerCode código do ecoponto
     * @return
     */
    private WasteType assignWasteType(String containerCode) {
        if (containerCode.startsWith("V")) {
            return WasteType.V;
        } else if (containerCode.startsWith("B")) {
            return WasteType.B;
        } else if (containerCode.startsWith("E")) {
            return WasteType.E;
        } else if (containerCode.startsWith("P")) {
            return WasteType.P;
        }

        return null;
    }

    private void importDistances() {

    }

    private void importBins(ICity city, JSONArray binData) throws RecyclingBinException, ContainerException {
        for (Object o : binData) {
            JSONObject jsonObject = (JSONObject) o;

            // passar a [ objeto -> string -> double ]
            GeographicCoordinates coordinates = new GeographicCoordinates(
                    Double.parseDouble(jsonObject.get("Latitude").toString()),
                    Double.parseDouble(jsonObject.get("Longitude").toString())
            );

            RecyclingBin bin = new RecyclingBin(
                    jsonObject.get("Codigo").toString(),
                    jsonObject.get("Ref. Localização").toString(),
                    jsonObject.get("Zona").toString(),
                    coordinates
            );

            JSONArray containersArray = (JSONArray) jsonObject.get("Contentores");
            // todos os elementos que estiverem no array passam a objeto
            for (Object containerObject : containersArray) {
                JSONObject container = (JSONObject) containerObject;

                if (!(container.containsKey("codigo") && container.containsKey("capacidade"))) {
                    throw new RecyclingBinException("Invalid Containers");
                }

                // Para extrair o WasteType do Contentor temos que olhar para a primeira letra do Codigo
                String containerCode = container.get("codigo").toString();

                // Ler JsonArray "Contentores"
                // Injetar propiedades na class Container
                // Injetar Container no RecyclingBin
                Container binContainer = new Container(
                        containerCode,
                        Double.parseDouble(container.get("capacidade").toString()),
                        assignWasteType(containerCode)
                );

                bin.addContainer(binContainer);
            }


            city.addRecyclingBin(bin);
        }
    }


    private void importReadings() {

    }

    @Override
    public void importData(ICity city, String path) throws IOException, CityException {

        if (city == null) {
            throw new CityException("An error occurred... quitting! (City doesn't exist.");
        }

        if (!path.endsWith(".json")) {
            throw new IOException("Invalid file type!");
        }

        JSONParser parser = new JSONParser();

        // Passar o ficheiro Json para um JsonArray
        JSONArray jsonArray;
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader(path));
        } catch (ParseException e) {
            throw new IOException("Cannot read the file");
        }
        System.out.println(jsonArray);


        if (isBinsFile(jsonArray)) {
            try {
                importBins(city, jsonArray);
            } catch (RecyclingBinException | ContainerException e) {
                throw new IOException(e.toString());
            }
        } else if (isDistancesFile(jsonArray)) {
            importDistances();
        } else if (isReadingsFile(jsonArray)) {
            importReadings();
        }

    }

    @Override
    public void report(String s) throws IOException {
//comments
    }
}
