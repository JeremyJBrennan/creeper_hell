package com.bedmen.creeper_hell.entities;

import javax.annotation.Nullable;

import com.bedmen.creeper_hell.entities.ai.AbstractCreeperSwellGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Collection;
import java.util.Random;

public abstract class AbstractCreeper extends PathfinderMob implements PowerableMob, Enemy {
    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(AbstractCreeper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(AbstractCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(AbstractCreeper.class, EntityDataSerializers.BOOLEAN);
    private int oldSwell;
    protected int swell;
    protected int maxSwell;
    protected int explosionRadius;
    private int droppedSkulls;
    protected boolean fireOnExplosion;

    public AbstractCreeper(EntityType<? extends AbstractCreeper> p_32278_, Level p_32279_, int fuseTime,
                           int explosionRadius, boolean fireOnExplosion) {
        super(p_32278_, p_32279_);
        this.maxSwell = fuseTime;
        this.explosionRadius = explosionRadius;
        this.fireOnExplosion = fireOnExplosion;
        this.xpReward = 5;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AbstractCreeperSwellGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public int getMaxFallDistance() {
        return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    public boolean causeFallDamage(float p_149687_, float p_149688_, DamageSource p_149689_) {
        boolean flag = super.causeFallDamage(p_149687_, p_149688_, p_149689_);
        this.swell = (int)((float)this.swell + p_149687_ * 1.5F);
        if (this.swell > this.maxSwell - 5) {
            this.swell = this.maxSwell - 5;
        }

        return flag;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SWELL_DIR, -1);
        this.entityData.define(DATA_IS_POWERED, false);
        this.entityData.define(DATA_IS_IGNITED, false);
    }

    public void addAdditionalSaveData(CompoundTag p_32304_) {
        super.addAdditionalSaveData(p_32304_);
        if (this.entityData.get(DATA_IS_POWERED)) {
            p_32304_.putBoolean("powered", true);
        }

        p_32304_.putShort("Fuse", (short)this.maxSwell);
        p_32304_.putByte("ExplosionRadius", (byte)this.explosionRadius);
        p_32304_.putBoolean("ignited", this.isIgnited());
    }

    public void readAdditionalSaveData(CompoundTag p_32296_) {
        super.readAdditionalSaveData(p_32296_);
        this.entityData.set(DATA_IS_POWERED, p_32296_.getBoolean("powered"));
        if (p_32296_.contains("Fuse", 99)) {
            this.maxSwell = p_32296_.getShort("Fuse");
        }

        if (p_32296_.contains("ExplosionRadius", 99)) {
            this.explosionRadius = p_32296_.getByte("ExplosionRadius");
        }

        if (p_32296_.getBoolean("ignited")) {
            this.ignite();
        }

    }

    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explodeCreeper();
            }
        }

        super.tick();
    }

    public void setTarget(@Nullable LivingEntity p_149691_) {
        if (!(p_149691_ instanceof Goat)) {
            super.setTarget(p_149691_);
        }
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getHurtSound(DamageSource p_32309_) {
        return SoundEvents.CREEPER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CREEPER_DEATH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.HOSTILE_SWIM;
    }

    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.HOSTILE_SPLASH;
    }

    protected SoundEvent getFallDamageSound(int p_33041_) {
        return p_33041_ > 4 ? SoundEvents.HOSTILE_BIG_FALL : SoundEvents.HOSTILE_SMALL_FALL;
    }

    protected boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    public boolean doHurtTarget(Entity p_32281_) {
        return true;
    }

    public boolean isPowered() {
        return this.entityData.get(DATA_IS_POWERED);
    }

    public float getSwelling(float p_32321_) {
        return Mth.lerp(p_32321_, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }

    public int getSwellDir() {
        return this.entityData.get(DATA_SWELL_DIR);
    }

    public void setSwellDir(int p_32284_) {
        this.entityData.set(DATA_SWELL_DIR, p_32284_);
    }

    public void thunderHit(ServerLevel p_32286_, LightningBolt p_32287_) {
        super.thunderHit(p_32286_, p_32287_);
        this.entityData.set(DATA_IS_POWERED, true);
    }

    protected InteractionResult mobInteract(Player p_32301_, InteractionHand p_32302_) {
        ItemStack itemstack = p_32301_.getItemInHand(p_32302_);
        if (itemstack.is(Items.FLINT_AND_STEEL)) {
            this.level.playSound(p_32301_, this.getX(), this.getY(), this.getZ(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level.isClientSide) {
                this.ignite();
                itemstack.hurtAndBreak(1, p_32301_, (p_32290_) -> {
                    p_32290_.broadcastBreakEvent(p_32302_);
                });
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(p_32301_, p_32302_);
        }
    }

    public void explodeCreeper() {
        if (!this.level.isClientSide) {
            Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, this.fireOnExplosion, explosion$blockinteraction);
            this.discard();
            this.spawnLingeringCloud();
        }
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    protected void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = this.getActiveEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
            areaeffectcloud.setRadius(2.5F);
            areaeffectcloud.setRadiusOnUse(-0.5F);
            areaeffectcloud.setWaitTime(10);
            areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());

            for(MobEffectInstance MobEffectInstance : collection) {
                areaeffectcloud.addEffect(new MobEffectInstance(MobEffectInstance));
            }

            this.level.addFreshEntity(areaeffectcloud);
        }

    }

    public boolean isIgnited() {
        return this.entityData.get(DATA_IS_IGNITED);
    }

    public void ignite() {
        this.entityData.set(DATA_IS_IGNITED, true);
    }

    public boolean canDropMobsSkull() {
        return this.isPowered() && this.droppedSkulls < 1;
    }

    public void increaseDroppedSkulls() {
        ++this.droppedSkulls;
    }

    public static boolean spawnPredicate(EntityType<? extends AbstractCreeper> type, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos pos, Random random) {
        return serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(type, serverLevelAccessor, mobSpawnType, pos, random);
    }

    public static boolean spawnSurfacePredicate(EntityType<? extends AbstractCreeper> type, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos pos, Random random) {
        return spawnPredicate(type, serverLevelAccessor, mobSpawnType, pos, random) && pos.getY() >= 56;
    }

    public static boolean spawnUndergroundPredicate(EntityType<? extends AbstractCreeper> type, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos pos, Random random) {
        return spawnPredicate(type, serverLevelAccessor, mobSpawnType, pos, random) && pos.getY() < 56;
    }

    public static boolean checkMonsterSpawnRules(EntityType<? extends Monster> type, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos pos, Random random) {
        return serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(type, serverLevelAccessor, mobSpawnType, pos, random);
    }
}
