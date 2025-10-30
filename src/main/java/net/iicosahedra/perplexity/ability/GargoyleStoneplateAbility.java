package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.client.particle.ShockwaveEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.item.GargoyleStoneplateItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class GargoyleStoneplateAbility extends ActiveAbility{
    public GargoyleStoneplateAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        var stackOpt = CuriosUtil.findFirstEquipped(player, GargoyleStoneplateItem.class);
        if (stackOpt.isPresent() && ItemData.getCooldown(stackOpt.get()) == 0) {
            float shieldAmount = player.getMaxHealth();
            player.getAttribute(Attributes.MAX_ABSORPTION).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.gargoyle.am"), shieldAmount, AttributeModifier.Operation.ADD_VALUE));
            player.setAbsorptionAmount(player.getAbsorptionAmount() + shieldAmount);
            TickScheduler.schedule(100, () ->{
                player.setAbsorptionAmount(player.getAbsorptionAmount() - shieldAmount);
                if(Objects.requireNonNull(player.getAttribute(Attributes.MAX_ABSORPTION)).hasModifier(ResourceLoc.create("attribute.perplexity.gargoyle.am"))){
                    player.getAttribute(Attributes.MAX_ABSORPTION).removeModifier(ResourceLoc.create("attribute.perplexity.gargoyle.am"));
                }
            });
            ShockwaveEffect.createShockwaveParticles(player, 1, 0xfff261, 2f);
            ItemData.setCooldown(stackOpt.get(), (int)(60*20* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Gargoyle Stoneplate is on cooldown for "+(ItemData.getCooldown(stackOpt.orElse(ItemStack.EMPTY))/20)+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            net.iicosahedra.perplexity.util.CuriosUtil.findFirstEquipped(player, net.iicosahedra.perplexity.item.LichBaneItem.class)
                    .ifPresent(s -> net.iicosahedra.perplexity.util.ItemData.setFlag(s, 0));
        }
    }
}
