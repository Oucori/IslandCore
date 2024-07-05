package gg.wildblood.island_core.util;

import gg.wildblood.island_core.IslandCore;
import gg.wildblood.island_core.data.IslandCoreConfiguration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ServerUtilities {
	public static void sendCountDownMessage(ZonedDateTime timeTillEnd) {
		MinecraftServer server = MinecraftClient.getInstance().getServer();
		sendCountDownMessage(timeTillEnd, server);
	}

	public static void sendCountDownMessage(ZonedDateTime timeTillEnd, MinecraftServer server) {
		Style textStyle = Style.EMPTY
			.withBold(true)
			.withColor(TextColor.parse("dark_gray"))
			.withFont(new Identifier("minecraft", "uniform"));


		ZonedDateTime dateTime = ZonedDateTime.now();

		ZonedDateTime diffTime = timeTillEnd.minusHours(dateTime.getHour()).minusMinutes(dateTime.getMinute() - 1).minusSeconds(dateTime.getSecond());

		String time = diffTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

		if(server == null) {
			IslandCore.LOGGER.error("Server is null, can't send countdown message.");
			return;
		}

		server.getPlayerManager().getPlayerList().forEach(player -> {
			player.sendSystemMessage(Text.literal("Server will open in: " + time + " Hours").setStyle(textStyle), true);
		});
	}
}
