package com.vsu.cgcourse.model;

public class ModeStatus {

    private boolean selected = true;
    private boolean replaceable = false;
    private boolean changes;

    public ModeStatus() {}

    public ModeStatus(boolean selected, boolean replaceable, boolean changes) {
        this.selected = selected;
        this.replaceable = replaceable;
        this.changes = changes;
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
