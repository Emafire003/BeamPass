package me.emafire003.dev.beampass.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.emafire003.dev.beampass.BeamPass;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;

import static me.emafire003.dev.beampass.BeamPass.LOGGER;

public class DataSaver {

    public static String PATH = String.valueOf(FabricLoader.getInstance().getConfigDir().resolve("beampass_blocklist.json"));
    @SuppressWarnings("UnstableApiUsage")
    public static Type blockBypassListToken = new TypeToken<List<String>>(){}.getType();

    static Gson gson = new Gson();

    //If the file is already there it doesn't touch it in ANY way. Aka it doesn't reset it to default every time
    public static void createFile() {
        try {
            File datafile = new File(PATH);
            if (datafile.createNewFile()) {
                LOGGER.info("Creating a new file and writing default config on it");
                write();
            }else{
                LOGGER.info("File already created skipping...");
            }
        } catch (IOException e) {
            LOGGER.error("There was an error trying to save the data on the file! (First time saving it)");
            e.printStackTrace();
        }
    }



    public static void write() {
        try {
            FileWriter datafileWriter = new FileWriter(PATH);
            String head = gson.toJson("Here is a list of blocks that will be bypassed by the beacon beam") +"\n";
            String blockList = gson.toJson(BeamPass.bypassableBlocksIds) + "\n";

            datafileWriter.write(head);
            datafileWriter.append(blockList);

            datafileWriter.close();
        } catch (IOException e) {
            LOGGER.error("There was an error trying to save the data on the file!");
            e.printStackTrace();
        } catch (Exception e){
            LOGGER.error("There was an error while writing on the file");
            e.printStackTrace();
        }
    }


    public static String getFileLine(int line, FileReader file) throws IOException {
        int lineNumber;
        BufferedReader readbuffer = new BufferedReader(file);
        for (lineNumber = 1; lineNumber < 10; lineNumber++) {
            if (lineNumber == line) {
                String the_line = readbuffer.readLine();
                return the_line;
            } else{
                readbuffer.readLine();
            }
        }
        return "ERROR001-NOLINEFOUND";
    }


    public static @Nullable List<Block> getBlockList(){
        try {
            FileReader file = new FileReader(PATH);
            String line = getFileLine(2, file);
            if(line.equalsIgnoreCase("ERROR001-NOLINEFOUND")){
                return null;
            }
            List<String> list = gson.fromJson(line, blockBypassListToken);
            return BeamPass.convertToBlockList(list);
        } catch (NoSuchElementException e){
            return null;
        } catch (IOException e) {
            LOGGER.error("There was an error trying to read the data on the file!");
            e.printStackTrace();
            return null;
        } catch (Exception e){
            LOGGER.error("There was an error while reading on the file");
            e.printStackTrace();
            return null;
        }
    }
}
