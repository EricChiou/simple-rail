package ericchiu.simplerail.entity;

import java.util.ArrayList;
import java.util.UUID;

import ericchiu.simplerail.constants.I18n;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class LocomotiveCartEntity extends FurnaceMinecartEntity {

  private static final DataParameter<FacingDirection> FACING = EntityDataManager.defineId(LocomotiveCartEntity.class,
      SimpleRailDataSerializers.FACING);
  private static final DataParameter<Boolean> LINKABLE = EntityDataManager.defineId(LocomotiveCartEntity.class,
      DataSerializers.BOOLEAN);

  private static final EntityType<LocomotiveCartEntity> CART_TYPE = Entities.LOCOMOTIVE_CART.get();

  private ArrayList<AbstractMinecartEntity> train;
  private BlockPos prevPos;

  public LocomotiveCartEntity(World world, double x, double y, double z) {
    this(CART_TYPE, world);
    this.setPos(x, y, z);
    this.initFacingDirection(world, x, y, z);
  }

  public LocomotiveCartEntity(EntityType<?> type, World world) {
    super(CART_TYPE, world);
  }

  public LocomotiveCartEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
    this(CART_TYPE, world);
  }

  @Override
  protected void defineSynchedData() {
    super.defineSynchedData();
    this.entityData.define(LINKABLE, true);
    this.entityData.define(FACING, FacingDirection.NORTH);
  }

  @Override
  public void destroy(DamageSource source) {
    if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
      ItemStack itemstack = this.getReturnItem();
      if (this.hasCustomName()) {
        itemstack.setHoverName(this.getCustomName());
      }

      this.spawnAtLocation(itemstack);
    }

    LinkageManager.removeTrain(this.uuid);
    this.remove();
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
    return this.getCartItem();
  }

  @Override
  public ItemStack getCartItem() {
    return this.getReturnItem();
  }

  @Override
  public boolean isPoweredCart() {
    return false;
  }

  @Override
  public void tick() {
    super.tick();

    this.updateFacingDirection();

    if (!this.level.isClientSide) {
      if (this.train == null) {
        this.initTrain();
      }

      if (this.detectPosChanged()) {
        this.moveCarts((ServerWorld) this.level);
        this.prevPos = this.blockPosition();
      } else {
        for (AbstractMinecartEntity cart : this.train) {
          cart.moveTo(cart.blockPosition(), cart.yRot, cart.xRot);
        }
      }

      for (AbstractMinecartEntity cart : this.train) {
        cart.setDeltaMovement(Vector3d.ZERO);
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
  protected void readAdditionalSaveData(CompoundNBT nbt) {
    super.readAdditionalSaveData(nbt);

    ArrayList<UUID> trainUuid = new ArrayList<UUID>();
    ListNBT listNBT = (ListNBT) nbt.get(I18n.LOCOMOTIVE_COMPOUND_TRAIN_NAME);
    if (listNBT != null) {
      for (INBT uuidNbt : listNBT) {
        UUID uuid = UUID.fromString(((StringNBT) uuidNbt).getAsString());
        trainUuid.add(uuid);
      }
    }
    LinkageManager.setTrainUuid(this.uuid, trainUuid);

    StringNBT stringNBT = (StringNBT) nbt.get(I18n.LOCOMOTIVE_COMPOUND_FACING_NAME);
    if (stringNBT != null) {
      String facing = stringNBT.getAsString();
      if (facing.equals(FacingDirection.EAST.toString())) {
        this.entityData.set(FACING, FacingDirection.EAST);
      } else if (facing.equals(FacingDirection.WEST.toString())) {
        this.entityData.set(FACING, FacingDirection.WEST);
      } else if (facing.equals(FacingDirection.NORTH.toString())) {
        this.entityData.set(FACING, FacingDirection.NORTH);
      } else if (facing.equals(FacingDirection.SOUTH.toString())) {
        this.entityData.set(FACING, FacingDirection.SOUTH);
      } else if (facing.equals(FacingDirection.NORTH_EAST.toString())) {
        this.entityData.set(FACING, FacingDirection.NORTH_EAST);
      } else if (facing.equals(FacingDirection.NORTH_WEST.toString())) {
        this.entityData.set(FACING, FacingDirection.NORTH_WEST);
      } else if (facing.equals(FacingDirection.SOUTH_EAST.toString())) {
        this.entityData.set(FACING, FacingDirection.SOUTH_EAST);
      } else if (facing.equals(FacingDirection.SOUTH_WEST.toString())) {
        this.entityData.set(FACING, FacingDirection.SOUTH_WEST);
      }
    }
  }

  @Override
  protected void addAdditionalSaveData(CompoundNBT nbt) {
    super.addAdditionalSaveData(nbt);

    if (this.train != null) {
      ListNBT listNbt = new ListNBT();
      for (AbstractMinecartEntity cart : this.train) {
        listNbt.add(StringNBT.valueOf(cart.getUUID().toString()));
      }
      nbt.put(I18n.LOCOMOTIVE_COMPOUND_TRAIN_NAME, listNbt);
    }

    nbt.putString(I18n.LOCOMOTIVE_COMPOUND_FACING_NAME, this.entityData.get(FACING).toString());
  }

  @Override
  public boolean canCollideWith(Entity entity) {
    if (entity instanceof PlayerEntity) {
      return false;
    } else if (entity instanceof LocomotiveCartEntity) {
      return super.canCollideWith(entity);
    } else if (entity instanceof AbstractMinecartEntity) {
      entity.setDeltaMovement(this.getDeltaMovement());
      return false;
    } else if (entity.isAlive()) {
      if (!this.getDeltaMovement().equals(Vector3d.ZERO)) {
        entity.kill();
      }
      return false;
    }

    return super.canCollideWith(entity);
  }

  public FacingDirection getFacingDirection() {
    return this.entityData.get(FACING);
  }

  public boolean linkNewCart(ServerWorld serverWorld, AbstractMinecartEntity cart) {
    if (!LinkageManager.checkCartLinkable(serverWorld, cart.getUUID())) {
      return false;
    }

    if (this.train == null) {
      this.initTrain();
    }

    this.train.add(cart);
    LinkageManager.updateTrain(this.uuid, this.train);
    return true;
  }

  public void deleteTrain() {
    LinkageManager.removeTrain(this.uuid);

    if (this.train != null) {
      for (AbstractMinecartEntity cart : this.train) {
        cart.remove();
      }
    }
    this.remove();
  }

  private void initFacingDirection(World world, double x, double y, double z) {
    BlockState state = this.level.getBlockState(this.blockPosition());
    if (state.hasProperty(BlockStateProperties.RAIL_SHAPE_STRAIGHT)) {
      RailShape shape = state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
      if (shape.equals(RailShape.NORTH_SOUTH)) {
        this.entityData.set(FACING, FacingDirection.NORTH);
      } else if (shape.equals(RailShape.EAST_WEST)) {
        this.entityData.set(FACING, FacingDirection.EAST);
      }
    }
  }

  private void updateFacingDirection() {
    Vector3d motion = this.getDeltaMovement();
    if (this.isMoving(motion)) {
      if (!this.isOnGround() && this.random.nextInt(4) == 0) {
        this.level.addParticle(ParticleTypes.LARGE_SMOKE, //
            this.getX(), this.getY() + 1.5D, this.getZ(), //
            0.0D, 0.0D, 0.0D);
      }

      if (motion.x > 0 && motion.z == 0) {
        this.entityData.set(FACING, FacingDirection.EAST);
      } else if (motion.x < 0 && motion.z == 0) {
        this.entityData.set(FACING, FacingDirection.WEST);
      } else if (motion.x == 0 && motion.z < 0) {
        this.entityData.set(FACING, FacingDirection.NORTH);
      } else if (motion.x == 0 && motion.z > 0) {
        this.entityData.set(FACING, FacingDirection.SOUTH);
      } else if (motion.x > 0 && motion.z < 0) {
        this.entityData.set(FACING, FacingDirection.NORTH_EAST);
      } else if (motion.x < 0 && motion.z < 0) {
        this.entityData.set(FACING, FacingDirection.NORTH_WEST);
      } else if (motion.x > 0 && motion.z > 0) {
        this.entityData.set(FACING, FacingDirection.SOUTH_EAST);
      } else if (motion.x < 0 && motion.z > 0) {
        this.entityData.set(FACING, FacingDirection.SOUTH_WEST);
      }
    }
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

  private void initTrain() {
    this.train = new ArrayList<AbstractMinecartEntity>();
    for (UUID cartUuid : LinkageManager.getTrainUuid(this.uuid)) {
      ServerWorld serverWorld = (ServerWorld) this.level;
      AbstractMinecartEntity cart = (AbstractMinecartEntity) serverWorld.getEntity(cartUuid);

      if (cart != null) {
        this.train.add(cart);
      }
    }
    LinkageManager.updateTrain(this.uuid, this.train);
  }

  private boolean detectPosChanged() {
    Vector3d pos = this.position();
    BlockPos blockPos = this.blockPosition();

    if (this.prevPos == null) {
      this.prevPos = this.blockPosition();
      return false;
    }

    if (!blockPos.equals(this.prevPos)) {
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

  private void moveCarts(ServerWorld serverWorld) {
    if (this.train.size() == 0) {
      return;
    }

    if (this.isOnGround()) {
      this.train.clear();
      LinkageManager.updateTrain(this.uuid, this.train);
      return;
    }

    for (int i = train.size() - 1; i >= 0; i--) {
      AbstractMinecartEntity cart = this.getCartByUuid(serverWorld, this.train.get(i).getUUID());
      if (cart == null) {
        this.train.subList(i, this.train.size()).clear();
        LinkageManager.updateTrain(this.uuid, this.train);
        break;
      }

      if (i == 0) {
        cart.moveTo(this.prevPos.getX() + 0.5D, this.prevPos.getY(), this.prevPos.getZ() + 0.5D);
      } else {
        BlockPos pos = this.train.get(i - 1).blockPosition();
        cart.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
      }
    }
  }

  private AbstractMinecartEntity getCartByUuid(ServerWorld serverWorld, UUID cartUuid) {
    return (AbstractMinecartEntity) serverWorld.getEntity(cartUuid);
  }

}
