package gg.wildblood.island_core.mixin;

import gg.wildblood.island_core.IslandCore;
import gg.wildblood.island_core.PlayerEntityAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityAccessor {

	@Unique
	public int islandId = 0;

	@Override
	public int islandCore$getIslandId() {
		return islandId;
	}

	@Override
	public void islandCore$setIslandId(int islandId) {
		this.islandId = islandId;
	}

	@Override
	public void islandCore$incrementIslandId() {
		this.islandId++;
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.islandId = nbt.getInt("IslandId");
		IslandCore.LOGGER.info("{ readCustomDataFromNbt } Island ID: {}", islandId);
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
	public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putInt("IslandId", islandId);
		IslandCore.LOGGER.info("{ writeCustomDataToNbt } Island ID: {}", islandId);
	}
}
