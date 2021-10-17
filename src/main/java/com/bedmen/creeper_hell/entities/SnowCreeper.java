package com.bedmen.creeper_hell.entities;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collection;
import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = PowerableMob.class
)
public class SnowCreeper extends AbstractCreeper {
    MobEffectInstance slowEffect2 = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false,true);
    MobEffectInstance slowEffect1 = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0, false,true);


    public SnowCreeper(EntityType<? extends SnowCreeper> type, Level worldIn) {
        super(type, worldIn, 30, 3, false);

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.FOLLOW_RANGE, 20);
    }

    public void explodeCreeper() {
        if (!this.level.isClientSide) {
            Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, this.fireOnExplosion, explosion$blockinteraction);
            double d = f*4.0F;

            AABB aabb = new AABB(this.getX()-d,this.getY()-d,this.getZ()-d,this.getX()+d,this.getY()+d,this.getZ()+d);
            List<LivingEntity> livingEntityList =  this.level.getEntitiesOfClass(LivingEntity.class, aabb);

            for(LivingEntity e : livingEntityList){
                e.addEffect(this.slowEffect2);
                e.addEffect(this.slowEffect1);
            }

            this.discard();
            this.spawnLingeringCloud();
        }
    }

    @Override
    protected void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = this.getActiveEffects();
        // Add slowness to whatever MobEffects already would've been afflicted with

        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        float f = this.isPowered() ? 2.0F : 1.0F;
        areaEffectCloud.setRadius(f*7.0F);
        areaEffectCloud.setRadiusOnUse(f*-6.5F);
        areaEffectCloud.setWaitTime(10);
        areaEffectCloud.setDuration(areaEffectCloud.getDuration() / 2);
        areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float)areaEffectCloud.getDuration());

        areaEffectCloud.addEffect(this.slowEffect2);
        areaEffectCloud.addEffect(this.slowEffect1);

        for(MobEffectInstance MobEffectInstance : collection) {
            areaEffectCloud.addEffect(new MobEffectInstance(MobEffectInstance));
        }

        this.level.addFreshEntity(areaEffectCloud);

    }

    public boolean canBeAffected(MobEffectInstance p_31495_) {
        return p_31495_.getEffect() != MobEffects.MOVEMENT_SLOWDOWN && super.canBeAffected(p_31495_);
    }
}
