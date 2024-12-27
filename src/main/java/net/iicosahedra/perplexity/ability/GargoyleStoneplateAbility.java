package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.client.particle.ShockwaveEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class GargoyleStoneplateAbility extends ActiveAbility{
    public GargoyleStoneplateAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        if (player.getData(Registration.GARGOYLE_COOLDOWN) == 0) {
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
            player.setData(Registration.GARGOYLE_COOLDOWN.value(), (int)(60*20* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Gargoyle Stoneplate is on cooldown for "+player.getData(Registration.GARGOYLE_COOLDOWN)/20+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            player.setData(Registration.LICH_FLAG.value(), 0);
        }
    }
}
