package gg.wildblood.island_core;

import gg.wildblood.island_core.item.ModItems;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IslandCore implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Island Core");

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Hello Quilt world from {}! Stay fresh!", mod.metadata().name());

		ModItems.register(mod);
	}
}
