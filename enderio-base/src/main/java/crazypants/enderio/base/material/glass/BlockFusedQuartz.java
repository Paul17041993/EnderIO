package crazypants.enderio.base.material.glass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.enderio.core.common.util.NNList;

import crazypants.enderio.api.IModObject;
import crazypants.enderio.base.EnderIO;
import crazypants.enderio.base.TileEntityEio;
import crazypants.enderio.base.config.config.BlockConfig;
import crazypants.enderio.base.config.config.PersonalConfig;
import crazypants.enderio.base.paint.PaintUtil.IWithPaintName;
import crazypants.enderio.base.render.IBlockStateWrapper;
import crazypants.enderio.base.render.ICustomSubItems;
import crazypants.enderio.base.render.IRenderMapper.IItemRenderMapper;
import crazypants.enderio.base.render.ITintedBlock;
import crazypants.enderio.base.render.ITintedItem;
import crazypants.enderio.base.render.pipeline.BlockStateWrapperBase;
import crazypants.enderio.base.render.property.EnumMergingBlockRenderMode;
import crazypants.enderio.base.render.registry.SmartModelAttacher;
import crazypants.enderio.util.FacadeUtil;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFusedQuartz extends BlockFusedQuartzBase<TileEntityEio> implements ITintedBlock, ICustomSubItems {

  protected static final @Nonnull EnumDyeColor DEFAULT_COLOR = EnumDyeColor.WHITE;

  @SideOnly(Side.CLIENT)
  private static FusedQuartzItemRenderMapper RENDER_MAPPER;

  private final @Nonnull FusedQuartzType glasstype;

  public static BlockFusedQuartz createFusedQuartz(@Nonnull IModObject modObject) {
    final BlockFusedQuartz block = new BlockFusedQuartz(modObject, FusedQuartzType.FUSED_QUARTZ);
    block.init();
    return block;
  }

  public static BlockFusedQuartz createFusedGlass(@Nonnull IModObject modObject) {
    final BlockFusedQuartz block = new BlockFusedQuartz(modObject, FusedQuartzType.FUSED_GLASS);
    block.init();
    return block;
  }

  public static BlockFusedQuartz createEnlightenedFusedQuartz(@Nonnull IModObject modObject) {
    final BlockFusedQuartz block = new BlockFusedQuartz(modObject, FusedQuartzType.ENLIGHTENED_FUSED_QUARTZ);
    block.init();
    return block;
  }

  public static BlockFusedQuartz createEnlightenedFusedGlass(@Nonnull IModObject modObject) {
    final BlockFusedQuartz block = new BlockFusedQuartz(modObject, FusedQuartzType.ENLIGHTENED_FUSED_GLASS);
    block.init();
    return block;
  }

  public static BlockFusedQuartz createDarkFusedQuartz(@Nonnull IModObject modObject) {
    final BlockFusedQuartz block = new BlockFusedQuartz(modObject, FusedQuartzType.DARK_FUSED_QUARTZ);
    block.init();
    return block;
  }

  public static BlockFusedQuartz createDarkFusedGlass(@Nonnull IModObject modObject) {
    final BlockFusedQuartz block = new BlockFusedQuartz(modObject, FusedQuartzType.DARK_FUSED_GLASS);
    block.init();
    return block;
  }

  private BlockFusedQuartz(@Nonnull IModObject modObject, @Nonnull FusedQuartzType glasstype) {
    super(modObject);
    this.glasstype = glasstype;
    setDefaultState(getBlockState().getBaseState().withProperty(EnumMergingBlockRenderMode.RENDER, EnumMergingBlockRenderMode.AUTO)
        .withProperty(FusedQuartzType.KIND, glasstype).withProperty(BlockColored.COLOR, DEFAULT_COLOR));
  }

  @Override
  protected void init() {
    super.init();
    glasstype.setBlock(this);
    SmartModelAttacher.register(this, EnumMergingBlockRenderMode.RENDER, EnumMergingBlockRenderMode.DEFAULTS, EnumMergingBlockRenderMode.AUTO);
  }

  @Override
  protected @Nonnull BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, new IProperty[] { EnumMergingBlockRenderMode.RENDER, FusedQuartzType.KIND, BlockColored.COLOR });
  }

  @Override
  public @Nonnull IBlockState getActualState(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
    return state.withProperty(EnumMergingBlockRenderMode.RENDER, EnumMergingBlockRenderMode.AUTO).withProperty(FusedQuartzType.KIND, glasstype);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public @Nonnull IBlockState getExtendedState(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    FusedQuartzBlockRenderMapper renderMapper = new FusedQuartzBlockRenderMapper(state, world, pos);
    IBlockStateWrapper blockStateWrapper = new BlockStateWrapperBase(state, world, pos, renderMapper);
    blockStateWrapper.addCacheKey(state.getValue(FusedQuartzType.KIND));
    blockStateWrapper.addCacheKey(renderMapper);
    blockStateWrapper.bakeModel();
    return blockStateWrapper;
  }

  @Override
  public @Nonnull IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.byMetadata(meta));
  }

  @Override
  public int getMetaFromState(@Nonnull IBlockState state) {
    return state.getValue(BlockColored.COLOR).getMetadata();
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void getSubBlocks(@Nonnull CreativeTabs par2CreativeTabs, @Nonnull NonNullList<ItemStack> par3List) {
    for (EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
      par3List.add(new ItemStack(this, 1, enumdyecolor.getMetadata()));
    }
  }

  @Override
  @Nonnull
  public NNList<ItemStack> getSubItems() {
    return getSubItems(this, 0, 15);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public @Nonnull IItemRenderMapper getItemRenderMapper() {
    return FusedQuartzItemRenderMapper.instance;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public @Nonnull BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.SOLID;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(@Nonnull IBlockState blockStateIn, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
    IBlockState otherState = world.getBlockState(pos.offset(side)).getActualState(world, pos.offset(side));
    if (FacadeUtil.instance.isFacaded(otherState)) {
      IBlockState facade = FacadeUtil.instance.getFacade(otherState, world, pos.offset(side), side);
      if (facade != null) {
        otherState = facade;
      }
    }

    if (otherState.getBlock() instanceof BlockFusedQuartz) {
      IBlockState ourState = blockStateIn.getActualState(world, pos);
      return !ourState.getValue(FusedQuartzType.KIND).connectTo(otherState.getValue(FusedQuartzType.KIND))
          || (!BlockConfig.glassConnectToTheirColorVariants.get() && ourState.getValue(BlockColored.COLOR) != otherState.getValue(BlockColored.COLOR));
    }
    return true;
  }

  @Override
  public int getBlockTint(@Nonnull IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
    if (PersonalConfig.candyColors.get()) {
      return state.getValue(BlockColored.COLOR).getColorValue();
    } else {
      return MapColor.getBlockColor(state.getValue(BlockColored.COLOR)).colorValue;
    }
  }

  @Override
  @Nullable
  public float[] getBeaconColorMultiplier(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockPos beaconPos) {
    if (PersonalConfig.candyColors.get()) {
      return state.getValue(BlockColored.COLOR).getColorComponentValues();
    } else {
      int colorValue = MapColor.getBlockColor(state.getValue(BlockColored.COLOR)).colorValue;
      int i = (colorValue & 0xFF0000) >> 16;
      int j = (colorValue & 0xFF00) >> 8;
      int k = (colorValue & 0xFF);
      return new float[] { i / 255.0F, j / 255.0F, k / 255.0F };
    }
  }

  @Override
  public Item createBlockItem(@Nonnull IModObject modObject) {
    return modObject.apply(new BlockItemFusedQuartz(this));
  }

  @Override
  public @Nonnull MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
    return MapColor.AIR; // Same as glass
  }

  public static class BlockItemFusedQuartz extends BlockFusedQuartzBase.BlockItemFusedQuartzBase implements ITintedItem, IWithPaintName {

    public BlockItemFusedQuartz(@Nonnull BlockFusedQuartz block) {
      super(block);
    }

    @Override
    public int getItemTint(@Nonnull ItemStack stack, int tintIndex) {
      return MapColor.getBlockColor(EnumDyeColor.byMetadata(stack.getMetadata())).colorValue;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected FusedQuartzType determineQuartzType(ItemStack par1ItemStack) {
      return ((BlockFusedQuartz) block).glasstype;
    }

    @Override
    public String getPaintName(@Nonnull ItemStack stack) {
      return EnderIO.lang.localize("color." + EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName());
    }

  }

}
