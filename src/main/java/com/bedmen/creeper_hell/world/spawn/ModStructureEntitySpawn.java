package com.bedmen.creeper_hell.world.spawn;

import com.bedmen.creeper_hell.CreeperHell;
import com.bedmen.creeper_hell.registry.EntityTypeRegistry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreeperHell.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModStructureEntitySpawn {

    public static MobSpawnSettings.SpawnerData WITHER_CREEPER_SPAWNER;

    public static void registerSpawnerData() {
        WITHER_CREEPER_SPAWNER = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.WITHER_CREEPER.get(), 100, 5, 5);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void spawnMobs(StructureSpawnListGatherEvent event){

        if(event.getStructure() == StructureFeature.NETHER_BRIDGE){
            event.addEntitySpawn(MobCategory.MONSTER,WITHER_CREEPER_SPAWNER);
        }
    }

}