package net.iicosahedra.perplexity.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.Map;

public class RelicItem extends Item implements ICurioItem {

    private final Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers;
    private final Holder<MobEffect> passive;
    private final Holder<ActiveAbility> active;

    public RelicItem(Properties properties, Map<Holder<Attribute>, AttributeModifier> attributeModifiers, @Nullable Holder<MobEffect> passive, @Nullable Holder<ActiveAbility> active) {
        super(properties);
        this.attributeModifiers = ArrayListMultimap.create();
        attributeModifiers.forEach(this.attributeModifiers::put);
        this.passive = passive;
        this.active = active;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
        if(passive!= null && !slotContext.entity().hasEffect(passive)){
            slotContext.entity().addEffect(new MobEffectInstance(passive, -1,0,false, false));
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if(passive != null) {
                slotContext.entity().addEffect(new MobEffectInstance(passive, -1,0,false, false));
            }
            if(active == null){
                int slot = slotContext.index();
                if(slot == 0){
                    slotContext.entity().setData(Registration.ABILITY_SLOT_1, Registration.NO_ABILITY.unwrapKey().get().location());
                }
                if(slot == 1){
                    slotContext.entity().setData(Registration.ABILITY_SLOT_2, Registration.NO_ABILITY.unwrapKey().get().location());
                }
                if(slot == 2){
                    slotContext.entity().setData(Registration.ABILITY_SLOT_3, Registration.NO_ABILITY.unwrapKey().get().location());
                }
                if(slot == 3){
                    slotContext.entity().setData(Registration.ABILITY_SLOT_4, Registration.NO_ABILITY.unwrapKey().get().location());
                }
            }
            else if(active != null){
                int slot = slotContext.index();
                if(slot == 0){
                    slotContext.entity().setData(Registration.ABILITY_SLOT_1, active.unwrapKey().get().location());
                }
                if(slot == 1){
                    slotContext.entity().setData(Registration.ABILITY_SLOT_2, active.unwrapKey().get().location());
                }
                if(slot == 2){
                    slotContext.entity().setData(Registration.ABILITY_SLOT_3, active.unwrapKey().get().location());
                }
                if(slot == 3){
                    slotContext.entity().setData(Registration.ABILITY_SLOT_4, active.unwrapKey().get().location());
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if(passive != null) {
                slotContext.entity().removeEffect(passive);
            }
            int slot = slotContext.index();
            if(slot == 0){
                slotContext.entity().setData(Registration.ABILITY_SLOT_1, Registration.NO_ABILITY.unwrapKey().get().location());
            }
            if(slot == 1){
                slotContext.entity().setData(Registration.ABILITY_SLOT_2, Registration.NO_ABILITY.unwrapKey().get().location());
            }
            if(slot == 2){
                slotContext.entity().setData(Registration.ABILITY_SLOT_3, Registration.NO_ABILITY.unwrapKey().get().location());
            }
            if(slot == 3){
                slotContext.entity().setData(Registration.ABILITY_SLOT_4, Registration.NO_ABILITY.unwrapKey().get().location());
            }
        }
    }

    @NotNull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC.value(), 1, 1);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> instance = ICurioItem.super.getAttributeModifiers(slotContext, id, stack);
        instance.putAll(attributeModifiers);
        return instance;
    }
}
