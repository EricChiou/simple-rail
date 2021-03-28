package ericchiu.simplerail.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.render.model.LocomotiveCartModel;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class LocomotiveCartRender<T extends LocomotiveCartEntity> extends MinecartRenderer<T> {
  private static final ResourceLocation TEXTURES = new ResourceLocation(
      SimpleRail.MOD_ID + ":textures/entity/minecart_panda.png");
  private final EntityModel<LocomotiveCartEntity> MODEL = new LocomotiveCartModel<LocomotiveCartEntity>();

  public LocomotiveCartRender(EntityRendererManager manager) {
    super(manager);
  }

  @Override
  public ResourceLocation getTextureLocation(T entity) {
    return TEXTURES;
  }

  @Override
  public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer,
      int packedLight) {
    // super.render(entity, entityYaw, partialTicks, matrixStack, buffer,
    // packedLight);

    matrixStack.pushPose();
    long i = (long) entity.getId() * 493286711L;
    i = i * i * 4392167121L + i * 98761L;
    float f = (((float) (i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
    float f1 = (((float) (i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
    float f2 = (((float) (i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
    matrixStack.translate((double) f, (double) f1, (double) f2);
    double d0 = MathHelper.lerp((double) partialTicks, entity.xOld, entity.getX());
    double d1 = MathHelper.lerp((double) partialTicks, entity.yOld, entity.getY());
    double d2 = MathHelper.lerp((double) partialTicks, entity.zOld, entity.getZ());
    // double d3 = (double) 0.3F;
    Vector3d Vector3d = entity.getPos(d0, d1, d2);
    float f3 = MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot);
    if (Vector3d != null) {
      Vector3d Vector3d1 = entity.getPosOffs(d0, d1, d2, (double) 0.3F);
      Vector3d Vector3d2 = entity.getPosOffs(d0, d1, d2, (double) -0.3F);
      if (Vector3d1 == null) {
        Vector3d1 = Vector3d;
      }

      if (Vector3d2 == null) {
        Vector3d2 = Vector3d;
      }

      matrixStack.translate(Vector3d.x - d0, (Vector3d1.y + Vector3d2.y) / 2.0D - d1, Vector3d.z - d2);
      Vector3d Vector3d3 = Vector3d2.add(-Vector3d1.x, -Vector3d1.y, -Vector3d1.z);
      if (Vector3d3.length() != 0.0D) {
        Vector3d3 = Vector3d3.normalize();
        entityYaw = (float) (Math.atan2(Vector3d3.z, Vector3d3.x) * 180.0D / Math.PI);
        f3 = (float) (Math.atan(Vector3d3.y) * 73.0D);
      }
    }

    entityYaw %= 360;
    if (entityYaw < 0)
      entityYaw += 360;
    entityYaw += 360;

    double serverYaw = entity.yRot;
    serverYaw += 180;
    serverYaw %= 360;
    if (serverYaw < 0)
      serverYaw += 360;
    serverYaw += 360;

    if (Math.abs(entityYaw - serverYaw) > 90) {
      entityYaw += 180;
      f3 = -f3;
    }

    matrixStack.translate(0.0D, 0.375D, 0.0D);
    matrixStack.mulPose(Vector3f.YP.rotationDegrees(entityYaw));
    float f5 = (float) entity.getHurtTime() - partialTicks;
    float f6 = entity.getDamage() - partialTicks;
    if (f6 < 0.0F) {
      f6 = 0.0F;
    }

    if (f5 > 0.0F) {
      matrixStack
          .mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(f5) * f5 * f6 / 10.0F * (float) entity.getHurtDir()));
    }

    int j = entity.getDisplayOffset();
    BlockState blockstate = entity.getDisplayBlockState();
    if (blockstate.getRenderShape() != BlockRenderType.INVISIBLE) {
      matrixStack.pushPose();
      // float f4 = 0.75F;
      matrixStack.scale(0.75F, 0.75F, 0.75F);
      matrixStack.translate(0.5D, (double) ((float) (j - 8) / 16.0F), -0.5D);
      matrixStack.mulPose(Vector3f.YP.rotationDegrees(270.0F));
      renderMinecartContents(entity, partialTicks, blockstate, matrixStack, buffer, packedLight);
      matrixStack.popPose();
    }

    matrixStack.scale(-1.0F, -1.0F, 1.0F);
    MODEL.setupAnim(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
    IVertexBuilder ivertexbuilder = buffer.getBuffer(MODEL.renderType(getTextureLocation(entity)));
    MODEL.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    matrixStack.popPose();
  }

  @Override
  protected void renderMinecartContents(T entity, float partialTicks, BlockState state, MatrixStack matrixStack,
      IRenderTypeBuffer buffer, int packedLight) {
    super.renderMinecartContents(entity, partialTicks, state, matrixStack, buffer, packedLight);
  }

}
