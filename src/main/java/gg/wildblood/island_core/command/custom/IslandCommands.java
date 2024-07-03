package gg.wildblood.island_core.command.custom;
import com.mojang.brigadier.context.CommandContext;
import gg.wildblood.island_core.island_system.Island;
import gg.wildblood.island_core.util.IslandUtilities;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class IslandCommands {
	public static int create(CommandContext<ServerCommandSource> context) {
		ServerPlayerEntity player = context.getSource().getPlayer();

		Island newIsland = IslandUtilities.createIsland(context.getSource().getWorld());

		if(player == null) {
			context.getSource().sendFeedback(() -> Text.literal("Island created, wont set Position and Spawn because Player is null!"), false);
			return 0;
		}

		player.teleport(newIsland.getIslandSpawnPosition().getX(), newIsland.getIslandSpawnPosition().getY(), newIsland.getIslandSpawnPosition().getZ());

		player.setSpawnPoint(context.getSource().getWorld().getRegistryKey(), newIsland.getIslandSpawnPosition(), 0, true, false);

		context.getSource().sendFeedback(() -> Text.literal("Player " + player.getDisplayName().getString() + " an Island"), false);
		return 1;
	}

	public static int join(CommandContext<ServerCommandSource> context) {
		int islandId = context.getArgument("island_name", Integer.class); // This is the island id (int

		Island targetIsland = IslandUtilities.getIsland(islandId);

		if(targetIsland == null) {
			context.getSource().sendFeedback(() -> Text.literal("Island not found!"), false);
			return 0;
		}

		ServerPlayerEntity player = context.getSource().getPlayer();

		if(player == null) {
			context.getSource().sendFeedback(() -> Text.literal("Player not found!"), false);
			return 0;
		}

		player.teleport(targetIsland.getIslandSpawnPosition().getX(), targetIsland.getIslandSpawnPosition().getY(), targetIsland.getIslandSpawnPosition().getZ());

		player.setSpawnPoint(context.getSource().getWorld().getRegistryKey(), targetIsland.getIslandSpawnPosition(), 0, true, false);

		context.getSource().sendFeedback(() -> Text.literal("Joined Island with ID: " + targetIsland.getId() + "!"), false);
		return 1;
	}

	public static int leave(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("leave Island !"), false);
		return 1;
	}

	// Add Player to Team
	// Remove Player from Team
	// Remove Team
	// List Player in Team

	public static int list(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Group List!"), false);
		return 1;
	}
}
