package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.effect.LichBaneEffect;
import net.iicosahedra.perplexity.item.LichBaneItem;
import net.iicosahedra.perplexity.item.RelicItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public abstract class ActiveAbility {
    public static void useAbility(Player player, int slot){
        ActiveAbility ability = Registration.NO_ABILITY.value();
        int curiosIndex = Math.max(0, slot - 1);
        var stackOpt = net.iicosahedra.perplexity.util.CuriosUtil.findEquippedInSlot(player, curiosIndex, RelicItem.class);
        if (stackOpt.isPresent() && stackOpt.get().getItem() instanceof RelicItem relic) {
            var holder = relic.getActiveAbilityHolder();
            if (holder != null) {
                ability = holder.value();
            }
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
            if(CuriosUtil.findFirstEquipped(player, LichBaneItem.class).isPresent()){
                LichBaneEffect.processLichConditions(player);
            }
        }
        else{
            player.displayClientMessage(Component.literal("Not enough mana. Current Mana: "+player.getData(Registration.MANA)+" Mana Cost: "+manaCost), true);
        }
    }
}
