package gg.wildblood.island_core.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import gg.wildblood.island_core.IslandCore;
import gg.wildblood.island_core.command.custom.IslandCommands;
import gg.wildblood.island_core.data.IslandCoreConfiguration;
import gg.wildblood.island_core.util.ServerUtilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

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
					.then(literal("reload")
						.executes((ctx) -> {
							IslandCoreConfiguration.getInstance().loadConfiguration();
							ctx.getSource().getPlayer().sendMessage(Text.literal("Configuration Reloaded"), false);
							return 1;
						})
					)
			);
			dispatcher.register(literal("broadcast")
				.executes(context -> {
					ServerPlayerEntity player = context.getSource().getPlayer();
					if(player == null) return 0;

					IslandCoreConfiguration configuration = IslandCoreConfiguration.getInstance();
					ZonedDateTime targetTime = configuration.getServerStartTime().toZonedDateTime();

					MinecraftServer server = player.getServer();

					if(server == null) {
						IslandCore.LOGGER.error("Server is null, can't broadcast countdown message.");
						return 0;
					}

					Timer timer = new Timer();

					// Remove this ...
					timer.scheduleAtFixedRate(new java.util.TimerTask() {
						public void run() {
							if(targetTime.isBefore(ZonedDateTime.now())) {
								timer.cancel();
								timer.purge();

								server.getPlayerManager().getPlayerList().forEach(p -> {
									p.sendSystemMessage(Text.literal("The Event starts now !"), true);
								});
								return;
							}

							ServerUtilities.sendCountDownMessage(targetTime, server);
						}
					}, 100, 1000);

					IslandCore.LOGGER.info("Broadcasting Done.");

					player.sendMessage(Text.literal("Set Countdown."), false);

					return 1;
				})
			);
		});
	}
}
