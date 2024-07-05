package gg.wildblood.island_core.island_system;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Island {
	public static class Inhabitant {
		private String name;
		private UUID uuid;

		public Inhabitant(String name, UUID uuid) {
			this.name = name;
			this.uuid = uuid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public UUID getUuid() {
			return uuid;
		}

		public void setUuid(UUID uuid) {
			this.uuid = uuid;
		}
	}

	private int id;
	private BlockPos islandPosition;
	private BlockPos islandSpawnPosition;
	private List<Inhabitant> inhabitants;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BlockPos getIslandPosition() {
		return islandPosition;
	}

	public void setIslandPosition(BlockPos islandPosition) {
		this.islandPosition = islandPosition;
	}

	public BlockPos getIslandSpawnPosition() {
		return islandSpawnPosition;
	}

	public void setIslandSpawnPosition(BlockPos islandSpawnPosition) {
		this.islandSpawnPosition = islandSpawnPosition;
	}

	public List<Inhabitant> getInhabitants() {
		return inhabitants;
	}

	public void setInhabitants(List<Inhabitant> inhabitants) {
		this.inhabitants = inhabitants;
	}

	public void addInhabitant(Inhabitant inhabitant) {
		this.inhabitants.add(inhabitant);
	}

	public void addInhabitant(String name, UUID uuid) {
		this.inhabitants.add(new Inhabitant(name, uuid));
	}

	public void removeInhabitant(UUID uuid) {
		this.inhabitants.removeIf(inhabitant -> inhabitant.getUuid().equals(uuid));
	}

	public Island(int id, BlockPos islandPosition, BlockPos islandSpawnPosition) {
		this.id = id;
		this.islandPosition = islandPosition;
		this.islandSpawnPosition = islandSpawnPosition;
		this.inhabitants = new ArrayList<>();
	}
}
