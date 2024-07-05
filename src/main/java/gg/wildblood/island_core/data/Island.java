package gg.wildblood.island_core.data;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Island {
	public static class PlayerReference {
		private String name;
		private UUID uuid;

		public PlayerReference(String name, UUID uuid) {
			this.name = name;
			this.uuid = uuid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public UUID getUuid() {
			return uuid;
		}

		public void setUuid(UUID uuid) {
			this.uuid = uuid;
		}
	}

	private int id;
	private BlockPos islandPosition;
	private BlockPos islandSpawnPosition;
	private List<PlayerReference> inhabitants;
	private List<PlayerReference> invitedPlayers;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BlockPos getIslandPosition() {
		return islandPosition;
	}

	public void setIslandPosition(BlockPos islandPosition) {
		this.islandPosition = islandPosition;
	}

	public BlockPos getIslandSpawnPosition() {
		return islandSpawnPosition;
	}

	public void setIslandSpawnPosition(BlockPos islandSpawnPosition) {
		this.islandSpawnPosition = islandSpawnPosition;
	}

	public List<PlayerReference> getInhabitants() {
		return inhabitants;
	}

	public void setInhabitants(List<PlayerReference> inhabitants) {
		this.inhabitants = inhabitants;
	}

	public void addInhabitant(PlayerReference inhabitant) {
		this.inhabitants.add(inhabitant);
	}

	public void addInhabitant(String name, UUID uuid) {
		this.inhabitants.add(new PlayerReference(name, uuid));
	}

	public void removeInhabitant(UUID uuid) {
		this.inhabitants.removeIf(inhabitant -> inhabitant.getUuid().equals(uuid));
	}

	public List<PlayerReference> getInvitedPlayers() {
		return invitedPlayers;
	}

	public void setInvitedPlayers(List<PlayerReference> invitedPlayers) {
		this.invitedPlayers = invitedPlayers;
	}

	public void addInvitedPlayer(PlayerReference invitedPlayer) {
		this.invitedPlayers.add(invitedPlayer);
	}

	public void addInvitedPlayer(String name, UUID uuid) {
		this.invitedPlayers.add(new PlayerReference(name, uuid));
	}

	public void removeInvitedPlayer(UUID uuid) {
		this.invitedPlayers.removeIf(invitedPlayer -> invitedPlayer.getUuid().equals(uuid));
	}

	public void removeInvitedPlayer(PlayerReference player) {
		this.invitedPlayers.removeIf(invitedPlayer -> invitedPlayer.getUuid().equals(player.getUuid()));
	}

	public void promoteInvitedPlayer(UUID uuid) {
		PlayerReference player = this.invitedPlayers.stream().filter(invitedPlayer -> invitedPlayer.getUuid().equals(uuid)).findFirst().orElse(null);
		if(player != null) {
			this.removeInvitedPlayer(player);
			this.addInhabitant(player);
		}
	}

	public Island(int id, BlockPos islandPosition, BlockPos islandSpawnPosition) {
		this.id = id;
		this.islandPosition = islandPosition;
		this.islandSpawnPosition = islandSpawnPosition;
		this.inhabitants = new ArrayList<>();
	}
}
