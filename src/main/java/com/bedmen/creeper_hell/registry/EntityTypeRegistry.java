package com.bedmen.creeper_hell.registry;

import com.bedmen.creeper_hell.CreeperHell;
import com.bedmen.creeper_hell.entities.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeRegistry {

	public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES , CreeperHell.MOD_ID);
	 
	public static void init() {
		ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	//Entity Type
	public static final RegistryObject<EntityType<NetherCreeper>> NETHER_CREEPER = ENTITY_TYPES.register("nether_creeper", () -> EntityType.Builder.<NetherCreeper>of(NetherCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).fireImmune().clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "nether_creeper").toString()));
	public static final RegistryObject<EntityType<DesertCreeper>> DESERT_CREEPER = ENTITY_TYPES.register("desert_creeper", () -> EntityType.Builder.<DesertCreeper>of(DesertCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "desert_creeper").toString()));
	public static final RegistryObject<EntityType<SnowCreeper>> SNOW_CREEPER = ENTITY_TYPES.register("snow_creeper", () -> EntityType.Builder.<SnowCreeper>of(SnowCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "snow_creeper").toString()));
	public static final RegistryObject<EntityType<RegularCreeper>> REGULAR_CREEPER = ENTITY_TYPES.register("regular_creeper", () -> EntityType.Builder.<RegularCreeper>of(RegularCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "regular_creeper").toString()));
	public static final RegistryObject<EntityType<BabyCreeper>> BABY_CREEPER = ENTITY_TYPES.register("baby_creeper", () -> EntityType.Builder.<BabyCreeper>of(BabyCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "baby_creeper").toString()));
	public static final RegistryObject<EntityType<StoneCreeper>> STONE_CREEPER = ENTITY_TYPES.register("stone_creeper", () -> EntityType.Builder.<StoneCreeper>of(StoneCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "stone_creeper").toString()));
	public static final RegistryObject<EntityType<SoulCreeper>> SOUL_CREEPER = ENTITY_TYPES.register("soul_creeper", () -> EntityType.Builder.<SoulCreeper>of(SoulCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).fireImmune().clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "soul_creeper").toString()));
	public static final RegistryObject<EntityType<BasaltCreeper>> BASALT_CREEPER = ENTITY_TYPES.register("basalt_creeper", () -> EntityType.Builder.<BasaltCreeper>of(BasaltCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).fireImmune().clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "basalt_creeper").toString()));
	public static final RegistryObject<EntityType<WitherCreeper>> WITHER_CREEPER = ENTITY_TYPES.register("wither_creeper", () -> EntityType.Builder.<WitherCreeper>of(WitherCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).fireImmune().clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "wither_creeper").toString()));
	public static final RegistryObject<EntityType<EndCreeper>> END_CREEPER = ENTITY_TYPES.register("end_creeper", () -> EntityType.Builder.<EndCreeper>of(EndCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "end_creeper").toString()));
	public static final RegistryObject<EntityType<StrongholdCreeper>> STRONGHOLD_CREEPER = ENTITY_TYPES.register("stronghold_creeper", () -> EntityType.Builder.<StrongholdCreeper>of(StrongholdCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).fireImmune().clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "stronghold_creeper").toString()));
	public static final RegistryObject<EntityType<WaterCreeper>> WATER_CREEPER = ENTITY_TYPES.register("water_creeper", () -> EntityType.Builder.<WaterCreeper>of(WaterCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "water_creeper").toString()));
	public static final RegistryObject<EntityType<WarpedCreeper>> WARPED_CREEPER = ENTITY_TYPES.register("warped_creeper", () -> EntityType.Builder.<WarpedCreeper>of(WarpedCreeper::new, MobCategory.MONSTER).sized(0.6f,1.7f).fireImmune().clientTrackingRange(8).build(new ResourceLocation(CreeperHell.MOD_ID, "warped_creeper").toString()));
}
