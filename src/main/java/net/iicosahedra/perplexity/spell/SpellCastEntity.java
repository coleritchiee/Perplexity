package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.event.SpellCastEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class SpellCastEntity extends Entity {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellCastEntity.class);
    private LivingEntity caster;
    private int durationTicks;

    ItemStack stack;
    private SpellCasting casting;
    private int tickCounter;
    private SpellCastEvent event;

    public SpellCastEntity(EntityType<? extends SpellCastEntity> type, Level world) {
        super(type, world);
    }

    public SpellCastEntity(Level world, LivingEntity caster, SpellCastEvent event, SpellCasting casting, ItemStack stack) {
        this(Registration.SPELL_CAST_ENTITY.get(), world);
        this.caster = caster;
        this.event = event;
        this.durationTicks = event.getContext().getSpell().getDelay() * 20;
        this.casting = casting;
        this.stack = stack;
        this.setPos(caster.getX(), caster.getY() + caster.getEyeHeight() + 1.0, caster.getZ());
        LOGGER.debug("SpellCastEntity created with durationTicks: {}", this.durationTicks);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326003_) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            tickCounter++;
            LOGGER.debug("Tick: {}, TickCounter: {}, DurationTicks: {}", this.tickCount, this.tickCounter, this.durationTicks);
            if (tickCounter >= durationTicks) {
                removeOnSuccess();
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
    }

    public void removeOnSuccess() {
        LOGGER.debug("Spell cast success. Calling onCast.");
        casting.onCast(stack,level());
        this.discard();
    }

    public void removeOnCancel() {
        LOGGER.debug("Spell cast canceled.");
        this.event.setCanceled(true);
        if (this.caster instanceof Player) {
            this.caster.sendSystemMessage(Component.translatable("Spell was interrupted"));
        }
        this.discard();
    }

}
