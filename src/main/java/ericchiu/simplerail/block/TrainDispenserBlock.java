package ericchiu.simplerail.block;

import java.util.LinkedList;
import java.util.List;

import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.item.LocomotiveCart;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.ProxyBlockSource;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.entity.item.minecart.FurnaceMinecartEntity;
import net.minecraft.entity.item.minecart.HopperMinecartEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MinecartItem;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class TrainDispenserBlock extends DispenserBlock {

  private static final int containerSize = 27;

  public TrainDispenserBlock() {
    super(AbstractBlock.Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).strength(3.5F));
  }

  @Override
  public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos2,
      boolean p_220069_6_) {
    boolean powered = world.hasNeighborSignal(pos);

    if (!world.isClientSide && powered) {
      Direction direction = state.getValue(DispenserBlock.FACING);
      ProxyBlockSource proxyblocksource = new ProxyBlockSource((ServerWorld) world, pos);
      ChestTileEntity chestTileEntity = proxyblocksource.getEntity();

      LocomotiveCartEntity locomotive = null;
      List<AbstractMinecartEntity> trainCars = new LinkedList<AbstractMinecartEntity>();

      for (int i = 0; i < containerSize; i++) {
        ItemStack itemstack = chestTileEntity.getItem(i);
        String itemName = itemstack.getItem().toString();
        Vector3d placeLoc = this.getPlaceLoc(direction, pos, i);

        List<AbstractMinecartEntity> carts = world.getEntitiesOfClass(AbstractMinecartEntity.class,
            new AxisAlignedBB(new BlockPos(placeLoc)));
        if (carts.size() > 0) {
          break;
        }

        if (itemstack.getItem() instanceof LocomotiveCart) {
          LocomotiveCartEntity cart = new LocomotiveCartEntity(world, placeLoc.x, placeLoc.y, placeLoc.z);
          world.addFreshEntity(cart);
          if (i == 0) {
            locomotive = cart;
          }

        } else if (itemstack.getItem() instanceof MinecartItem) {
          AbstractMinecartEntity cart = this.getCartEntity(world, placeLoc, itemName);
          world.addFreshEntity(cart);
          trainCars.add(cart);
        }
      }

      if (locomotive != null) {
        for (AbstractMinecartEntity cart : trainCars) {
          locomotive.linkNewCart((ServerWorld) world, cart);
        }
      }
    }
  }

  @Override
  public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
    return new ChestTileEntity();
  }

  @Override
  public void onPlace(BlockState state, World world, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
    Direction direction = state.getValue(DispenserBlock.FACING);
    if (!direction.equals(Direction.EAST) //
        && !direction.equals(Direction.WEST) //
        && !direction.equals(Direction.NORTH) //
        && !direction.equals(Direction.SOUTH)) {
      world.setBlock(pos, state.setValue(FACING, Direction.NORTH), 3);
    }
  }

  @Override
  public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
      BlockRayTraceResult rayTraceResult) {
    if (world.isClientSide) {
      return ActionResultType.SUCCESS;
    }

    TileEntity tileEntity = world.getBlockEntity(pos);
    if (tileEntity instanceof ChestTileEntity) {
      player.openMenu((ChestTileEntity) tileEntity);
      player.awardStat(Stats.OPEN_CHEST);
    }

    return ActionResultType.CONSUME;
  }

  private Vector3d getPlaceLoc(Direction direction, BlockPos pos, int translate) {
    Vector3d loc = new Vector3d(pos.getX(), pos.getY(), pos.getZ());

    if (direction.equals(Direction.EAST)) {
      loc = loc.add(1.5D, 0.0D, translate + 0.5D);
    } else if (direction.equals(Direction.WEST)) {
      loc = loc.add(-0.5D, 0.0D, -translate + 0.5D);
    } else if (direction.equals(Direction.NORTH)) {
      loc = loc.add(translate + 0.5D, 0.0D, -0.5D);
    } else if (direction.equals(Direction.SOUTH)) {
      loc = loc.add(-translate + 0.5D, 0.0D, 1.5D);
    }

    return loc;
  }

  private AbstractMinecartEntity getCartEntity(World world, Vector3d placeLoc, String itemName) {
    AbstractMinecartEntity cart = null;
    switch (itemName) {
    case "chest_minecart":
      cart = new ChestMinecartEntity(world, placeLoc.x, placeLoc.y, placeLoc.z);
      break;
    case "furnace_minecart":
      cart = new FurnaceMinecartEntity(world, placeLoc.x, placeLoc.y, placeLoc.z);
      break;
    case "hopper_minecart":
      cart = new HopperMinecartEntity(world, placeLoc.x, placeLoc.y, placeLoc.z);
      break;
    case "tnt_minecart":
      cart = new TNTMinecartEntity(world, placeLoc.x, placeLoc.y, placeLoc.z);
      break;
    default:
      cart = new MinecartEntity(world, placeLoc.x, placeLoc.y, placeLoc.z);
      break;
    }

    return cart;
  }

}
