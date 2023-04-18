package jin.yerim.trendly;

import java.util.HashMap;
import java.util.Map;


public class Style {
    private boolean isCasual;
    private boolean isFormal;
    private boolean isSporty;

    public Style(boolean isCasual, boolean isFormal, boolean isSporty) {
        this.isCasual = isCasual;
        this.isFormal = isFormal;
        this.isSporty = isSporty;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("isCasual", isCasual);
        map.put("isFormal", isFormal);
        map.put("isSporty", isSporty);
        return map;
    }
}
