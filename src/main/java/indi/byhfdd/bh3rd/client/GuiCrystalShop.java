package indi.byhfdd.bh3rd.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.shop.ContainerCrystalShop;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiCrystalShop extends GuiContainer {

    private static final String LANG = "gui.bh3rd.crystalShop.";
    private static final int TEXT = 0xE8F3FA;
    private static final int MUTED = 0xA3B6C9;
    private static final int ACCENT = 0x7AE2F2;
    private static final int ERROR = 0xFFAB94;

    private final ContainerCrystalShop shop;
    private GuiButton purchaseButton;

    public GuiCrystalShop(ContainerCrystalShop container) {
        super(container);
        shop = container;
        xSize = 248;
        ySize = 212;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        purchaseButton = new GuiButton(0, guiLeft + 172, guiTop + 68, 64, 20, I18n.format(LANG + "buy"));
        buttonList.add(purchaseButton);
        updatePurchaseButton();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        updatePurchaseButton();
    }

    private void updatePurchaseButton() {
        int index = shop.getOfferIndex();
        purchaseButton.enabled = index >= 0 && index < 7
            && shop.getOfferStack() != null
            && shop.getCrystalBalance() >= ContainerCrystalShop.PRICE;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == purchaseButton && button.enabled) {
            // The server checks the day, inventory space and payment before exchanging.
            mc.playerController.sendEnchantPacket(inventorySlots.windowId, shop.getOfferIndex());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        updatePurchaseButton();
        super.drawScreen(mouseX, mouseY, partialTicks);

        ItemStack offer = shop.getOfferStack();
        if (offer != null && mc.thePlayer.inventory.getItemStack() == null
            && func_146978_c(18, 41, 36, 36, mouseX, mouseY)) {
            renderToolTip(offer, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawRect(guiLeft - 1, guiTop - 1, guiLeft + xSize + 1, guiTop + ySize + 1, 0xFF47667F);
        drawGradientRect(guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, 0xFF203349, 0xFF131F30);
        drawRect(guiLeft, guiTop, guiLeft + xSize, guiTop + 2, 0xFF70D7ED);
        drawRect(guiLeft + 12, guiTop + 27, guiLeft + 236, guiTop + 28, 0xFF3B546C);

        drawRect(guiLeft + 12, guiTop + 33, guiLeft + 236, guiTop + 97, 0xFF405A74);
        drawGradientRect(guiLeft + 13, guiTop + 34, guiLeft + 235, guiTop + 96, 0xFF2A435C, 0xFF22364C);
        drawRect(guiLeft + 13, guiTop + 34, guiLeft + 15, guiTop + 96, 0xFF70D7ED);
        drawRect(guiLeft + 18, guiTop + 41, guiLeft + 54, guiTop + 77, 0xFF142538);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                drawInventorySlot(44 + column * 18, 124 + row * 18);
            }
        }
        for (int column = 0; column < 9; column++) {
            drawInventorySlot(44 + column * 18, 182);
        }

        ItemStack offer = shop.getOfferStack();
        if (offer != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef(guiLeft + 20, guiTop + 43, 0.0F);
            GL11.glScalef(2.0F, 2.0F, 1.0F);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            itemRender.zLevel = 100.0F;
            itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offer, 0, 0);
            itemRender.zLevel = 0.0F;
            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void drawInventorySlot(int x, int y) {
        drawRect(guiLeft + x - 1, guiTop + y - 1, guiLeft + x + 17, guiTop + y + 17, 0xFF51677D);
        drawRect(guiLeft + x, guiTop + y, guiLeft + x + 16, guiTop + y + 16, 0xFF162334);
        drawRect(guiLeft + x, guiTop + y, guiLeft + x + 16, guiTop + y + 1, 0xFF0B1420);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawFittedText(I18n.format(LANG + "title"), 12, 11, 106, TEXT);
        String balance = I18n.format(LANG + "balance", shop.getCrystalBalance());
        fontRendererObj.drawString(balance, 236 - fontRendererObj.getStringWidth(balance), 11, ACCENT);

        ItemStack offer = shop.getOfferStack();
        String name = offer == null ? I18n.format(LANG + "loading") : offer.getDisplayName();
        drawFittedText(name, 62, 41, 166, TEXT);
        int index = shop.getOfferIndex();
        if (index >= 0 && index < 7) {
            drawFittedText(I18n.format(LANG + "today", I18n.format(LANG + "day." + index)), 62, 54, 166, MUTED);
        }
        drawFittedText(I18n.format(LANG + "price", ContainerCrystalShop.PRICE), 62, 75, 106, ACCENT);

        drawFittedText(statusText(), 12, 102, 224, statusColor());
        fontRendererObj.drawString(I18n.format("container.inventory"), 44, 113, MUTED);
        String rotation = fontRendererObj.trimStringToWidth(I18n.format(LANG + "rotation"), 232);
        fontRendererObj.drawString(rotation, (xSize - fontRendererObj.getStringWidth(rotation)) / 2, 202, MUTED);
    }

    private String statusText() {
        if (shop.getOfferIndex() < 0) {
            return I18n.format(LANG + "loading");
        }
        switch (shop.getStatus()) {
            case ContainerCrystalShop.SUCCESS:
                return I18n.format(LANG + "status.success");
            case ContainerCrystalShop.INSUFFICIENT_CRYSTALS:
                return I18n.format(LANG + "status.insufficient");
            case ContainerCrystalShop.INVENTORY_FULL:
                return I18n.format(LANG + "status.inventoryFull");
            case ContainerCrystalShop.OFFER_CHANGED:
                return I18n.format(LANG + "status.offerChanged");
            default:
                return I18n.format(
                    LANG + (shop.getCrystalBalance() < ContainerCrystalShop.PRICE ? "status.insufficient"
                        : "status.ready"));
        }
    }

    private int statusColor() {
        if (shop.getOfferIndex() < 0) {
            return MUTED;
        }
        int status = shop.getStatus();
        if (status == ContainerCrystalShop.SUCCESS) {
            return 0x9FE3AC;
        }
        if (status == ContainerCrystalShop.INSUFFICIENT_CRYSTALS || status == ContainerCrystalShop.INVENTORY_FULL
            || status == ContainerCrystalShop.OFFER_CHANGED
            || shop.getCrystalBalance() < ContainerCrystalShop.PRICE) {
            return ERROR;
        }
        return MUTED;
    }

    private void drawFittedText(String text, int x, int y, int maxWidth, int color) {
        if (fontRendererObj.getStringWidth(text) > maxWidth) {
            text = fontRendererObj.trimStringToWidth(text, maxWidth - fontRendererObj.getStringWidth("...")) + "...";
        }
        fontRendererObj.drawString(text, x, y, color);
    }
}
