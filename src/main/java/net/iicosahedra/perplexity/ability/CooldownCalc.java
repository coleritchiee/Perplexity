package net.iicosahedra.perplexity.ability;

public class CooldownCalc {
    public static double cooldownReduction(double abilityHaste){
        return ((1.0/(1.0+(abilityHaste/100.0))));
    }
}
