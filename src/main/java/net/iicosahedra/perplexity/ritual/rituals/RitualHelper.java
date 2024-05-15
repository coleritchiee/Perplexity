package net.iicosahedra.perplexity.ritual.rituals;

import net.iicosahedra.perplexity.ritual.RitualContext;
import net.minecraft.world.entity.player.Player;

public class RitualHelper {
    public static void startRitual(RitualContext context, Player caster){
        if(TestRitual.INSTANCE.matches(caster.level(), context.activationPoint)){
            TestRitual.INSTANCE.activate(caster.level(), context);
        }
    }
}
