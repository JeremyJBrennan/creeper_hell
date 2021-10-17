package com.bedmen.creeper_hell.registry;

import com.bedmen.creeper_hell.CreeperHell;
import com.bedmen.creeper_hell.items.ModSpawnEggItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CreeperHell.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Spawn Eggs
    public static final RegistryObject<Item> REGULAR_CREEPER_SPAWN_EGG = ITEMS.register("regular_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.REGULAR_CREEPER, 894731, 0, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BABY_CREEPER_SPAWN_EGG = ITEMS.register("baby_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.BABY_CREEPER, 0x519241, 0, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> DESERT_CREEPER_SPAWN_EGG = ITEMS.register("desert_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.DESERT_CREEPER, 0xe3dbb0, 0, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SNOW_CREEPER_SPAWN_EGG = ITEMS.register("snow_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.SNOW_CREEPER, 0xffffff, 0, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> WATER_CREEPER_SPAWN_EGG = ITEMS.register("water_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.WATER_CREEPER, 0x5e7025, 0, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> STONE_CREEPER_SPAWN_EGG = ITEMS.register("stone_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.STONE_CREEPER, 0x7f7f7f, 0, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> NETHER_CREEPER_SPAWN_EGG = ITEMS.register("nether_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.NETHER_CREEPER, 0x511515, 0xff7f27, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SOUL_CREEPER_SPAWN_EGG = ITEMS.register("soul_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.SOUL_CREEPER, 0x3d2e25, 0x34e8ee, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BASALT_CREEPER_SPAWN_EGG = ITEMS.register("basalt_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.BASALT_CREEPER, 0x3a3b48, 0xff7f27, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> WARPED_CREEPER_SPAWN_EGG = ITEMS.register("warped_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.WARPED_CREEPER, 0x167e86, 0xcc00fa, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> WITHER_CREEPER_SPAWN_EGG = ITEMS.register("wither_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.WITHER_CREEPER, 0x151515, 0xcacaca, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> STRONGHOLD_CREEPER_SPAWN_EGG = ITEMS.register("stronghold_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.STRONGHOLD_CREEPER, 0x767676, 0x65f5e3, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> END_CREEPER_SPAWN_EGG = ITEMS.register("end_creeper_spawn_egg", () -> new ModSpawnEggItem(EntityTypeRegistry.END_CREEPER, 0xdee6a4, 0xcc00fa, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
}
