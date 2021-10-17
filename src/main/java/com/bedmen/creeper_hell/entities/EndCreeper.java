package com.bedmen.creeper_hell.entities;

import net.minecraft.world.damagesource.DamageSource;
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

import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = PowerableMob.class
)
public class EndCreeper extends AbstractCreeper {
    public EndCreeper(EntityType<? extends EndCreeper> type, Level worldIn) {
        super(type, worldIn, 20, 1, false);
    }

    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
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
                e.setDeltaMovement(e.getDeltaMovement().add(0,4,0));
            }

            this.discard();
            this.spawnLingeringCloud();
        }
    }
}
