package gg.wildblood.island_core.command.custom;
import com.mojang.brigadier.context.CommandContext;
import gg.wildblood.island_core.island_system.Island;
import gg.wildblood.island_core.util.IslandUtilities;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class IslandCommands {
	public static int create(CommandContext<ServerCommandSource> context) {
		ServerPlayerEntity player = context.getSource().getPlayer();

		if(player == null) {
			context.getSource().sendFeedback(() -> Text.literal("You are not a Player!"), false);
			return 0;
		}

		Island playerIsland = IslandUtilities.getIsland(player);

		if(playerIsland != null) {
			context.getSource().sendFeedback(() -> Text.literal("You already have a Island!"), false);
			return 0;
		}

		// Send Animation to Player to swoosh them to the Island

		Island newIsland = IslandUtilities.createIsland(context.getSource().getWorld());

		newIsland.addInhabitant(player.getName().getString(), player.getUuid());

		IslandUtilities.saveState();

		IslandUtilities.teleportTo(player, newIsland, true);

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

		Island playerIsland = IslandUtilities.getIsland(player);

		if(playerIsland != null) IslandUtilities.removeInhabitant(player, playerIsland);

		IslandUtilities.addInhabitant(player, targetIsland);

		IslandUtilities.teleportTo(player, targetIsland, true);

		IslandUtilities.saveState();

		context.getSource().sendFeedback(() -> Text.literal("Joined Island with ID: " + targetIsland.getId() + "!"), false);
		return 1;
	}

	public static int leave(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("leave Island !"), false);
		return 1;
	}

	// Add Player to Team
	// Remove Player from Team
	// List Player in Team

	public static int home(CommandContext<ServerCommandSource> context) {
		ServerPlayerEntity player = context.getSource().getPlayer();

		if(player == null) {
			context.getSource().sendFeedback(() -> Text.literal("You are not a Player!"), false);
			return 0;
		}

		Island playerIsland = IslandUtilities.getIsland(player);

		if(playerIsland == null) {
			context.getSource().sendFeedback(() -> Text.literal("You do not have an Island!"), false);
			return 0;
		}

		IslandUtilities.teleportTo(player, playerIsland);

		context.getSource().sendFeedback(() -> Text.literal("Back at Home!"), false);
		return 1;
	}

	public static int list(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Island List:"), false);

		IslandUtilities.getIslands().forEach(island -> {
			context.getSource().sendFeedback(() -> Text.literal(island.getId() + "."), false);
			island.getInhabitants().forEach((inhabitant) -> {
				context.getSource().sendFeedback(() -> Text.literal("  " + inhabitant.getName()), false);
			});
		});

		return 1;
	}
}
