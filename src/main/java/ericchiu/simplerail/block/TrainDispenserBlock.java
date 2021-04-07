package ericchiu.simplerail.block;

import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.item.LocomotiveCart;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.ProxyBlockSource;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MinecartItem;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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
      // LocomotiveCartEntity locomotive = new LocomotiveCartEntity(world, pos.getX()
      // + 2.5D, pos.getY(),
      // pos.getZ() + 0.5D);
      // AbstractMinecartEntity cart = new MinecartEntity(world, pos.getX() + 1.5D,
      // pos.getY(), pos.getZ() + 0.5D);

      // world.addFreshEntity(locomotive);
      // world.addFreshEntity(cart);
      // locomotive.linkNewCart((ServerWorld) world, cart);

      ProxyBlockSource proxyblocksource = new ProxyBlockSource((ServerWorld) world, pos);
      ChestTileEntity chestTileEntity = proxyblocksource.getEntity();
      for (int i = 0; i < containerSize; i++) {
        ItemStack itemstack = chestTileEntity.getItem(i);
        System.out.println(itemstack);
        if (itemstack.getItem() instanceof LocomotiveCart) {
        } else if (itemstack.getItem() instanceof MinecartItem) {
        }
      }
    }
  }

  @Override
  public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
    return new ChestTileEntity();
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

}
