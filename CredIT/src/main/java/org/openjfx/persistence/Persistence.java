package org.openjfx.persistence;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;
import org.openjfx.domain.Production;
import org.openjfx.domain.User;
import org.openjfx.interfaces.*;

import java.io.*;
import java.lang.reflect.Array;
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
        this.castFile = new File("PersistenceFiles\\castFile.txt");
        this.broadcastFile = new File("PersistenceFiles\\broadcastFile.txt");
        this.productionFile = new File("PersistenceFiles\\productionFile.txt");
        this.userFile = new File("PersistenceFiles\\userFile.txt");
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
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");
                if (Integer.parseInt(user[0]) != id) {
                    newTxt += currentLine + "\n";

                } else {
                    returnBool = true;
                }
            }
            writer = new PrintWriter(userFile);
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
            for (String s : test.keySet()) {
                int i = 0;
                if (test.keySet().size()-1 != k) {
                    temp += s + ";";
                    for (ICast cast : test.get(s)) {
                        if (i == test.get(s).size()-1) {
                            temp += cast.getId() + "_";
                        } else {
                            temp += cast.getId() + ":";

                        }
                        i++;
                    }


                } else {
                    temp += s + ";";
                    for (ICast cast : test.get(s)) {
                        if (i == test.get(s).size()-1) {
                            temp += cast.getId() + ",";
                        } else {
                            temp += cast.getId() + ":";

                        }
                        i++;
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
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] broadcast = currentLine.split(",");
                if (Integer.parseInt(broadcast[0]) != id) {
                    newTxt += currentLine + "\n";

                } else {
                    returnBool = true;
                }
            }
            writer = new PrintWriter(broadcastFile);
            writer.print(newTxt);
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
            temp += production.getProductionCompany() + "," + production.getNumberOfSeasons() + "," + production.getNumberOfEpisodes();
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
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] production = currentLine.split(",");
                if (Integer.parseInt(production[0]) != id) {
                    newTxt += currentLine + "\n";

                } else {
                    returnBool = true;
                }
            }
            writer = new PrintWriter(productionFile);
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
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");
                if (Integer.parseInt(user[0]) != id) {
                    newTxt += currentLine + "\n";

                } else {
                    returnBool = true;
                }
            }
            writer = new PrintWriter(castFile);
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
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] info = line.split(",");
                if (keyword.contains(info[1].toLowerCase())) {
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

    @Override
    public List<String> getBroadcastFromDatabase(int id) {
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(broadcastFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] info = line.split(",");
                if (id == Integer.parseInt(info[0])) {
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



    @Override
    public List<String> getCastFromDatabase(String keyword) {
        keyword = keyword.toLowerCase();
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(castFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] info = line.split(",");
                if (keyword.contains(info[1].toLowerCase())) {
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

    @Override
    public List<String> getCastFromDatabase(int id) {
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(castFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] info = line.split(",");
                if (id == Integer.parseInt(info[0])) {
                    output.add(line);
                } else{
                    continue;
                }
            }

            return output;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.close();
        }



    }

    @Override
    public List<String> getProductionFromDatabase(String keyword) {
        keyword = keyword.toLowerCase();
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(productionFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] info = line.split(",");
                if (keyword.contains(info[1].toLowerCase())) {
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

    @Override
    public List<String> getProductionFromDatabase(int id) {
        List<String> output = new ArrayList<String>();
        try {
            reader = new Scanner(productionFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] info = line.split(",");
                if (id == Integer.parseInt(info[0])) {
                    output.add(line);
                } else{
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

    @Override
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2) {
        boolean returnBool = false;

        String newTxt = "";
        try {
            reader = new Scanner(broadcastFile);
            while (reader.hasNextLine()) {

                String output = "";
                String currentLine = reader.nextLine();



                if (currentLine.contains(String.valueOf(cast2.getId()))) {

                    String[] broadcast = currentLine.split(",");

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
                }else{
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

        return returnBool;
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

        try {
            Cast cast = new Cast(1, "Sarah", 1);
            Cast cast1 = new Cast(2, "Laust", 2);
            Cast cast2 = new Cast(3, "Teis", 3);
            Cast cast3 = new Cast(4, "Nichlas",4);
            Cast cast4 = new Cast(5,"Lise",5);
            Cast cast5 = new Cast(6,"Simon",6);

            persistence.createNewCastInDatabase(cast);
            persistence.createNewCastInDatabase(cast1);
            persistence.createNewCastInDatabase(cast2);
            persistence.createNewCastInDatabase(cast3);
            persistence.createNewCastInDatabase(cast4);
            persistence.createNewCastInDatabase(cast5);
            persistence.createNewCastInDatabase(cast);

            Broadcast b = new Broadcast(1, "hej i stuen",1,2,"02-02-2020");
            HashMap<String,ArrayList<ICast>> castMap= new HashMap<>();
            ArrayList<ICast> cameraList = new ArrayList<>();
            cameraList.add(cast);
            cameraList.add(cast1);
            ArrayList<ICast> producerList = new ArrayList<>();
            producerList.add(cast1);
            producerList.add(cast2);

            castMap.put("kameramand", cameraList);
            castMap.put("Producer", producerList);

            b.setCastMap(castMap);

            Broadcast b1 = new Broadcast(2, "hej i stuen 2",1,2,"02-02-2020");
            HashMap<String,ArrayList<ICast>> castMap1= new HashMap<>();
            ArrayList<ICast> cameraList1 = new ArrayList<>();
            cameraList1.add(new Cast(7,"Sarah",1));
            cameraList1.add(cast1);
            ArrayList<ICast> producerList1 = new ArrayList<>();
            producerList1.add(cast1);
            producerList1.add(cast2);

            castMap1.put("kameramand", cameraList1);
            castMap1.put("Producer", producerList1);

            b1.setCastMap(castMap1);

            persistence.createNewBroadcastInDatabase(b);
            persistence.createNewBroadcastInDatabase(b1);


            persistence.mergeCastInDatabase(cast, new Cast(7,"Sarah", 7));

            persistence.updateCastInDatabase(1, "Søren", 78);

            User u = new User(1, "Teis","password", "username", Role.SYSADMIN);

            persistence.createNewUserInDatabase(u);
            persistence.removeUserFromDatabase(2);

            System.out.println(persistence.getBroadcastFromDatabase(1));
            System.out.println(persistence.getBroadcastFromDatabase("hej"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



