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

    public boolean getIsCasual() {
        return isCasual;
    }

    public void setIsCasual(boolean isCasual) {
        this.isCasual = isCasual;
    }

    public boolean getIsFormal() {
        return isFormal;
    }

    public void setIsFormal(boolean isFormal) {
        this.isFormal = isFormal;
    }

    public boolean getIsSporty() {
        return isSporty;
    }

    public void setIsSporty(boolean isSporty) {
        this.isSporty = isSporty;
    }

}
