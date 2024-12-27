package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Map;

public class DuskbladeOfDraktharrItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> duskbladeModifiers = Map.of(
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.dbd.ad"), 5, AttributeModifier.Operation.ADD_VALUE),
            Registration.LETHALITY, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.dbd.lethality"), 20, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.dbd.ability_haste"), 25, AttributeModifier.Operation.ADD_VALUE)
    );

    public DuskbladeOfDraktharrItem() {
        super(new Item.Properties().stacksTo(1), duskbladeModifiers, Registration.DUSKBLADE_EFFECT, null);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if(!slotContext.entity().level().isClientSide()) {
            slotContext.entity().setData(Registration.DUSKBLADE_IMMUNITY, 0);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (slotContext.entity().getData(Registration.DUSKBLADE_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.DUSKBLADE_COOLDOWN,
                        slotContext.entity().getData(Registration.DUSKBLADE_COOLDOWN.value()) - 1);
            }
            if (slotContext.entity().getData(Registration.DUSKBLADE_COOLDOWN.value()) == 0) {
                slotContext.entity().setData(Registration.DUSKBLADE_IMMUNITY, 0);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.duskblade"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
