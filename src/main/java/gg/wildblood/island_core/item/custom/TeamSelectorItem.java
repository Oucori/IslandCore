package gg.wildblood.island_core.item.custom;

import gg.wildblood.island_core.IslandCore;
import gg.wildblood.island_core.multiplayer.IslandCoreNetworkingConstants;
import gg.wildblood.island_core.screen.TeamSelectionScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class TeamSelectorItem extends Item {

	public TeamSelectorItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

		if(user.isServer()) {
			//ClientPlayNetworking.send(IslandCoreNetworkingConstants.SHOW_TEAM_SELECTION_SCREEN, PacketByteBufs.empty());
			//ServerPlayNetworking.send((ServerPlayerEntity) user, IslandCoreNetworkingConstants.SHOW_TEAM_SELECTION_SCREEN, PacketByteBufs.empty());
			MinecraftClient.getInstance().setScreen(new TeamSelectionScreen());
		}

		return TypedActionResult.pass(user.getStackInHand(hand));
	}

	public static void ShowTeamSelectionScreen(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		client.execute(() -> {
			IslandCore.LOGGER.info("Clicked on Client");
			client.setScreen(new TeamSelectionScreen());
		});
	}
}
