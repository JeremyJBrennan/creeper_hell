package com.bedmen.creeper_hell.client.entity.render;

import com.bedmen.creeper_hell.CreeperHell;
import com.bedmen.creeper_hell.client.entity.model.AbstractCreeperModel;
import com.bedmen.creeper_hell.client.entity.model.AbstractCreeperPowerLayer;
import com.bedmen.creeper_hell.entities.AbstractCreeper;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class AbstractCreeperRenderer<T extends AbstractCreeper> extends MobRenderer<T, AbstractCreeperModel<T>> {
    public static final Map<String, ResourceLocation> LOCATION_MAP = ImmutableMap.<String, ResourceLocation>builder()
            .put("entity.creeper_hell.baby_creeper", new ResourceLocation("textures/entity/creeper/creeper.png"))
            .put("entity.creeper_hell.basalt_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/basalt_creeper.png"))
            .put("entity.creeper_hell.desert_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/desert_creeper.png"))
            .put("entity.creeper_hell.end_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/end_creeper.png"))
            .put("entity.creeper_hell.nether_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/nether_creeper.png"))
            .put("entity.creeper_hell.regular_creeper", new ResourceLocation("textures/entity/creeper/creeper.png"))
            .put("entity.creeper_hell.snow_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/snow_creeper.png"))
            .put("entity.creeper_hell.soul_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/soul_creeper.png"))
            .put("entity.creeper_hell.stone_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/stone_creeper.png"))
            .put("entity.creeper_hell.stronghold_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/stronghold_creeper.png"))
            .put("entity.creeper_hell.warped_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/warped_creeper.png"))
            .put("entity.creeper_hell.water_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/water_creeper.png"))
            .put("entity.creeper_hell.wither_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/wither_creeper.png"))
            .build();

    public AbstractCreeperRenderer(EntityRendererProvider.Context context) {
        super(context, new AbstractCreeperModel<>(context.bakeLayer(ModelLayers.CREEPER)), 0.5F);
        this.addLayer(new AbstractCreeperPowerLayer<>(this, context.getModelSet()));
    }

    protected void scale(T p_114046_, PoseStack p_114047_, float p_114048_) {
        float f = p_114046_.getSwelling(p_114048_);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        p_114047_.scale(f2, f3, f2);
    }

    protected float getWhiteOverlayProgress(T p_114043_, float p_114044_) {
        float f = p_114043_.getSwelling(p_114044_);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    public ResourceLocation getTextureLocation(T entity) {
        return LOCATION_MAP.get(entity.getType().getDescriptionId().toLowerCase());
    }
}