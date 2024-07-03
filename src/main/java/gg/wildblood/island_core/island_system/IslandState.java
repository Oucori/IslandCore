package gg.wildblood.island_core.island_system;


import gg.wildblood.island_core.IslandCore;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;
import org.spongepowered.include.com.google.gson.JsonSyntaxException;
import org.spongepowered.include.com.google.gson.annotations.Expose;
import org.spongepowered.include.com.google.gson.reflect.TypeToken;
import org.spongepowered.include.com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IslandState {
	@Expose(serialize = false, deserialize = false)
	private static IslandState instance = null;

	private List<Island> islands = new ArrayList<>();

	public List<Island> getIslands() {
		return islands;
	}

	public void setIslands(List<Island> islands) {
		this.islands = islands;
	}

	public boolean writeToStateFile() {
		try {
			File StateFile = QuiltLoader.getConfigDir().resolve("islands.json").toFile();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			gson.toJson(islands, new FileWriter(StateFile));

			return true;
		} catch (IOException e) {
			IslandCore.LOGGER.error("Cant Save Island State, IslandState file not found.");

			return false;
		}
	}

	private boolean readFromStateFile() {
		try {
			File StateFile = QuiltLoader.getConfigDir().resolve("islands.json").toFile();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			JsonReader reader = new JsonReader(new FileReader(StateFile));

			Type islandListType = new TypeToken<List<Island>>() {}.getType();

			IslandState data = gson.fromJson(reader, islandListType);

			if(data == null) {
				IslandCore.LOGGER.warn("Cant Load Island State, IslandState file is empty. ( if this is the first time you load an island you can ignore this message! )");

				return false;
			}

			this.setIslands(data.getIslands());

			return true;
		} catch (FileNotFoundException e) {
			IslandCore.LOGGER.error("Cant Load Island State, IslandState file not found.");

			return false;
		} catch (JsonSyntaxException e) {
			IslandCore.LOGGER.error("Cant Load Island State, IslandState file is corrupted.");

			return false;
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
