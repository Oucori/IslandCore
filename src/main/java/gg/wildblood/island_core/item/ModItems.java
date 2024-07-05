package gg.wildblood.island_core.item;

import gg.wildblood.island_core.item.custom.TeamSelectorItem;
import gg.wildblood.island_core.multiplayer.IslandCoreNetworkingConstants;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class ModItems {
	public static final Item TEAM_SELECTOR = new TeamSelectorItem(new QuiltItemSettings());

	public static void register(ModContainer mod) {
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "team_selector"), TEAM_SELECTOR);
		ClientPlayNetworking.registerGlobalReceiver(IslandCoreNetworkingConstants.SHOW_TEAM_SELECTION_SCREEN, TeamSelectorItem::ShowTeamSelectionScreen);
	}
}

