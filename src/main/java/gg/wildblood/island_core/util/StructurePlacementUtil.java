package gg.wildblood.island_core.util;

import gg.wildblood.island_core.IslandCore;
import gg.wildblood.island_core.structure.StructureConstants;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class StructurePlacementUtil {
	public static BlockPos placeStructure(ServerWorld world, BlockPos pos, Identifier structureIdentifier) {
		Optional<Structure> optionalTemplate = world.getStructureTemplateManager().getStructure(structureIdentifier);

		IslandCore.LOGGER.info("Template: {}", optionalTemplate.isPresent() ? "Found" : "Not Found");
		if (optionalTemplate.isPresent()) {
			Structure template = optionalTemplate.get();
			BlockPos structurePos = pos.down(); // Adjust as necessary
			StructurePlacementData placementData = new StructurePlacementData();

			template.place(world, structurePos, structurePos, placementData, world.getRandom(), 3);

			return structurePos;
		}

		return pos;
	}
}
