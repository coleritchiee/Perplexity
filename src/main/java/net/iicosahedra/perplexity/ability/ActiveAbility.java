package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.effect.LichBaneEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public abstract class ActiveAbility {
    public static void useAbility(Player player, int slot){
        ActiveAbility ability = Registration.NO_ABILITY.value();
        if(slot == 1){
            ability = Registration.ABILITIES.getRegistry().get().get(player.getData(Registration.ABILITY_SLOT_1));
        }
        else if(slot == 2){
            ability = Registration.ABILITIES.getRegistry().get().get(player.getData(Registration.ABILITY_SLOT_2));

        }
        else if(slot == 3){
            ability = Registration.ABILITIES.getRegistry().get().get(player.getData(Registration.ABILITY_SLOT_3));

        }
        else if(slot == 4){
            ability = Registration.ABILITIES.getRegistry().get().get(player.getData(Registration.ABILITY_SLOT_4));

        }
        if(ability != Registration.NO_ABILITY.value()&&ability != null){
            ability.canCast(player);
        }
    }
    public int manaCost;

    public ActiveAbility(int manaCost){
        this.manaCost = manaCost;
    }

    public abstract void cast(Player player);

    public void canCast(Player player){
        if(player.getData(Registration.MANA) >= manaCost){
            player.setData(Registration.MANA, player.getData(Registration.MANA) - manaCost);
            this.cast(player);
            if(player.hasEffect(Registration.LICH_EFFECT)){
                LichBaneEffect.processLichConditions(player);
            }
        }
        else{
            player.displayClientMessage(Component.literal("Not enough mana. Current Mana: "+player.getData(Registration.MANA)+" Mana Cost: "+manaCost), true);
        }
    }
}
