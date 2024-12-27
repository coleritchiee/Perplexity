package net.iicosahedra.perplexity.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.iicosahedra.perplexity.entity.RocketbeltProjectileEntity;
import net.iicosahedra.perplexity.util.ResourceLoc;

public class RocketbeltProjectileRenderer extends EntityRenderer<RocketbeltProjectileEntity> {

    private static final ResourceLocation TEXTURE = ResourceLoc.create("textures/entity/rocketbelt_proj.png");

    public RocketbeltProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(RocketbeltProjectileEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        // Align the texture with the camera
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.scale(0.5F, 0.5F, 0.5F); // Scale the texture (adjust as needed)

        // Create the vertex consumer for rendering
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));

        // Render the flat texture as a quad
        poseStack.translate(0.0D, 0.0D, 0.0D); // Adjust position if necessary
        renderQuad(poseStack, vertexConsumer, 15728880);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    private void renderQuad(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight) {
        PoseStack.Pose pose = poseStack.last();

        // Define quad vertices for a 2D plane
        vertexConsumer.addVertex(pose.pose(), -0.5F, -0.5F, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 1.0F).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, 0.0f, 0.0F, 1.0F).setLight(packedLight);
        vertexConsumer.addVertex(pose.pose(), 0.5F, -0.5F, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 1.0F).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, 0.0F, 0.0F, 1.0F).setLight(packedLight);
        vertexConsumer.addVertex(pose.pose(), 0.5F, 0.5F, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, 0.0F, 0.0F, 1.0F).setLight(packedLight);
        vertexConsumer.addVertex(pose.pose(), -0.5F, 0.5F, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, 0.0F, 0.0F, 1.0F).setLight(packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketbeltProjectileEntity entity) {
        return TEXTURE;
    }
}
