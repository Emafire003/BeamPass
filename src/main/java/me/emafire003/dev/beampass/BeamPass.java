package me.emafire003.dev.beampass;

import me.emafire003.dev.beampass.config.DataSaver;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BeamPass implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "beampass";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static List<Block> bypassableBlocks = new ArrayList<>();
	public static List<String> bypassableBlocksIds = new ArrayList<>();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		initializeBypassableBlocks();
		DataSaver.createFile();
		bypassableBlocks = DataSaver.getBlockList();
		LOGGER.info("Done!");
	}

	private void initializeBypassableBlocks(){
		//Suggestions accepted on adding other blocks here
		bypassableBlocks.add(Blocks.LODESTONE);
		bypassableBlocks.add(Blocks.PRISMARINE);
		bypassableBlocks.add(Blocks.BEE_NEST);
		bypassableBlocksIds = convertFromBlockList(bypassableBlocks);
	}

	/**
	 * Used to convert Block id values to string (those in a list)
	 * */
	public static List<String> convertFromBlockList(List<Block> typelist){
		List<String> list = new ArrayList<>();
		for(Block type : typelist){
			list.add(Registry.BLOCK.getId(type).toString());
		}
		return list;
	}

	/**
	 * Used to convert String values to EntityType (those in a list)
	 * */
	public static List<Block> convertToBlockList(List<String> typelist){
		List<Block> list = new ArrayList<>();
		for(String type : typelist){
			Block block = Registry.BLOCK.get(new Identifier(type));
			if(block != null){
				list.add(block);
			}

		}
		return list;
	}
}
