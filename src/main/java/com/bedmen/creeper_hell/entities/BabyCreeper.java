package com.bedmen.creeper_hell.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = PowerableMob.class
)
public class BabyCreeper extends AbstractCreeper {

    public BabyCreeper(EntityType<? extends BabyCreeper> type, Level worldIn) {
        super(type, worldIn, 18, 2, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.4D).add(Attributes.FOLLOW_RANGE, 20);
    }

    public boolean isBaby(){
        return true;
    }
}
