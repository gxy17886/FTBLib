package ftb.lib.gui.widgets;

import ftb.lib.client.FTBLibClient;
import ftb.lib.gui.GuiLM;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.*;

import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class ButtonSimpleLM extends ButtonLM
{
	public int colorText = 0xFFFFFFFF;
	public int colorButton = 0xFF888888;
	public int colorButtonOver = 0xFF999999;
	
	public ButtonSimpleLM(GuiLM g, int x, int y, int w, int h)
	{ super(g, x, y, w, h); }
	
	public void addMouseOverText(List<String> l)
	{
	}
	
	public void renderWidget()
	{
		int ax = getAX();
		int ay = getAY();
		FTBLibClient.setGLColor(mouseOver(ax, ay) ? colorButtonOver : colorButton);
		GuiLM.drawBlankRect(ax, ay, gui.getZLevel(), width, height);
		GlStateManager.color(1F, 1F, 1F, 1F);
		gui.drawCenteredString(gui.getFontRenderer(), title, ax + width / 2, ay + (height - gui.getFontRenderer().FONT_HEIGHT) / 2, colorText);
	}
}