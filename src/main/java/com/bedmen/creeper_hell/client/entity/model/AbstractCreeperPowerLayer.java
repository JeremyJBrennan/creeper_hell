package com.bedmen.creeper_hell.client.entity.model;

import com.bedmen.creeper_hell.CreeperHell;
import com.bedmen.creeper_hell.entities.AbstractCreeper;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class AbstractCreeperPowerLayer<T extends AbstractCreeper, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public static final Map<String, ResourceLocation> LOCATION_MAP = ImmutableMap.<String, ResourceLocation>builder()
            .put("entity.creeper_hell.baby_creeper", new ResourceLocation("textures/entity/creeper/creeper_armor.png"))
            .put("entity.creeper_hell.basalt_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/nether_creeper_armor.png"))
            .put("entity.creeper_hell.desert_creeper", new ResourceLocation("textures/entity/creeper/creeper_armor.png"))
            .put("entity.creeper_hell.end_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/purple_armor.png"))
            .put("entity.creeper_hell.nether_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/nether_creeper_armor.png"))
            .put("entity.creeper_hell.regular_creeper", new ResourceLocation("textures/entity/creeper/creeper_armor.png"))
            .put("entity.creeper_hell.snow_creeper", new ResourceLocation("textures/entity/creeper/creeper_armor.png"))
            .put("entity.creeper_hell.soul_creeper", new ResourceLocation("textures/entity/creeper/creeper_armor.png"))
            .put("entity.creeper_hell.stone_creeper", new ResourceLocation("textures/entity/creeper/creeper_armor.png"))
            .put("entity.creeper_hell.stronghold_creeper", new ResourceLocation("textures/entity/creeper/creeper_armor.png"))
            .put("entity.creeper_hell.warped_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/purple_armor.png"))
            .put("entity.creeper_hell.water_creeper",new ResourceLocation("textures/entity/creeper/creeper_armor.png"))
            .put("entity.creeper_hell.wither_creeper", new ResourceLocation(CreeperHell.MOD_ID, "textures/entity/gray_armor.png"))
            .build();
    private final AbstractCreeperModel<T> model;

    public AbstractCreeperPowerLayer(RenderLayerParent<T, M> p_174471_, EntityModelSet p_174472_) {
        super(p_174471_);
        this.model = new AbstractCreeperModel<>(p_174472_.bakeLayer(ModelLayers.CREEPER_ARMOR));
    }

    public void render(PoseStack p_116970_, MultiBufferSource p_116971_, int p_116972_, T p_116973_, float p_116974_, float p_116975_, float p_116976_, float p_116977_, float p_116978_, float p_116979_) {
        if (p_116973_.isPowered()) {
            float f = (float)p_116973_.tickCount + p_116976_;
            EntityModel<T> entitymodel = this.model;
            entitymodel.prepareMobModel(p_116973_, p_116974_, p_116975_, p_116976_);
            this.getParentModel().copyPropertiesTo(entitymodel);
            VertexConsumer vertexconsumer = p_116971_.getBuffer(RenderType.energySwirl(this.getTextureLocation(p_116973_), this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
            entitymodel.setupAnim(p_116973_, p_116974_, p_116975_, p_116977_, p_116978_, p_116979_);
            entitymodel.renderToBuffer(p_116970_, vertexconsumer, p_116972_, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
        }
    }

    protected float xOffset(float p_116683_) {
        return p_116683_ * 0.01F;
    }

    public ResourceLocation getTextureLocation(T entity) {
        return LOCATION_MAP.get(entity.getType().getDescriptionId().toLowerCase());
    }
}