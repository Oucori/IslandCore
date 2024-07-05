package gg.wildblood.island_core.data;

import gg.wildblood.island_core.IslandCore;
import gg.wildblood.island_core.util.SerializableDateTime;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;
import org.spongepowered.include.com.google.gson.JsonSyntaxException;
import org.spongepowered.include.com.google.gson.annotations.Expose;
import org.spongepowered.include.com.google.gson.stream.JsonReader;

import java.io.*;
import java.time.ZonedDateTime;

public class IslandCoreConfiguration {
	@Expose(deserialize = false, serialize = false)
	private static final File CONFIG_FILE = new File(IslandCore.ConfigurationDirectory, "island_core_config.json");

	private int islandDistance = 100;
	private SerializableDateTime serverStartTime = new SerializableDateTime(ZonedDateTime.now());
	private int maxPlayerPerIsland = 2;
	private boolean reloadSpawnStructure = true;

	private static IslandCoreConfiguration instance;

	public static IslandCoreConfiguration getInstance() {
		if(instance == null) {
			instance = new IslandCoreConfiguration();
			instance.loadConfiguration();
		}
		return instance;
	}

	public IslandCoreConfiguration() {
	}

	public void loadConfiguration() {
		if(!IslandCore.ConfigurationDirectory.exists() && !IslandCore.ConfigurationDirectory.mkdirs()) {
			IslandCore.LOGGER.info("Couldn't Create Config Directory for IslandCore Configuration whilst reading.");
			return;
		}

		if(!CONFIG_FILE.exists()) {
			saveConfiguration();
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try (JsonReader reader = new JsonReader(new FileReader(CONFIG_FILE))) {
			IslandCoreConfiguration configuration = gson.fromJson(reader, IslandCoreConfiguration.class);

			this.setIslandDistance(configuration.getIslandDistance());
			this.setServerStartTime(configuration.getServerStartTime());
			this.setMaxPlayerPerIsland(configuration.getMaxPlayerPerIsland());
			this.setReloadSpawnStructure(configuration.getReloadSpawnStructure());

		} catch (FileNotFoundException e) {
			IslandCore.LOGGER.error("Cant Load IslandCore Configuration, IslandState file not found. FileNotFoundException");
		} catch (JsonSyntaxException e) {
			IslandCore.LOGGER.error("Cant Load IslandCore Configuration, IslandCore Configuration file is corrupted.");
		} catch (IOException e) {
			IslandCore.LOGGER.error("Cant Load IslandCore Configuration, IslandCore Configuration file not found. IOException");
		}
	}

	public void saveConfiguration() {
		if(!IslandCore.ConfigurationDirectory.exists() && !IslandCore.ConfigurationDirectory.mkdirs()) {
			IslandCore.LOGGER.info("Couldn't Create Config Directory for IslandCore Configuration whilst saving.");
			return;
		}

		try(BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE))) {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			gson.toJson(this, writer);

		} catch (IOException e) {
			IslandCore.LOGGER.error("Cant Save IslandCore Configuration, IslandCore Configuration file not found.");
		}
	}

	public int getIslandDistance() {
		return islandDistance;
	}

	public void setIslandDistance(int islandDistance) {
		this.islandDistance = islandDistance;
	}

	public SerializableDateTime getServerStartTime() {
		return serverStartTime;
	}

	public void setServerStartTime(SerializableDateTime serverStartTime) {
		this.serverStartTime = serverStartTime;
	}

	public int getMaxPlayerPerIsland() {
		return maxPlayerPerIsland;
	}

	public void setMaxPlayerPerIsland(int maxPlayerPerIsland) {
		this.maxPlayerPerIsland = maxPlayerPerIsland;
	}

	public boolean getReloadSpawnStructure() {
		return reloadSpawnStructure;
	}

	public void setReloadSpawnStructure(boolean reloadSpawnStructure) {
		this.reloadSpawnStructure = reloadSpawnStructure;
	}
}
