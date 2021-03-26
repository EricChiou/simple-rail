package ericchiu.simplerail.entity;

import ericchiu.simplerail.registry.Entities;
import ericchiu.simplerail.registry.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class LocomotiveCartEntity extends AbstractCustomcartEntity {

  public LocomotiveCartEntity(EntityType<?> entityType, World world) {
    super(entityType, world);
  }

  public LocomotiveCartEntity(World world, double x, double y, double z) {
    super(Entities.LOCOMOTIVE_CART, world, x, y, z);
  }

  public LocomotiveCartEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
    this(Entities.LOCOMOTIVE_CART, world);
  }

  @Override
  Item getCartType() {
    return Items.LOCOMOTIVE_CART;
  }

  @Override
  public void tick() {
    super.tick();
  }

}
