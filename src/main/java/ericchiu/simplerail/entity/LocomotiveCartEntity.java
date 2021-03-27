package ericchiu.simplerail.entity;

import ericchiu.simplerail.registry.Entities;
import ericchiu.simplerail.registry.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.FurnaceMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class LocomotiveCartEntity extends FurnaceMinecartEntity {

  private static final EntityType<LocomotiveCartEntity> CART_TYPE = Entities.LOCOMOTIVE_CART.get();

  public LocomotiveCartEntity(World world, double x, double y, double z) {
    super(CART_TYPE, world);
    this.setPos(x, y, z);
  }

  public LocomotiveCartEntity(EntityType<?> type, World world) {
    super(CART_TYPE, world);
  }

  public LocomotiveCartEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
    this(CART_TYPE, world);
  }

  @Override
  public void destroy(DamageSource source) {
    remove();
    if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
      ItemStack itemstack = getReturnItem();
      if (hasCustomName()) {
        itemstack.setHoverName(getCustomName());
      }

      spawnAtLocation(itemstack);
    }
  }

  @Override
  public IPacket<?> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }

  @Override
  public AbstractMinecartEntity.Type getMinecartType() {
    return AbstractMinecartEntity.Type.FURNACE;
  }

  @Override
  public ItemStack getPickedResult(RayTraceResult target) {
    return getCartItem();
  }

  @Override
  public ItemStack getCartItem() {
    return getReturnItem();
  }

  @Override
  public boolean isPoweredCart() {
    return false;
  }

  public ItemStack getReturnItem() {
    return new ItemStack(Items.LOCOMOTIVE_CART.get());
  }

}
