package gui;

import java.util.List;

public final class DropTargetList {
    private static final List<DropTarget> dropTargets = new java.util.ArrayList<>();

    public static void registerDropTarget(DropTarget dropTarget) {
        dropTargets.add(dropTarget);
    }

    public static void unhighlightAll() {
        for (DropTarget dt : dropTargets) {
            if (dt.isHighlighted()) {
                dt.setHighlighted(false);
            }
        }
    }
}
