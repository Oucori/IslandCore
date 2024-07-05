package gg.wildblood.island_core.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import gg.wildblood.island_core.command.custom.IslandCommands;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;

public class ModCommands {
	public static void register() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(
				literal("island")
					.then(literal("create")
						.executes(IslandCommands::create)
					)
					.then(literal("join")
						.then(argument("island_name", IntegerArgumentType.integer(0))
							.executes(IslandCommands::join)
						)
					)
					.then(literal("home")
						.executes(IslandCommands::home)
					)
					.then(literal("leave")
						.executes(IslandCommands::leave)
					)
					.then(literal("list")
						.executes(IslandCommands::list)
					)
			);
		});
	}
}
