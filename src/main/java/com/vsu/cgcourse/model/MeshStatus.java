package com.vsu.cgcourse.model;

public class MeshStatus {

    private boolean selected = true;
    private boolean replaceable = false;
    private boolean changes;
    private boolean isGrid = true;
    private boolean isTexture = true;
    private boolean isPainting = true;

    public MeshStatus() {}

    public MeshStatus(boolean selected, boolean replaceable, boolean changes) {
        this.selected = selected;
        this.replaceable = replaceable;
        this.changes = changes;
    }

    public boolean isGrid() {
        return isGrid;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    public boolean isTexture() {
        return isTexture;
    }

    public void setTexture(boolean texture) {
        isTexture = texture;
    }

    public boolean isPainting() {
        return isPainting;
    }

    public void setPainting(boolean painting) {
        isPainting = painting;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isReplaceable() {
        return replaceable;
    }

    public void setReplaceable(boolean replaceable) {
        this.replaceable = replaceable;
    }

    public boolean isChanges() {
        return changes;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
    }
}
