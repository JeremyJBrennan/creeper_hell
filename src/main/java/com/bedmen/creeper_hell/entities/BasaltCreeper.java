package com.bedmen.creeper_hell.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = PowerableMob.class
)
public class BasaltCreeper extends AbstractCreeper {

    public BasaltCreeper(EntityType<? extends BasaltCreeper> type, Level worldIn) {
        super(type, worldIn, 30, 4, true);
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
            int l = (int)f * 2 + 1;

            for(int i = -1*l; i <= l; i++){
                for(int j = -1*l; j <= l; j++){
                    for(int k = -1*l; k <= l; k++){
                        if(Math.sqrt(i*i+j*j+k*k) < 1.5F*f){
                            BlockPos blockPos = new BlockPos(this.position().add(i,j,k));
                            if(this.level.getBlockState(blockPos).getBlock().getExplosionResistance() < 600.0f){
                                this.level.setBlock(blockPos, Blocks.LAVA.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
            this.discard();
            this.spawnLingeringCloud();
        }
    }
}
