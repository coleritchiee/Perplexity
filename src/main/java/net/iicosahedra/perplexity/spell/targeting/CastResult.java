package net.iicosahedra.perplexity.spell.targeting;

public class CastResult {
    public static final CastResult SUCCESS = new CastResult("success", true);
    public static final CastResult FAIL = new CastResult("fail", false);

    private String result;
    public boolean success;
    private CastResult(String result, boolean success){
        this.result = result;
        this.success = success;
    }
}
