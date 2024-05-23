package net.iicosahedra.perplexity.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.iicosahedra.perplexity.spell.SpellCastEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderSpellCastEntity extends EntityRenderer<SpellCastEntity> {
    private final ResourceLocation entityTexture;

    public RenderSpellCastEntity(EntityRendererProvider.Context pContext, ResourceLocation entityTexture) {
        super(pContext);
        this.entityTexture = entityTexture;
    }

    @Override
    public void render(SpellCastEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    }

    @Override
    public ResourceLocation getTextureLocation(SpellCastEntity pEntity) {
        return null;
    }
}
