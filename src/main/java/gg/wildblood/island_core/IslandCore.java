package gg.wildblood.island_core;

import gg.wildblood.island_core.command.ModCommands;
import gg.wildblood.island_core.data.IslandCoreConfiguration;
import gg.wildblood.island_core.item.ModItems;
import gg.wildblood.island_core.multiplayer.IslandCoreNetworkingConstants;
import gg.wildblood.island_core.structure.StructureConstants;
import gg.wildblood.island_core.util.ServerUtilities;
import gg.wildblood.island_core.util.StructurePlacementUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Timer;

public class IslandCore implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Island Core");
	public static final File ConfigurationDirectory = QuiltLoader.getConfigDir().resolve("island_core").toFile();

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Hello Quilt world from {}! Stay fresh!", mod.metadata().name());

		IslandCoreConfiguration configuration = IslandCoreConfiguration.getInstance();

		ModItems.register(mod);

		ModCommands.register();

		ServerWorldLoadEvents.LOAD.register((server, world) -> {
			LOGGER.info("World loaded: {}", world.getRegistryKey().getValue());

			if (world.getRegistryKey().equals(World.OVERWORLD) && configuration.getReloadSpawnStructure()) {
				BlockPos spawnPos = world.getSpawnPos().add(-3, 0, -3);

				StructurePlacementUtil.placeStructure(world, spawnPos, StructureConstants.STRUCTURE_SKY_SPAWN);

				configuration.setReloadSpawnStructure(false);

				configuration.saveConfiguration();
			}

			Timer timer = new Timer();

			// Remove this ...
			timer.scheduleAtFixedRate(new java.util.TimerTask() {
				public void run() {
					if(configuration.getServerStartTime().toZonedDateTime().isBefore(ZonedDateTime.now())) {
						timer.cancel();
						timer.purge();

						server.getPlayerManager().getPlayerList().forEach(p -> {
							p.sendSystemMessage(Text.literal("The Event starts now !"), true);
						});
						return;
					}

					ServerUtilities.sendCountDownMessage(configuration.getServerStartTime().toZonedDateTime(), server);
				}
			}, 100, 1000);

		});
	}
}
