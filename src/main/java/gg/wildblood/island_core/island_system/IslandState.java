package gg.wildblood.island_core.island_system;


import gg.wildblood.island_core.IslandCore;
import net.minecraft.client.MinecraftClient;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;
import org.spongepowered.include.com.google.gson.JsonSyntaxException;
import org.spongepowered.include.com.google.gson.reflect.TypeToken;
import org.spongepowered.include.com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IslandState {
	private static final File ConfigurationDirectory = QuiltLoader.getConfigDir().resolve("island_core").toFile();
	private static final File StateFile = new File(ConfigurationDirectory, "islands.json");

	private static IslandState instance = null;

	private List<Island> islands = new ArrayList<>();

	public List<Island> getIslands() {
		return islands;
	}

	public void setIslands(List<Island> islands) {
		this.islands = islands;
	}

	public void writeToStateFile() {
		if(!ConfigurationDirectory.exists() && !ConfigurationDirectory.mkdirs()) {
			IslandCore.LOGGER.info("Couldn't Create Config Directory for Island State.");
			return;
		}


		try(BufferedWriter writer = new BufferedWriter(new FileWriter(StateFile))) {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			gson.toJson(islands, writer);

		} catch (IOException e) {
			IslandCore.LOGGER.error("Cant Save Island State, IslandState file not found.");
		}
	}

	private void readFromStateFile() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try (JsonReader reader = new JsonReader(new FileReader(StateFile))) {

			Type islandListType = new TypeToken<List<Island>>() {}.getType();

			islands = gson.fromJson(reader, islandListType);

			if(islands == null) {
				IslandCore.LOGGER.warn("Cant Load Island State, IslandState file is empty. ( if this is the first time you load an island you can ignore this message! )");

				islands = new ArrayList<>();
			}

		} catch (FileNotFoundException e) {
			IslandCore.LOGGER.error("Cant Load Island State, IslandState file not found. FileNotFoundException");
		} catch (JsonSyntaxException e) {
			IslandCore.LOGGER.error("Cant Load Island State, IslandState file is corrupted.");
		} catch (IOException e) {
			IslandCore.LOGGER.error("Cant Load Island State, IslandState file not found. IOException");
		}
	}

	public static IslandState getInstance() {
		if(instance == null) {
			instance = new IslandState();
			instance.readFromStateFile();
		}
		return instance;
	}
}
