package com.raoulvdberge.refinedstorage.gui.sidebutton;

import com.raoulvdberge.refinedstorage.gui.GuiBase;
import com.raoulvdberge.refinedstorage.tile.TileDiskManipulator;
import com.raoulvdberge.refinedstorage.tile.data.TileDataManager;
import com.raoulvdberge.refinedstorage.tile.data.TileDataParameter;
import net.minecraft.util.text.TextFormatting;

public class SideButtonIOMode extends SideButton {
    private TileDataParameter<Integer> parameter;

    public SideButtonIOMode(GuiBase gui, TileDataParameter<Integer> parameter) {
        super(gui);

        this.parameter = parameter;
    }

    @Override
    public String getTooltip() {
        return TextFormatting.GREEN + GuiBase.t("sidebutton.refinedstorage:iomode") + TextFormatting.RESET + "\n" + GuiBase.t("sidebutton.refinedstorage:iomode." + (parameter.getValue() == TileDiskManipulator.IO_MODE_INSERT ? "insert" : "extract"));
    }

    @Override
    protected void drawButtonIcon(int x, int y) {
        gui.drawTexture(x, y, parameter.getValue() == TileDiskManipulator.IO_MODE_EXTRACT ? 0 : 16, 160, 16, 16);
    }

    @Override
    public void actionPerformed() {
        TileDataManager.setParameter(parameter, parameter.getValue() == TileDiskManipulator.IO_MODE_INSERT ? TileDiskManipulator.IO_MODE_EXTRACT : TileDiskManipulator.IO_MODE_INSERT);
    }
}
