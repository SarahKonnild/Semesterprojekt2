package org.openjfx.persistence;

import org.openjfx.interfaces.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Persistence implements IPersistence {

    //Instance of the singleton class Persistence
    static Persistence instance = null;

    //ID variables. needed because java cant make a serial like a database.
    private static int userId;
    private static int broadcastId;
    private static int productionId;
    private static int castId;

    //References to the files we write to and read from
    private final File castFile;
    private final File productionFile;
    private final File broadcastFile;
    private final File userFile;

    //The readers and writers needed to read and write to the file
    private PrintWriter writer;
    private FileWriter fw = null;
    private Scanner reader;


    private Persistence() {
        this.castFile = new File("PersistenceFiles/castFile.txt");
        this.broadcastFile = new File("PersistenceFiles/broadcastFile.txt");
        this.productionFile = new File("PersistenceFiles/productionFile.txt");
        this.userFile = new File("PersistenceFiles/userFile.txt");
        this.writer = null;
        this.reader = null;

        initializeUserId();
        initializeBroadcastId();
        initializeCastId();
        initializeProductionId();
    }

    public static Persistence getInstance() {
        //If there is no instance of the Persistence class, make one
        if (instance == null) {
            instance = new Persistence();
        }

        //Return the singleton instance of Persistence
        return instance;
    }

    //region create new stuff in database methods here
    @Override
    public int createNewProductionInDatabase(IProduction production) throws IOException {
        int returnNumber = -1;
        try {
            fw = new FileWriter(productionFile, true);
            writer = new PrintWriter(fw);

            String temp = productionId + "," + production.getName() + ",";
            int i = 0;
            if (production.getBroadcasts() != null) {
                for (IBroadcast b : production.getBroadcasts()) {
                    if (production.getBroadcasts().size() - 1 != i) {
                        temp += b.getId() + ";";
                    } else {
                        temp += b.getId() + ",";
                    }
                    i++;

                }
            } else {
                temp += ",";
            }
            temp += production.getProductionCompany() + "," + production.getNumberOfSeasons() + "," + production.getNumberOfEpisodes();
            writer.println(temp);
            returnNumber = productionId;
            productionId++;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            fw.close();
        }

        return returnNumber;
    }

    @Override
    public int createNewCastInDatabase(ICast cast) throws IOException {
        int returnNumber = -1;
        try {
            fw = new FileWriter(castFile, true);
            writer = new PrintWriter(fw);

            writer.println(castId + "," + cast.getName() + "," + cast.getRegDKID());
            returnNumber = castId;
            castId++;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            fw.close();
        }

        return returnNumber;
    }

    @Override
    public boolean createNewUserInDatabase(IUser user) throws IOException {
        boolean returnBool = false;
        try {
            fw = new FileWriter(userFile, true);
            writer = new PrintWriter(fw);

            writer.println(userId + "," + user.getName() + "," + user.getPassword() + "," +
                    user.getUsername() + "," + user.getRole());

            userId++;

            returnBool = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            fw.close();
        }

        return returnBool;
    }

    @Override
    public int createNewBroadcastInDatabase(IBroadcast broadcast) throws IOException {
        int returnNumber = -1;
        try {
            fw = new FileWriter(broadcastFile, true);
            writer = new PrintWriter(fw);

            String outputString = broadcastId + "," + broadcast.getName() + ",";
            HashMap<String, ArrayList<ICast>> castMap = broadcast.getCastMap();
            int k = 0;
            // First check is run on castMap to see if it's not null.
            if (castMap != null) {
                for (String s : castMap.keySet()) {
                    int i = 0;

                    // Second check is run on the size of the keySet from castMap, to determine if we're on the last entry or not.
                    if (castMap.keySet().size() - 1 != k) {
                        outputString += s + ";";

                        //get the values from castMap and add it to outputString.
                        for (ICast cast : castMap.get(s)) {
                            //Third check is run on the size again to determine wether were on the last item or not in the returned value(ArrayList).
                            if (i == castMap.get(s).size() - 1) {
                                outputString += cast.getId() + "_";
                            } else {
                                outputString += cast.getId() + ":";
                            }
                            i++;
                        }
                    } else {
                        outputString += s + ";";
                        for (ICast cast : castMap.get(s)) {
                            if (i == castMap.get(s).size() - 1) {
                                outputString += cast.getId() + ",";
                            } else {
                                outputString += cast.getId() + ":";
                            }
                            i++;
                        }
                    }
                    k++;
                }
            }else{
                outputString += ",";
            }
            outputString += broadcast.getSeasonNumber() + "," + broadcast.getEpisodeNumber() + "," + broadcast.getAirDate()[0] +
                    "-" + broadcast.getAirDate()[1] + "-" + broadcast.getAirDate()[2] + "," + broadcast.getProductionName();
            writer.println(outputString);
            returnNumber = broadcastId;
            broadcastId++;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            fw.close();
        }

        return returnNumber;
    }

    //endregion
    
    //region remove from database methods goes here


    @Override
    public boolean removeUserFromDatabase(int id) {
        return removeDataFromDatabase(id, userFile);
    }

    @Override
    public boolean removeBroadcastFromDatabase(int id) {
        return removeDataFromDatabase(id, broadcastFile);
    }

    @Override
    public boolean removeProductionFromDatabase(int id) {
        return removeDataFromDatabase(id, productionFile);
    }

    @Override
    public boolean removeCastFromDatabase(int id) {
        return removeDataFromDatabase(id, castFile);
    }
    /**
     * Deletes data from the persistence/layer(Database).
     * It reads every line in the file, if the current line not equals the the parsed id, we add it to temperary String, else we just skip that line.
     * Finally we write the new information to the file.
     *
     * @param id The ID on the data you want to delete in the persistence layer.
     * @return returns the boolean value of the delete run.
     * @param file
     */
    private boolean removeDataFromDatabase(int id, File file) {
        boolean returnBool = false;
        String newTxt = "";
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] production = currentLine.split(",");
                if (Integer.parseInt(production[0]) != id) {
                    newTxt += currentLine + "\n";
                } else {
                    returnBool = true;
                }
            }
            writer = new PrintWriter(file);
            writer.write(newTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
        return returnBool;
    }

    //endregion

    //region get data from database methods here
    @Override
    public List<String> getBroadcastFromDatabase(String keyword) {
        return getDataFromDataBaseReadFile(broadcastFile, keyword);
    }

    @Override
    public List<String> getBroadcastFromDatabase(int id) {
        return getDataFromDataBaseReadFile(broadcastFile, id);
    }

    @Override
    public List<String> getCastFromDatabase(String keyword) {
        return getDataFromDataBaseReadFile(castFile, keyword);
    }

    @Override
    public List<String> getCastFromDatabase(int id) {
        return getDataFromDataBaseReadFile(castFile, id);
    }

    @Override
    public List<String> getProductionFromDatabase(String keyword) {
        return getDataFromDataBaseReadFile(productionFile, keyword);
    }

    @Override
    public List<String> getProductionFromDatabase(int id) {
        return getDataFromDataBaseReadFile(productionFile, id);
    }

    private List<String> getDataFromDataBaseReadFile(File file, String keyword){
        keyword = keyword.toLowerCase();
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] info = line.split(",");
                if (info[1].toLowerCase().contains(keyword)) {
                    output.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.close();
        }


        return output;
    }

    private List<String> getDataFromDataBaseReadFile(File file, int id){
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] info = line.split(",");
                if (id == Integer.parseInt(info[0])) {
                    output.add(line);
                } else {
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.close();
        }
        return output;
    }

    //endregion

    @Override
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2) {
        boolean returnBool = false;

        String newTxt = "";
        try {
            reader = new Scanner(broadcastFile);
            while (reader.hasNextLine()) {

                String output = "";
                String currentLine = reader.nextLine();

                //first check is run with contains method, to check if the line contains the ID of cast2.

                if (currentLine.contains(String.valueOf(cast2.getId()))) {
                    //splitting the line by "," - gives us the broadcast.
                    String[] broadcast = currentLine.split(",");

                    //splitting the string at index 2 in broadcast to get keyValuePairs of roles and the assigned casts.

                    String[] keyValuePairs = broadcast[2].split("_");


                    output = broadcast[0] + "," + broadcast[1] + ",";
                    int i = 0;
                    
                    for (String keyValue : keyValuePairs) {
                        int k = 0;
                        String[] keyValueSplit = keyValue.split(";");

                        String[] values = keyValueSplit[1].split(":");

                        if (i != keyValuePairs.length - 1) {
                            output += keyValueSplit[0] + ";";

                            for (String value : values) {

                                if (k != values.length - 1) {
                                    if (value.equals(String.valueOf(cast2.getId()))) {
                                        value = String.valueOf(cast1.getId());
                                    }

                                    output += value + ":";
                                } else {
                                    if (value.equals(String.valueOf(cast2.getId()))) {
                                        value = String.valueOf(cast1.getId());
                                    }

                                    output += value + "_";
                                }
                                k++;
                            }
                        } else {
                            output += keyValueSplit[0] + ";";

                            for (String value : values) {
                                if (k != values.length - 1) {
                                    if (value.equals(String.valueOf(cast2.getId()))) {
                                        value = String.valueOf(cast1.getId());
                                    }

                                    output += value + ":";
                                } else {
                                    if (value.equals(String.valueOf(cast2.getId()))) {
                                        value = String.valueOf(cast1.getId());
                                    }

                                    output += value + ",";
                                }
                                k++;
                            }
                        }
                        i++;
                    }
                    output += broadcast[3] + "," + broadcast[4] + "," + broadcast[5];
                } else {
                    output = currentLine;
                }
                newTxt += output + "\n";
            }
            newTxt = newTxt.trim();
            removeCastFromDatabase(cast2.getId());
            writer = new PrintWriter(broadcastFile);
            writer.println(newTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }

        return true;
    }

    @Override
    public boolean updateCastInDatabase(int id, String name, int regDKID) {
        boolean returnBool = false;
        String newTxt = "";

        try {
            reader = new Scanner(castFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");

                if (!String.valueOf(id).equals(user[0])) {
                    newTxt += currentLine + "\n";
                } else {
                    newTxt += user[0] + "," + name + "," + regDKID + "\n";
                }
            }
            writer = new PrintWriter(castFile);
            writer.write(newTxt);
            returnBool = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
        return returnBool;
    }

    //region initialize ID methods here.
    /**
     * Loops through the userFile and finds the largest userId. The userId on the class is instantiated to one higher than this.
     */
    private void initializeUserId() {
        userId = initializeReadFile(userFile);
    }

    private void initializeBroadcastId() {
        broadcastId = initializeReadFile(broadcastFile);
    }

    private void initializeProductionId() {
        productionId = initializeReadFile(productionFile);
    }

    private void initializeCastId() {
        castId = initializeReadFile(castFile);
    }

    private int initializeReadFile(File file){
        int id = 0;
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");
                int currentId = Integer.parseInt(user[0]);
                if (currentId > id) {
                    id = currentId;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return id+1;
    }
    //endregion

}
