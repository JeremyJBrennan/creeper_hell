package com.bedmen.creeper_hell.world.spawn;

import com.bedmen.creeper_hell.CreeperHell;
import com.bedmen.creeper_hell.registry.EntityTypeRegistry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = CreeperHell.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEntitySpawn {

    public static MobSpawnSettings.SpawnerData NETHER_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData DESERT_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData SNOW_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData BABY_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData REGULAR_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData STONE_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData SOUL_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData BASALT_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData END_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData STRONGHOLD_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData WATER_CREEPER_SPAWNER;
    public static MobSpawnSettings.SpawnerData WARPED_CREEPER_SPAWNER;

    public static void registerSpawnerData() {
        NETHER_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.NETHER_CREEPER.get(), 1000, 1, 1);
        DESERT_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.DESERT_CREEPER.get(), 1000, 1, 1);
        SNOW_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SNOW_CREEPER.get(), 1000, 1, 1);
        BABY_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.BABY_CREEPER.get(), 200, 1, 1);
        REGULAR_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.REGULAR_CREEPER.get(), 800, 1, 1);
        STONE_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.STONE_CREEPER.get(), 1000, 1, 1);
        SOUL_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SOUL_CREEPER.get(), 1000, 1, 1);
        BASALT_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.BASALT_CREEPER.get(), 1000, 1, 1);
        END_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.END_CREEPER.get(), 1000, 1, 1);
        STRONGHOLD_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.STRONGHOLD_CREEPER.get(), 200, 1, 1);
        WATER_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.WATER_CREEPER.get(), 1000, 1, 1);
        WARPED_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.WARPED_CREEPER.get(), 1000, 1, 1);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void spawnMobs(BiomeLoadingEvent event){
        List<MobSpawnSettings.SpawnerData> spawns = event.getSpawns().getSpawner(MobCategory.MONSTER);

        if(event.getCategory() == Biome.BiomeCategory.NETHER){
            if(event.getName().toString().equals("minecraft:basalt_deltas")) {
                spawns.add(BASALT_CREEPER_SPAWNER);
            }
            else if(event.getName().toString().equals("minecraft:soul_sand_valley")){
                spawns.add(SOUL_CREEPER_SPAWNER);
            }
            else if(event.getName().toString().equals("minecraft:nether_wastes")){
                spawns.add(NETHER_CREEPER_SPAWNER);
            }
            else if(event.getName().toString().equals("minecraft:warped_forest")){
                spawns.add(WARPED_CREEPER_SPAWNER);
            }
        }



        else if(event.getCategory() == Biome.BiomeCategory.THEEND) {
            spawns.add(END_CREEPER_SPAWNER);
        }

        else {

            spawns.add(STONE_CREEPER_SPAWNER);
            spawns.add(STRONGHOLD_CREEPER_SPAWNER);

            if(event.getCategory() == Biome.BiomeCategory.DESERT){
                spawns.add(DESERT_CREEPER_SPAWNER);
            }

            else if(event.getCategory() == Biome.BiomeCategory.ICY || event.getClimate().temperature < 0.1F){
                spawns.add(SNOW_CREEPER_SPAWNER);
            }

            else if(event.getCategory() == Biome.BiomeCategory.OCEAN){
                spawns.add(WATER_CREEPER_SPAWNER);
            }

            else{
                spawns.add(BABY_CREEPER_SPAWNER);
                spawns.add(REGULAR_CREEPER_SPAWNER);
            }
        }

    }

}