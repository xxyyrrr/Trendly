package jin.yerim.trendly;


public class FormItem {

    String charity;
    String clothesnum;
    String quality;

    public FormItem(String charity, String clothesnum, String quality) {
        this.charity = charity;
        this.clothesnum = clothesnum;
        this.quality = quality;
    }

    public String getCharity() {
        return charity;
    }
    public void setCharity(String charity) {
        this.charity = charity;
    }

    public String getClothesnum() {
        return clothesnum;
    }
    public void setClothesnum(String clothesnum) {
        this.clothesnum = clothesnum;
    }

    public String getQuality() {
        return quality;
    }
    public void setQuality(String quality) {
        this.quality = quality;
    }


}