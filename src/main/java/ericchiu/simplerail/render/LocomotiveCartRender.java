package ericchiu.simplerail.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.entity.LocomotiveCartEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.util.ResourceLocation;

public class LocomotiveCartRender extends MinecartRenderer<LocomotiveCartEntity> {

  public LocomotiveCartRender(EntityRendererManager renderManager) {
    super(renderManager);
    this.shadowStrength = 0.5f;
  }

  @Override
  public ResourceLocation getTextureLocation(LocomotiveCartEntity entity) {
    return new ResourceLocation(SimpleRail.MOD_ID + ":textures/entities/minecart_panda.png");
  }

  @Override
  public void render(LocomotiveCartEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_,
      IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
    super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
  }

  @Override
  protected void renderMinecartContents(LocomotiveCartEntity p_225630_1_, float p_225630_2_, BlockState p_225630_3_,
      MatrixStack p_225630_4_, IRenderTypeBuffer p_225630_5_, int p_225630_6_) {
    super.renderMinecartContents(p_225630_1_, p_225630_2_, p_225630_3_, p_225630_4_, p_225630_5_, p_225630_6_);
  }

}
