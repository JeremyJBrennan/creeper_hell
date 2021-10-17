package com.bedmen.creeper_hell.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = PowerableMob.class
)
public class StrongholdCreeper extends AbstractCreeper {

    public StrongholdCreeper(EntityType<? extends StrongholdCreeper> type, Level worldIn) {
        super(type, worldIn, 30, 4, false);
        this.xpReward = 20;
    }

    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.FOLLOW_RANGE, 20).add(Attributes.MAX_HEALTH, 40.0d);
    }

    public void tick() {
        super.tick();
        if (this.swell >= this.maxSwell) {
            this.swell = 0;
        }
    }

    public void explodeCreeper() {
        if (!this.level.isClientSide) {
            Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, this.fireOnExplosion, explosion$blockinteraction);
            this.setDeltaMovement(this.getDeltaMovement().add(this.random.nextFloat()*2-1,this.random.nextFloat(),this.random.nextFloat()*2-1));
        }
    }

    public static boolean spawnStrongholdPredicate(EntityType<? extends StrongholdCreeper> type, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos pos, Random random) {
        BlockState blockstate = serverLevelAccessor.getBlockState(pos.below());
        return AbstractCreeper.spawnPredicate(type, serverLevelAccessor, mobSpawnType, pos, random) && (blockstate.is(Blocks.STONE_BRICKS) || blockstate.is(Blocks.CRACKED_STONE_BRICKS) || blockstate.is(Blocks.MOSSY_STONE_BRICKS)) && (pos.getY() <= 56);
    }

    public boolean hurt(DamageSource damageSource, float amount) {
        if(damageSource.isExplosion()){
            return false;
        }
        return super.hurt(damageSource, amount);
    }
}
