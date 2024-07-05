package gg.wildblood.island_core.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class TeamSelectionScreen extends Screen {
	public TeamSelectionScreen() {
		super(Text.literal("Team Selection"));
	}

	public ButtonWidget button1;
	public ButtonWidget button2;

	@Override
	protected void init() {
		button1 = ButtonWidget.builder(Text.literal("Increment Player Island ID"), button -> {
				PlayerEntity playerEntity = MinecraftClient.getInstance().player;
				if (playerEntity == null) return;
//				if(playerEntity instanceof PlayerEntityAccessor) {
//					((PlayerEntityAccessor) playerEntity).islandCore$incrementIslandId();
//					ClientPlayNetworking.send(IslandCoreNetworkingConstants.SET_ISLAND_ID, PacketByteBufs.empty());
//
//
//					System.out.println("Player Island ID: " + ((PlayerEntityAccessor) playerEntity).islandCore$getIslandId());
//				}
			})
			.positionAndSize(width / 2 - 205, 50, 200, 20)
			.tooltip(Tooltip.create(Text.literal("Tooltip of button1")))
			.build();
		button2 = ButtonWidget.builder(Text.literal("Get Player Island ID"), button -> {
				PlayerEntity playerEntity = MinecraftClient.getInstance().player;
				if (playerEntity == null) return;
//				if(playerEntity instanceof PlayerEntityAccessor) {
//					int islandId = ((PlayerEntityAccessor) playerEntity).islandCore$getIslandId();
//
//					System.out.println("Player Island ID: " + islandId);
//				}
			})
			.positionAndSize(width / 2 + 5, 50, 200, 20)
			.tooltip(Tooltip.create(Text.literal("Tooltip of button2")))
			.build();

		addDrawableChild(button1);
		addDrawableChild(button2);
	}
}
