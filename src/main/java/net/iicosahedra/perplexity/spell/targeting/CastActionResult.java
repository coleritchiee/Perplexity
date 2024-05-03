package net.iicosahedra.perplexity.spell.targeting;

public class CastActionResult {
    public static final CastActionResult SUCCESS = new CastActionResult("success", true);
    public static final CastActionResult FAIL = new CastActionResult("fail", false);

    public String id;
    public boolean success;
    public CastActionResult(String name, boolean success) {
        this.id = name;
        this.success = success;
    }
}
