package com.bedmen.creeper_hell;

import com.bedmen.creeper_hell.client.entity.render.AbstractCreeperRenderer;
import com.bedmen.creeper_hell.entities.*;
import com.bedmen.creeper_hell.items.ModSpawnEggItem;
import com.bedmen.creeper_hell.registry.EntityTypeRegistry;
import com.bedmen.creeper_hell.registry.ItemRegistry;
import com.bedmen.creeper_hell.world.spawn.ModEntitySpawn;
import com.bedmen.creeper_hell.world.spawn.ModStructureEntitySpawn;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod("creeper_hell")
@Mod.EventBusSubscriber(modid = CreeperHell.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreeperHell
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "creeper_hell";

    public CreeperHell() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        EntityTypeRegistry.init();
        ItemRegistry.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        SpawnPlacements.register(EntityTypeRegistry.NETHER_CREEPER .get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnPredicate);
        SpawnPlacements.register(EntityTypeRegistry.DESERT_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnSurfacePredicate);
        SpawnPlacements.register(EntityTypeRegistry.SNOW_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnSurfacePredicate);
        SpawnPlacements.register(EntityTypeRegistry.REGULAR_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnSurfacePredicate);
        SpawnPlacements.register(EntityTypeRegistry.BABY_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnSurfacePredicate);
        SpawnPlacements.register(EntityTypeRegistry.STONE_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnUndergroundPredicate);
        SpawnPlacements.register(EntityTypeRegistry.SOUL_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnPredicate);
        SpawnPlacements.register(EntityTypeRegistry.BASALT_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnPredicate);
        SpawnPlacements.register(EntityTypeRegistry.WITHER_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnPredicate);
        SpawnPlacements.register(EntityTypeRegistry.END_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnPredicate);
        SpawnPlacements.register(EntityTypeRegistry.STRONGHOLD_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StrongholdCreeper::spawnStrongholdPredicate);
        SpawnPlacements.register(EntityTypeRegistry.WATER_CREEPER.get(),SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterCreeper::spawnOceanPredicate);
        SpawnPlacements.register(EntityTypeRegistry.WARPED_CREEPER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractCreeper::spawnPredicate);

        ModEntitySpawn.registerSpawnerData();
        ModStructureEntitySpawn.registerSpawnerData();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        EntityRenderers.register(EntityTypeRegistry.NETHER_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.DESERT_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SNOW_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.REGULAR_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.BABY_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.STONE_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SOUL_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.BASALT_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.WITHER_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.END_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.STRONGHOLD_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.WATER_CREEPER.get(), AbstractCreeperRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.WARPED_CREEPER.get(), AbstractCreeperRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event){
        ModSpawnEggItem.initSpawnEggs();
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(final EntityAttributeCreationEvent event){
        event.put(EntityTypeRegistry.NETHER_CREEPER.get(), NetherCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.DESERT_CREEPER.get(), DesertCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.SNOW_CREEPER.get(), SnowCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.REGULAR_CREEPER.get(), RegularCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.BABY_CREEPER.get(), BabyCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.STONE_CREEPER.get(), StoneCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.SOUL_CREEPER.get(), SoulCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.BASALT_CREEPER.get(), BasaltCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.WITHER_CREEPER.get(), WitherCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.END_CREEPER.get(), EndCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.STRONGHOLD_CREEPER.get(), StrongholdCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.WATER_CREEPER.get(), WaterCreeper.createAttributes().build());
        event.put(EntityTypeRegistry.WARPED_CREEPER.get(), WarpedCreeper.createAttributes().build());
    }
}
