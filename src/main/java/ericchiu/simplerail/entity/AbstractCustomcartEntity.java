package ericchiu.simplerail.entity;

import java.util.function.Supplier;

import ericchiu.simplerail.registry.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class AbstractCustomcartEntity extends AbstractMinecartEntity {

  public AbstractCustomcartEntity(EntityType<?> type, World world) {
    super(type, world);
  }

  public AbstractCustomcartEntity(EntityType<?> type, World world, double x, double y, double z) {
    super(type, world, x, y, z);
  }

  @Override
  public void destroy(DamageSource damageSource) {
    this.remove();
    if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
      ItemStack itemstack = this.getCartReturn();
      if (this.hasCustomName()) {
        itemstack.setHoverName(this.getCustomName());
      }

      this.spawnAtLocation(itemstack);
    }
  }

  @Override
  public AbstractMinecartEntity.Type getMinecartType() {
    return AbstractMinecartEntity.Type.RIDEABLE;
  }

  @Override
  public ActionResultType interact(PlayerEntity playerEntity, Hand hand) {
    return ActionResultType.PASS;
  }

  @Override
  public boolean isPoweredCart() {
    return false;
  }

  @Override
  public ItemStack getCartItem() {
    return getCartReturn();
  }

  @Override
  public ItemStack getPickedResult(RayTraceResult target) {
    return getCartItem();
  }

  public ItemStack getCartReturn() {
    return new ItemStack(getCartType());
  }

  abstract Item getCartType();

}