package ericchiu.simplerail.entity;

import java.util.ArrayList;
import java.util.List;

import ericchiu.simplerail.item.Wrench;
import ericchiu.simplerail.link.LinkageManager;
import ericchiu.simplerail.registry.Entities;
import ericchiu.simplerail.registry.Items;
import ericchiu.simplerail.setup.SimpleRailDataSerializers;
import ericchiu.simplerail.setup.SimpleRailDataSerializers.FacingDirection;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.FurnaceMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class LocomotiveCartEntity extends FurnaceMinecartEntity {

  private static final DataParameter<FacingDirection> FACING = EntityDataManager.defineId(LocomotiveCartEntity.class,
      SimpleRailDataSerializers.FACING);
  private static final DataParameter<Boolean> LINKABLE = EntityDataManager.defineId(LocomotiveCartEntity.class,
      DataSerializers.BOOLEAN);

  private static final EntityType<LocomotiveCartEntity> CART_TYPE = Entities.LOCOMOTIVE_CART.get();

  private ArrayList<AbstractMinecartEntity> train;
  private LinkageManager linkageManager;
  private BlockPos currentPos = new BlockPos(0, 0, 0);

  public LocomotiveCartEntity(World world, double x, double y, double z) {
    this(CART_TYPE, world);
    this.setPos(x, y, z);
    this.currentPos = new BlockPos(x, y, z);

    BlockState state = world.getBlockState(new BlockPos(x, y, z));
    if (state.hasProperty(BlockStateProperties.RAIL_SHAPE_STRAIGHT)) {
      RailShape shape = state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
      if (shape.equals(RailShape.NORTH_SOUTH)) {
        this.entityData.set(FACING, FacingDirection.NORTH);
      } else if (shape.equals(RailShape.EAST_WEST)) {
        this.entityData.set(FACING, FacingDirection.EAST);
      }
    }
  }

  public LocomotiveCartEntity(EntityType<?> type, World world) {
    super(CART_TYPE, world);
    this.entityData.define(LINKABLE, true);
    this.entityData.define(FACING, FacingDirection.NORTH);

    if (!world.isClientSide) {
      this.linkageManager = LinkageManager.get(world);
      this.train = this.linkageManager.getTrain(this.uuid);
    }
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

    if (!this.level.isClientSide) {
      if (detectPosChanged()) {
        if (this.isOnGround()) {
          this.train.clear();
          this.updateTrain();
        } else if (this.train.size() > 0) {
          AbstractMinecartEntity firstCart = this.train.get(0);

          if (firstCart == null) {
            this.train.clear();
            this.updateTrain();
          } else {
            // move carts
            if (this.train.size() > 1) {
              for (int i = 1; i < train.size(); i++) {
                AbstractMinecartEntity cart = this.train.get(i);
                if (cart == null) {
                  this.train.subList(i, this.train.size()).clear();
                  this.updateTrain();
                  break;
                } else {
                  BlockPos pos = this.train.get(i - 1).blockPosition();
                  cart.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                }
              }
            }

            // move first carts
            firstCart.moveTo(this.currentPos.getX() + 0.5D, this.currentPos.getY(), this.currentPos.getZ() + 0.5D);
          }
        }

        this.currentPos = this.blockPosition();
      }

      for (AbstractMinecartEntity cart : this.train) {
        cart.setDeltaMovement(Vector3d.ZERO);
      }
    }

    Vector3d motion = this.getDeltaMovement();
    if (isMoving(motion)) {
      if (!this.isOnGround() && this.random.nextInt(4) == 0) {
        this.level.addParticle(ParticleTypes.LARGE_SMOKE, //
            this.getX(), //
            this.getY() + 1.5D, //
            this.getZ(), //
            0.0D, 0.0D, 0.0D);
      }

      if (motion.x > 0 && motion.z == 0) {
        this.entityData.set(FACING, FacingDirection.EAST);
      } else if (motion.x < 0 && motion.z == 0) {
        this.entityData.set(FACING, FacingDirection.WEST);
      } else if (motion.x == 0 && motion.z < 0) {
        this.entityData.set(FACING, FacingDirection.NORTH);
      } else if (motion.x > 0 && motion.z < 0) {
        this.entityData.set(FACING, FacingDirection.NORTH_EAST);
      } else if (motion.x < 0 && motion.z < 0) {
        this.entityData.set(FACING, FacingDirection.NORTH_WEST);
      } else if (motion.x == 0 && motion.z > 0) {
        this.entityData.set(FACING, FacingDirection.SOUTH);
      } else if (motion.x > 0 && motion.z > 0) {
        this.entityData.set(FACING, FacingDirection.SOUTH_EAST);
      } else if (motion.x < 0 && motion.z > 0) {
        this.entityData.set(FACING, FacingDirection.SOUTH_WEST);
      }
    }
  }

  @Override
  public void push(Entity entity) {
    if (entity instanceof PlayerEntity) {
      super.push(entity);
    }
  }

  @Override
  public ActionResultType interact(PlayerEntity player, Hand hand) {
    if (!this.level.isClientSide) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (itemStack.getItem() instanceof Wrench && !this.isOnGround()) {
        BlockPos blockpos = this.getCurrentRailPosition();
        FacingDirection facing = this.entityData.get(FACING);
        BlockPos detectBlockpos = getDetectBlockPos(blockpos, facing);
        BlockState detectBlockState = this.level.getBlockState(detectBlockpos);

        if (detectBlockState.is(BlockTags.RAILS)) {
          List<AbstractMinecartEntity> entities = this.level.getEntitiesOfClass(AbstractMinecartEntity.class,
              new AxisAlignedBB(detectBlockpos), EntityPredicates.NO_SPECTATORS);
          if (entities.size() > 0) {
            this.linkNewCart(entities.get(0));
          }
        }
      }
    }

    return super.interact(player, hand);
  }

  public FacingDirection getFacingDirection() {
    return this.entityData.get(FACING);
  }

  private ItemStack getReturnItem() {
    return new ItemStack(Items.LOCOMOTIVE_CART.get());
  }

  private boolean isMoving() {
    Vector3d motion = this.getDeltaMovement();
    return motion.x != 0 || motion.y != 0 || motion.z != 0;
  }

  private boolean isMoving(Vector3d motion) {
    return motion.x != 0 || motion.y != 0 || motion.z != 0;
  }

  private boolean linkNewCart(AbstractMinecartEntity cart) {
    if (cart.getUUID().equals(this.uuid)) {
      return false;
    }

    if (!this.linkageManager.checkCartLinkable(cart.getUUID())) {
      return false;
    }

    System.out.println("linkNewCart");
    this.train.add(cart);
    this.updateTrain();
    return true;
  }

  private BlockPos getDetectBlockPos(BlockPos blockPos, FacingDirection facing) {
    if (facing.equals(FacingDirection.EAST)) {
      return blockPos.west();
    } else if (facing.equals(FacingDirection.WEST)) {
      return blockPos.east();
    } else if (facing.equals(FacingDirection.NORTH)) {
      return blockPos.south();
    } else if (facing.equals(FacingDirection.SOUTH)) {
      return blockPos.north();
    }

    return blockPos.south();
  }

  private void updateTrain() {
    this.linkageManager.updateTrain(this.uuid, this.train);
  }

  private boolean detectPosChanged() {
    Vector3d pos = this.position();
    BlockPos blockPos = this.blockPosition();

    if (!blockPos.equals(this.currentPos)) {
      if (isMoving()) {
        if ((blockPos.getX() + 0.2D <= pos.x && pos.x <= blockPos.getX() + 0.8D)
            && (blockPos.getZ() + 0.2D <= pos.z && pos.z <= blockPos.getZ() + 0.8D)) {
          return true;
        }
      } else {
        return true;
      }
    }

    return false;
  }

}
