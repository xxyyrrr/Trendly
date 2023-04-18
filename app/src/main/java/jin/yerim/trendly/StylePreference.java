package jin.yerim.trendly;
public class StylePreference {
    private String color;
    private String pattern;
    private String material;

    public StylePreference(String color, String pattern, String material) {
        this.color = color;
        this.pattern = pattern;
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}


