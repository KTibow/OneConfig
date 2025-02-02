package cc.polyfrost.oneconfig.gui.animations;

import cc.polyfrost.oneconfig.utils.color.ColorPalette;

public class ColorAnimation {
    private int speed = 100;
    private ColorPalette palette;
    /**
     * 0 = nothing
     * 1 = hovered
     * 2 = pressed
     * 3 = color palette changed
     */
    private int prevState = 0;
    private Animation redAnimation;
    private Animation greenAnimation;
    private Animation blueAnimation;
    private Animation alphaAnimation;

    public ColorAnimation(ColorPalette palette) {
        this.palette = palette;
        redAnimation = new DummyAnimation(palette.getNormalColorf()[0]);
        greenAnimation = new DummyAnimation(palette.getNormalColorf()[1]);
        blueAnimation = new DummyAnimation(palette.getNormalColorf()[2]);
        alphaAnimation = new DummyAnimation(palette.getNormalColorf()[3]);
    }

    /**
     * Return the current color at the current time, according to a EaseInOut quadratic animation.
     *
     * @param hovered the hover state of the element
     * @param pressed the pressed state of the element
     * @return the current color
     */
    public int getColor(boolean hovered, boolean pressed) {
        int state = pressed ? 2 : hovered ? 1 : 0;
        if (state != prevState) {
            float[] newColors = pressed ? palette.getPressedColorf() : hovered ? palette.getHoveredColorf() : palette.getNormalColorf();
            redAnimation = new EaseInOutQuad(speed, redAnimation.get(), newColors[0], false);
            greenAnimation = new EaseInOutQuad(speed, greenAnimation.get(), newColors[1], false);
            blueAnimation = new EaseInOutQuad(speed, blueAnimation.get(), newColors[2], false);
            alphaAnimation = new EaseInOutQuad(speed, alphaAnimation.get(), newColors[3], false);
            prevState = state;
        }
        return ((int) (alphaAnimation.get() * 255) << 24) | ((int) (redAnimation.get() * 255) << 16) | ((int) (greenAnimation.get() * 255) << 8) | ((int) (blueAnimation.get() * 255));
    }

    /** Set the speed in milliseconds for the animation. */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /** Get the speed in milliseconds for the animation. */
    public int getSpeed() {
        return speed;
    }

    /**
     * Return the current alpha of the color. This method is used to get the alpha of pressed buttons that have text/icons on them, so they also darken accordingly.
     */
    public float getAlpha() {
        return alphaAnimation.get(0);
    }

    public ColorPalette getPalette() {
        return palette;
    }

    public void setPalette(ColorPalette palette) {
        if (this.palette.equals(palette)) return;
        this.palette = palette;
        prevState = 3;
    }

    public void setColors(float[] colors) {
        redAnimation = new DummyAnimation(colors[0]);
        greenAnimation = new DummyAnimation(colors[1]);
        blueAnimation = new DummyAnimation(colors[2]);
        alphaAnimation = new DummyAnimation(colors[3]);
    }
}
