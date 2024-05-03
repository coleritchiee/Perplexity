package net.iicosahedra.perplexity.spell.effect;

import com.mojang.authlib.GameProfile;
import net.iicosahedra.perplexity.spell.Affinities;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.SpellResolver;
import net.iicosahedra.perplexity.spell.component.AbstractEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class EffectBreak extends AbstractEffect {
    public static final EffectBreak INSTANCE = new EffectBreak();

    public EffectBreak() {
        super("effect.break", "break", Affinities.EARTH);
    }

    @Override
    public void onCastOnBlock(BlockHitResult blockHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellResolver resolver) {
        ItemStack stack = Items.NETHERITE_PICKAXE.getDefaultInstance();
        if(caster instanceof Player){
            FakePlayer player = FakePlayerFactory.get((ServerLevel) world, new GameProfile(caster.getUUID(), UsernameCache.getLastKnownUsername(caster.getUUID())));
            Block block =   caster.level().getBlockState(blockHitResult.getBlockPos()).getBlock();
            BlockState state = world.getBlockState(blockHitResult.getBlockPos());
            if(world.getBlockState(blockHitResult.getBlockPos()).getDestroySpeed(world, blockHitResult.getBlockPos())>=0) {
                stack.mineBlock(world, state, blockHitResult.getBlockPos(), player);
                boolean removed = state.onDestroyedByPlayer(world, blockHitResult.getBlockPos(), player, state.canHarvestBlock(world, blockHitResult.getBlockPos(), player), world.getFluidState(blockHitResult.getBlockPos()));
                if (removed) {
                    state.getBlock().destroy(world, blockHitResult.getBlockPos(), state);
                }
                block.playerDestroy(world, player, blockHitResult.getBlockPos(), state, world.getBlockEntity(blockHitResult.getBlockPos()), stack);
                int exp = ForgeHooks.onBlockBreakEvent(world, GameType.SURVIVAL, player, blockHitResult.getBlockPos());
                if (removed && exp > 0) {
                    state.getBlock().popExperience((ServerLevel) world, blockHitResult.getBlockPos(), exp);
                }
            }
        }
    }
}
