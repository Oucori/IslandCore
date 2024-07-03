package gg.wildblood.island_core.util;

import gg.wildblood.island_core.island_system.Island;
import gg.wildblood.island_core.island_system.IslandState;
import gg.wildblood.island_core.structure.StructureConstants;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class IslandUtilities {
	private static final int MIN_DISTANCE = 100;

	public static Island getIsland(int id) {
		for (Island island : IslandState.getInstance().getIslands()) {
			if (island.getId() == id) {
				return island;
			}
		}
		return null;
	}

	public static Island createIsland(ServerWorld world){
		// Create a new Island
		IslandState islandState = IslandState.getInstance();

		// 1. Get the next Island Spawn Position
		BlockPos newIslandPosition = calculatePosition(world.getSpawnPos(), islandState.getIslands().size());

		// 2. Create a new Sky Island
		StructurePlacementUtil.placeStructure(world, newIslandPosition, StructureConstants.STRUCTURE_SKY_ISLAND);

		// 3. Return true if the Island was created successfully
		Island newIsland = new Island(islandState.getIslands().size(), newIslandPosition, newIslandPosition.add(StructureConstants.STRUCTURE_OFFSET_MAP.get(StructureConstants.STRUCTURE_SKY_ISLAND)));

		islandState.getIslands().add(newIsland); // Add the new island to the list

		islandState.writeToStateFile(); // Save the new island state

		return newIsland;
	}

	private static BlockPos calculatePosition(BlockPos center, int count) {
		int angleIncrement = 360 / (count + 1); // Add 1 to count to avoid division by zero
		int radius = MIN_DISTANCE;

		double angle = Math.toRadians(count * angleIncrement);
		int x = (int) (center.getX() + radius * Math.cos(angle));
		int z = (int) (center.getZ() + radius * Math.sin(angle));
		BlockPos newPos = new BlockPos(x, center.getY(), z);

		// Check if the new position collides with any existing islands
		for (Island island : IslandState.getInstance().getIslands()) {
			if (isColliding(island.getIslandPosition(), newPos)) {
				// If it collides, increase the radius and calculate a new position
				radius += MIN_DISTANCE;
				x = (int) (center.getX() + radius * Math.cos(angle));
				z = (int) (center.getZ() + radius * Math.sin(angle));
				newPos = new BlockPos(x, center.getY(), z);
			}
		}

		return newPos;
	}

	private static boolean isColliding(BlockPos existingPos, BlockPos newPos) {
		return newPos.isWithinDistance(existingPos, MIN_DISTANCE) || newPos.isWithinDistance(existingPos, MIN_DISTANCE);
	}
}
