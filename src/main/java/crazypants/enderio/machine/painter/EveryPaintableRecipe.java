package crazypants.enderio.machine.painter;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import crazypants.enderio.render.paint.IPaintableBlock;

public class EveryPaintableRecipe<T extends Block & IPaintableBlock> extends BasicPainterTemplate<T> {

  protected EveryPaintableRecipe() {
    super(null);
  }

  @Override
  protected T getTargetBlock(ItemStack target) {
    if (target != null) {
      Block blk = Block.getBlockFromItem(target.getItem());
      if (blk instanceof IPaintableBlock) {
        return (T) blk;
      }
    }
    return null;
  }

  @Override
  public boolean isValidTarget(ItemStack target) {
    if (target == null) {
      return false;
    }

    Block blk = Block.getBlockFromItem(target.getItem());
    return blk instanceof IPaintableBlock;
  }

}