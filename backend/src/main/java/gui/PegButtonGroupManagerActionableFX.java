package gui;

import java.util.List;

public class PegButtonGroupManagerActionableFX extends PegButtonGroupManagerFX {
    protected PegActionMenuFX actionMenu;

    public PegButtonGroupManagerActionableFX() {
        super();
        configureActionMenu();
    }

    public PegButtonGroupManagerActionableFX(List<PegButtonSelectableFX> buttonGroup) {
        super(buttonGroup);
        configureActionMenu();
    }

    protected void configureActionMenu() {
        actionMenu = new PegActionMenuFX();
    }

    @Override
    public void selectButton(PegButtonSelectableFX button, boolean shiftDown) {
        if (!(button instanceof PegButtonActionableFX)) {
            throw new IllegalArgumentException("Button must be a PegButtonActionableFX, not " + button.getClass());
        }
        PegButtonActionableFX b = (PegButtonActionableFX)button;
        super.selectButton(b, shiftDown);
        actionMenu.showFor(b, b.getAvailableActions());
        if (selectedButtons.size() > 1) {
            actionMenu.hide();
        }
    }

    public PegActionMenuFX getPegActionMenu() {
        return actionMenu;
    }
    
    @Override
    public void deselectAll() {
        super.deselectAll();
        actionMenu.hide();
    }

    @Override
    public void dragging(PegButtonSelectableFX button) {
        super.dragging(button);
        actionMenu.hide();
    }

    @Override
    protected void buttonsAdjusted() {
        actionMenu.relocate();
    }
}
