package org.openjfx.persistence;

import org.openjfx.interfaces.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Persistence implements IPersistence {


    static Persistence instance = null;
    //ID variables. needed because java cant make a serial like a database.
    private static int userId;
    private static int broadcastId;
    private static int productionId;
    private static int castId;
    private final File castFile;
    private final File productionFile;
    private final File broadcastFile;
    private final File userFile;
    private PrintWriter writer;
    //private BufferedWriter bw;
    private FileWriter fw = null;
    private Scanner reader;


    private Persistence() {
        this.castFile = new File("PersistenceFiles\\castFile.csv");
        this.broadcastFile = new File("PersistenceFiles\\broadcastFile.csv");
        this.productionFile = new File("PersistenceFiles\\productionFile.csv");
        this.userFile = new File("PersistenceFiles\\userFile.csv");
        this.writer = null;
        this.reader = null;

        initializeUserId();
        initializeBroadcastId();
        initializeCastId();
        initializeProductionId();
    }

    public static Persistence getInstance() {
        if (instance == null) {
            instance = new Persistence();
        }

        return instance;
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

    /**
     * Deletes a user from the persistence/layer(Database).
     * It reads every line in the file, if the current line not equals the the parsed id, we add it to temperary String, else we just skip that line.
     * Finally we write the new information to the file.
     *
     * @param id The ID on the user you want to delete in the persistence layer.
     * @return returns the boolean value of the delete run.
     */
    @Override
    public boolean removeUserFromDatabase(int id) {
        boolean returnBool = false;
        String newTxt = "";
        try {
            reader = new Scanner(userFile);
            writer = new PrintWriter(userFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");
                if (Integer.parseInt(user[0]) != id) {
                    newTxt += currentLine + "\n";

                } else {
                    returnBool = true;
                }
            }
            writer.write(newTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
        return returnBool;
    }

    @Override
    public boolean createNewBroadcastInDatabase(IBroadcast broadcast) throws IOException {
        boolean returnBool = false;
        try {
            fw = new FileWriter(broadcastFile, true);
            writer = new PrintWriter(fw);

            String temp = broadcastId + "," + broadcast.getName() + ",";
            HashMap<String, ArrayList<ICast>> test = broadcast.getCastMap();
            int k = 0;
            int i = 0;
            for (String s : test.keySet()) {
                if (test.keySet().size() - 1 != k) {
                    temp += s + ";";
                    for (ICast cast : test.get(s)) {
                        if (i == test.get(s).size() - 1) {
                            temp += cast.getId() + "_";
                        } else {
                            temp += cast.getId() + ":";
                            i++;
                        }
                    }


                } else {
                    temp += s + ";";
                    for (ICast cast : test.get(s)) {
                        if (i == test.get(s).size() - 1) {
                            temp += cast.getId() + ",";
                        } else {
                            temp += cast.getId() + ":";
                            i++;
                        }
                    }
                }
                k++;
            }
            temp += broadcast.getSeasonNumber() + "," + broadcast.getEpisodeNumber() + "," + broadcast.getAirDate();
            writer.println(temp);
            broadcastId++;
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
    public boolean removeBroadcastFromDatabase(int id) {
        boolean returnBool = false;
        String newTxt = "";
        try {
            reader = new Scanner(broadcastFile);
            writer = new PrintWriter(broadcastFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] broadcast = currentLine.split(",");
                if (Integer.parseInt(broadcast[0]) != id) {
                    newTxt += currentLine + "\n";

                } else {
                    returnBool = true;
                }
            }
            writer.write(newTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
        return returnBool;
    }

    @Override
    public boolean createNewProductionInDatabase(IProduction production) throws IOException {
        boolean returnBool = false;
        try {
            fw = new FileWriter(productionFile, true);
            writer = new PrintWriter(fw);

            String temp = productionId + "," + production.getName() + ",";
            int i = 0;
            for (IBroadcast b : production.getBroadcasts()) {
                if (production.getBroadcasts().size() - 1 != i) {
                    temp += b.getId() + ";";
                } else {
                    temp += b.getId() + ",";
                }
                i++;

            }
            writer.println(temp);
            productionId++;
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
    public boolean removeProductionFromDatabase(int id) {
        boolean returnBool = false;
        String newTxt = "";
        try {
            reader = new Scanner(productionFile);
            writer = new PrintWriter(productionFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] production = currentLine.split(",");
                if (Integer.parseInt(production[0]) != id) {
                    newTxt += currentLine + "\n";

                } else {
                    returnBool = true;
                }
            }
            writer.write(newTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
        return returnBool;
    }

    @Override
    public boolean createNewCastInDatabase(ICast cast) throws IOException {
        boolean returnBool = false;
        try {
            fw = new FileWriter(castFile, true);
            writer = new PrintWriter(fw);

            writer.println(castId + "," + cast.getName() + "," + cast.getRegDKID());

            castId++;

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
    public boolean removeCastFromDatabase(int id) {
        boolean returnBool = false;
        String newTxt = "";
        try {
            reader = new Scanner(castFile);
            writer = new PrintWriter(castFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");
                if (Integer.parseInt(user[0]) != id) {
                    newTxt += currentLine + "\n";

                } else {
                    returnBool = true;
                }
            }
            writer.write(newTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
        return returnBool;
    }

    @Override
    public List<String> getBroadcastFromDatabase(String keyword) {
        keyword = keyword.toLowerCase();
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(broadcastFile);
            String line = reader.nextLine();
            while (reader.hasNextLine()) {
                String[] info = line.split(",");
                if (keyword.contains(info[1].toLowerCase()) || keyword.contains(info[0].toLowerCase())) {
                    output.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }


        return output;
    }

    @Override
    public List<String> getCastFromDatabase(String keyword) {
        keyword = keyword.toLowerCase();
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(castFile);
            String line = reader.nextLine();
            while (reader.hasNextLine()) {
                String[] info = line.split(",");
                if (keyword.contains(info[1].toLowerCase()) || keyword.contains(info[0].toLowerCase())) {
                    output.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }


        return output;
    }

    @Override
    public List<String> getProductionFromDatabase(String keyword) {
        keyword = keyword.toLowerCase();
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(productionFile);
            String line = reader.nextLine();
            while (reader.hasNextLine()) {
                String[] info = line.split(",");
                if (keyword.contains(info[1].toLowerCase()) || keyword.contains(info[0].toLowerCase())) {
                    output.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }


        return output;
    }

    @Override
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2) {
        boolean returnBool = false;

        String newTxt = "";
        try {
            reader = new Scanner(broadcastFile);
            writer = new PrintWriter(broadcastFile);
            while (reader.hasNextLine()) {

                String output = "";
                String currentLine = reader.nextLine();
                int i = 0;
                int k = 0;

                if (currentLine.contains(String.valueOf(cast2.getId()))) {

                    String[] broadcast = currentLine.split(",");

                    String[] keyValuePairs = broadcast[3].split("_");

                    output = broadcast[0] + "," + broadcast[1] + ",";

                    for (String keyValue : keyValuePairs) {
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
                }
                newTxt += output + "\n";
            }
            writer.write(newTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }

        return returnBool;
    }

    @Override
    public boolean updateCastInDatabase(int id, String name, int regDKID) {
        boolean returnBool = false;
        String newTxt = "";

        try {
            reader = new Scanner(castFile);
            writer = new PrintWriter(castFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");

                if (!String.valueOf(id).equals(user[0])) {
                    newTxt += currentLine + "\n";
                } else {
                    newTxt += user[0] + "," + name + "," + regDKID + "\n";
                }
            }

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

    /**
     * Loops through the userFile and finds the largest userId. The userId on the class is instantiated to one higher than this.
     */
    private void initializeUserId() {
        int id = 0;
        try {
            reader = new Scanner(userFile);
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

        userId = id + 1;
    }

    private void initializeCastId() {
        int id = 0;
        try {
            reader = new Scanner(castFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] cast = currentLine.split(",");
                int currentId = Integer.parseInt(cast[0]);
                if (currentId > id) {
                    id = currentId;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        castId = id + 1;
    }

    private void initializeBroadcastId() {
        int id = 0;
        try {
            reader = new Scanner(broadcastFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] broadcast = currentLine.split(",");
                int currentId = Integer.parseInt(broadcast[0]);
                if (currentId > id) {
                    id = currentId;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        broadcastId = id + 1;
    }

    private void initializeProductionId() {
        int id = 0;
        try {
            reader = new Scanner(productionFile);
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] production = currentLine.split(",");
                int currentId = Integer.parseInt(production[0]);
                if (currentId > id) {
                    id = currentId;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        productionId = id + 1;
    }


    public static void main(String[] args) {
        Persistence persistence = Persistence.getInstance();
        persistence.removeProductionFromDatabase(19);
    }

}



