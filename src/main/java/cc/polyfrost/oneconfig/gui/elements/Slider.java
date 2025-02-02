package cc.polyfrost.oneconfig.gui.elements;

import cc.polyfrost.oneconfig.internal.assets.Colors;
import cc.polyfrost.oneconfig.renderer.RenderManager;
import cc.polyfrost.oneconfig.utils.InputUtils;
import org.lwjgl.input.Mouse;

public class Slider extends BasicElement {
    private final float min, max;
    protected float value;
    protected float currentDragPoint;
    protected float dragPointerSize = 8f;
    private boolean dragging = false;
    private boolean mouseWasDown = false;

    public Slider(int length, float min, float max, float startValue) {
        super(length, 8, false);
        this.min = min;
        this.max = max;
        setValue(startValue);
    }

    @Override
    public void draw(long vg, int x, int y) {
        if(!disabled) update(x, y);
        else RenderManager.setAlpha(vg, 0.5f);
        RenderManager.drawRoundedRect(vg, x, y + 2, width, height - 4, Colors.GRAY_300, 3f);
        RenderManager.drawRoundedRect(vg, x, y + 2, width * value, height - 4, Colors.PRIMARY_500, 3f);
        RenderManager.drawRoundedRect(vg, currentDragPoint - dragPointerSize / 2, y - 8, 24, 24, Colors.WHITE, 12f);
        RenderManager.setAlpha(vg, 1f);


    }

    public void update(int x, int y) {
        super.update(x, y);
        boolean isMouseDown = Mouse.isButtonDown(0);
        boolean hovered = InputUtils.isAreaHovered(x - 6, y - 3, width + 12, height + 6);
        if (hovered && isMouseDown && !mouseWasDown) dragging = true;
        mouseWasDown = isMouseDown;
        if (dragging) {
            value = ((float) InputUtils.mouseX() - x) / width;
        }
        if (dragging && InputUtils.isClicked(true)) {
            dragging = false;
            value = ((float) InputUtils.mouseX() - x) / width;
        }

        if (value < 0) value = 0;
        if (value > 1) value = 1;

        currentDragPoint = x + (width - dragPointerSize) * value;

    }

    public float getValue() {
        return value * (max - min) + min;
    }

    public void setValue(float value) {
        this.value = (value - min) / (max - min);
    }

    public boolean isDragging() {
        return dragging;
    }
}
