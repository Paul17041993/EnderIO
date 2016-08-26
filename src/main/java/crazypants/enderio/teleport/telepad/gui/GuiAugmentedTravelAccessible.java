package crazypants.enderio.teleport.telepad.gui;

import crazypants.enderio.EnderIO;
import crazypants.enderio.GuiHandler;
import crazypants.enderio.gui.IconEIO;
import crazypants.enderio.network.PacketHandler;
import crazypants.enderio.teleport.ContainerTravelAccessable;
import crazypants.enderio.teleport.GuiTravelAccessable;
import crazypants.enderio.teleport.telepad.ITileTelePad;
import crazypants.enderio.teleport.telepad.packet.PacketOpenServerGui;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiAugmentedTravelAccessible extends GuiTravelAccessable implements IToggleableGui {

  private static final int ID_SWITCH_BUTTON = 99;

  ToggleTravelButton switchButton;

  private ITileTelePad te;
  
  public GuiAugmentedTravelAccessible(InventoryPlayer playerInv, ITileTelePad te, World world) {
    super(playerInv, te, world);
    this.te = te;
    switchButton = new ToggleTravelButton(this, ID_SWITCH_BUTTON, GuiTelePad.SWITCH_X, GuiTelePad.SWITCH_Y, IconEIO.IO_WHATSIT);
    switchButton.setToolTip(EnderIO.lang.localize("gui.telepad.configure.telepad"));
  }
  
  public GuiAugmentedTravelAccessible(ContainerTravelAccessable container) {
    super(container);
  }

  @Override
  public void initGui() {
    super.initGui();
    switchButton.onGuiInit();
  }

  @Override
  public void switchGui() {
    mc.thePlayer.openGui(EnderIO.instance, GuiHandler.GUI_ID_TELEPAD, world, te.getTileEntity().getPos().getX(), te.getTileEntity().getPos().getY(), te.getTileEntity().getPos().getZ());
    PacketHandler.INSTANCE.sendToServer(new PacketOpenServerGui(te, GuiHandler.GUI_ID_TELEPAD));
  }
}