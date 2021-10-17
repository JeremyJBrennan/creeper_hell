package com.bedmen.creeper_hell.entities;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = PowerableMob.class
)
public class StoneCreeper extends AbstractCreeper {

    public StoneCreeper(EntityType<? extends StoneCreeper> type, Level worldIn) {
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

            float numSilverFish = f * 3.0f;
            for(int i = 0; i < (int)(numSilverFish); i++){
                Silverfish silverfish = EntityType.SILVERFISH.create(this.level);
                silverfish.setPos(this.getX() + Mth.cos(i*(float)(2.0d*Math.PI)/(numSilverFish)), this.getY(), this.getZ() + Mth.sin(i*(float)(2.0d*Math.PI)/(numSilverFish)));
                silverfish.setXRot(0.0f);
                silverfish.setYRot(this.getYRot());
                this.level.addFreshEntity(silverfish);
            }

            this.discard();
            this.spawnLingeringCloud();
        }
    }
}
