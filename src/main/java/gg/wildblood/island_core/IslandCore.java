package gg.wildblood.island_core;

import gg.wildblood.island_core.command.ModCommands;
import gg.wildblood.island_core.item.ModItems;
import gg.wildblood.island_core.multiplayer.IslandCoreNetworkingConstants;
import gg.wildblood.island_core.structure.StructureConstants;
import gg.wildblood.island_core.util.StructurePlacementUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class IslandCore implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Island Core");

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Hello Quilt world from {}! Stay fresh!", mod.metadata().name());

		ModItems.register(mod);

		ModCommands.register();

		ServerPlayNetworking.registerGlobalReceiver(IslandCoreNetworkingConstants.SET_ISLAND_ID, (server, player, handler, buf, responseSender) -> {
			IslandCore.LOGGER.info("ServerPlayNetworking Clicked on Server");
//			if (player instanceof PlayerEntityAccessor) {
//				((PlayerEntityAccessor) player).islandCore$incrementIslandId();
//			}
		});

		ServerWorldLoadEvents.LOAD.register((server, world) -> {
			LOGGER.info("World loaded: {}", world.getRegistryKey().getValue());

			if (world.getRegistryKey().equals(World.OVERWORLD)) {
				BlockPos spawnPos = world.getSpawnPos();
				spawnPos.add(-3, 0, -3);
				StructurePlacementUtil.placeStructure(world, spawnPos, StructureConstants.STRUCTURE_SKY_SPAWN);
			}
		});
	}
}
