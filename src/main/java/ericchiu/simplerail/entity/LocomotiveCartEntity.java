package ericchiu.simplerail.entity;

import ericchiu.simplerail.registry.Entities;
import ericchiu.simplerail.registry.Items;
import ericchiu.simplerail.setup.SimpleRailDataSerializers;
import ericchiu.simplerail.setup.SimpleRailDataSerializers.FacingDirection;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.FurnaceMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class LocomotiveCartEntity extends FurnaceMinecartEntity {

  private static final DataParameter<FacingDirection> FACING_DIRECTION = EntityDataManager
      .defineId(LocomotiveCartEntity.class, SimpleRailDataSerializers.FACING_DIRECTION);

  private static final EntityType<LocomotiveCartEntity> CART_TYPE = Entities.LOCOMOTIVE_CART.get();

  public LocomotiveCartEntity(World world, double x, double y, double z) {
    this(CART_TYPE, world);
    this.setPos(x, y, z);

    BlockState state = world.getBlockState(new BlockPos(x, y, z));
    if (state.hasProperty(BlockStateProperties.RAIL_SHAPE_STRAIGHT)) {
      RailShape shape = state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
      if (shape.equals(RailShape.NORTH_SOUTH)) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.NORTH);
      } else if (shape.equals(RailShape.EAST_WEST)) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.EAST);
      }
    }
  }

  public LocomotiveCartEntity(EntityType<?> type, World world) {
    super(CART_TYPE, world);

    this.entityData.define(FACING_DIRECTION, FacingDirection.NORTH);
  }

  public LocomotiveCartEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
    this(CART_TYPE, world);
  }

  @Override
  public void destroy(DamageSource source) {
    remove();
    if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
      ItemStack itemstack = this.getReturnItem();
      if (this.hasCustomName()) {
        itemstack.setHoverName(this.getCustomName());
      }

      this.spawnAtLocation(itemstack);
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

  @Override
  public void tick() {
    super.tick();

    Vector3d motion = this.getDeltaMovement();
    if (this.getDeltaMovement().x != 0 || this.getDeltaMovement().y != 0 || this.getDeltaMovement().z != 0) {
      if (this.random.nextInt(4) == 0) {
        this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.5D, this.getZ(), 0.0D, 0.0D,
            0.0D);
      }

      if (motion.x > 0 && motion.z == 0) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.EAST);
      } else if (motion.x < 0 && motion.z == 0) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.WEST);
      } else if (motion.x == 0 && motion.z < 0) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.NORTH);
      } else if (motion.x > 0 && motion.z < 0) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.NORTH_EAST);
      } else if (motion.x < 0 && motion.z < 0) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.NORTH_WEST);
      } else if (motion.x == 0 && motion.z > 0) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.SOUTH);
      } else if (motion.x > 0 && motion.z > 0) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.SOUTH_EAST);
      } else if (motion.x < 0 && motion.z > 0) {
        this.entityData.set(FACING_DIRECTION, FacingDirection.SOUTH_WEST);
      }
    }
  }

  public ItemStack getReturnItem() {
    return new ItemStack(Items.LOCOMOTIVE_CART.get());
  }

  public FacingDirection getFacingDirection() {
    return this.entityData.get(FACING_DIRECTION);
  }

}
