package gg.wildblood.island_core.island_system;

import net.minecraft.util.math.BlockPos;

public class Island {
	private int id;
	private BlockPos islandPosition;
	private BlockPos islandSpawnPosition;

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

	public Island(int id, BlockPos islandPosition, BlockPos islandSpawnPosition) {
		this.id = id;
		this.islandPosition = islandPosition;
		this.islandSpawnPosition = islandSpawnPosition;
	}
}
