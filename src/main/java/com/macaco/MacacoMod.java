package com.macaco;

import com.macaco.entity.MacacoVermelhoEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacacoMod implements ModInitializer {
	public static final String MOD_ID = "macaco";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityType<MacacoVermelhoEntity> MACACO_VERMELHO = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			id("macaco_vermelho"),
			EntityType.Builder.of(MacacoVermelhoEntity::new, MobCategory.CREATURE)
					.sized(0.5F, 1.3F)
					.build(ResourceKey.create(Registries.ENTITY_TYPE, id("macaco_vermelho")))
	);

	public static final Item MACACO_SPAWN_EGG = Registry.register(
			BuiltInRegistries.ITEM,
			id("macaco_spawn_egg"),
			new SpawnEggItem(new Item.Properties()
					.setId(ResourceKey.create(Registries.ITEM, id("macaco_spawn_egg")))
					.spawnEgg(MACACO_VERMELHO))
	);

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(MACACO_VERMELHO, MacacoVermelhoEntity.createAttributes());

		SpawnPlacements.register(
				MACACO_VERMELHO,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(type, level, reason, pos, random) ->
						level.getDifficulty() != Difficulty.PEACEFUL
								&& level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK)
								&& level.getRawBrightness(pos, 0) > 8
		);

		BiomeModifications.addSpawn(
				BiomeSelectors.tag(BiomeTags.IS_JUNGLE),
				MobCategory.CREATURE,
				MACACO_VERMELHO,
				10, 1, 3
		);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS)
				.register(output -> output.accept(MACACO_SPAWN_EGG));

		LOGGER.info("Macaco Vermelho loaded!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
