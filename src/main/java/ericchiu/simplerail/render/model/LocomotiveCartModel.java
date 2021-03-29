package ericchiu.simplerail.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import ericchiu.simplerail.entity.LocomotiveCartEntity;
import net.minecraft.client.renderer.entity.model.MinecartModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class LocomotiveCartModel<E extends LocomotiveCartEntity> extends MinecartModel<E> {

  public ModelRenderer cart;
  public ModelRenderer body;
  public ModelRenderer chassis;
  public ModelRenderer furnace;
  public ModelRenderer wheels;
  public ModelRenderer bumper;
  public ModelRenderer smokestack;

  public LocomotiveCartModel() {
    texWidth = 96;
    texHeight = 64;

    cart = new ModelRenderer(this);
    cart.setPos(0.0F, 5.0F, 0.0F);

    body = new ModelRenderer(this, 0, 0);
    body.setPos(0.0F, 0.0F, 0.0F);
    body.addBox(-2.0F, -14.0F, -6.0F, 11, 11, 12, 0.0F, true);
    cart.addChild(body);

    furnace = new ModelRenderer(this, 47, 0);
    furnace.setPos(0.0F, 0.0F, 0.0F);
    furnace.addBox(-9.0F, -15.0F, -7.0F, 7, 12, 14, 0.0F, true);
    cart.addChild(furnace);

    chassis = new ModelRenderer(this, 0, 27);
    chassis.setPos(0.0F, 0.0F, 0.0F);
    chassis.addBox(-10.0F, -3.0F, -8.0F, 20, 2, 16, 0.0F, true);
    cart.addChild(chassis);

    wheels = new ModelRenderer(this, 0, 46);
    wheels.setPos(0.0F, 0.0F, 0.0F);
    wheels.addBox(-9.0F, -1.0F, -7.0F, 18, 1, 14, 0.0F, true);
    cart.addChild(wheels);

    bumper = new ModelRenderer(this, 65, 46);
    bumper.setPos(0.0F, 0.0F, 0.0F);
    bumper.addBox(9.0F, -4.0F, -6.0F, 1, 4, 12, 0.0F, true);
    cart.addChild(bumper);

    smokestack = new ModelRenderer(this, 73, 27);
    smokestack.setPos(0.0F, 0.0F, 0.0F);
    smokestack.addBox(4.0F, -18.0F, -2.0F, 4, 4, 4, 0.0F, true);
    cart.addChild(smokestack);
  }

  @Override
  public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
      float headPitch) {
    super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
  }

  public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.xRot = x;
    modelRenderer.yRot = y;
    modelRenderer.zRot = z;
  }

  @Override
  public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
      float red, float green, float blue, float alpha) {
    cart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
  }

}
