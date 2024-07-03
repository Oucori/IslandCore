package gg.wildblood.island_core.structure;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.Map;

public class StructureConstants {
	public static final Identifier STRUCTURE_SKY_SPAWN = new Identifier("island_core", "sky_spawn");

	public static final Identifier STRUCTURE_SKY_ISLAND = new Identifier("island_core", "sky_island");

	public static final Map<Identifier, Vec3i> STRUCTURE_OFFSET_MAP = Map.of(
		STRUCTURE_SKY_SPAWN, new Vec3i(-2, 0, -2),
		STRUCTURE_SKY_ISLAND, new Vec3i(2, 2, 2)
	);

}
