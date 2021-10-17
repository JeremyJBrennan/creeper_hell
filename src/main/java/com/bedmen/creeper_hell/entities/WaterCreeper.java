package com.bedmen.creeper_hell.entities;

import java.util.List;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;

public class WaterCreeper extends AbstractCreeper {
    private static final MobEffectInstance WATER_SPEED = new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 999999, 0, true, false, false);
    private static final MobEffectInstance DRY_SLOW = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 4, true, false, false);

    public WaterCreeper(EntityType<? extends WaterCreeper> type, Level worldIn) {
        super(type, worldIn, 30, 3, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.75D).add(Attributes.FOLLOW_RANGE, 20);
    }

    public void explodeCreeper() {
        if (!this.level.isClientSide) {
            Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, explosion$blockinteraction);
            double d = f*4.0F;

            AABB aabb = new AABB(this.getX()-d,this.getY()-d,this.getZ()-d,this.getX()+d,this.getY()+d,this.getZ()+d);
            List<LivingEntity> livingEntityList =  this.level.getEntitiesOfClass(LivingEntity.class, aabb);
            for(LivingEntity e : livingEntityList){
                e.setDeltaMovement(e.getDeltaMovement().add(0,-4,0));
                e.setAirSupply(0);
            }

            this.discard();
        }

    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean checkSpawnObstruction(LevelReader p_30348_) {
        return p_30348_.isUnobstructed(this);
    }

    protected void handleAirSupply(int p_30344_) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(p_30344_ - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(i);
        if(!this.hasEffect(MobEffects.DOLPHINS_GRACE)){
            this.addEffect(WATER_SPEED);
        }
        if(!this.isInWater()){
            this.addEffect(DRY_SLOW);
        }
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public static boolean spawnOceanPredicate(EntityType<? extends AbstractCreeper> type, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos pos, Random random) {
        return serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && (mobSpawnType == MobSpawnType.SPAWNER || serverLevelAccessor.getFluidState(pos).is(FluidTags.WATER));
    }
}
