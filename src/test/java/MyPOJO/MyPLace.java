package MyPOJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyPLace {
    private String placename;
    private String longitude;
    private String state;
    private String stateabbreviation;
    private String latitude;

    public String getPlacename() {
        return placename;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getState() {
        return state;
    }

    public String getStateabbreviation() {
        return stateabbreviation;
    }

    public String getLatitude() {
        return latitude;
    }

    @JsonProperty("place name") // JSON -> Nesne dönüşümü için değişken adlarında boşluk varsa java ile uyumu sağlamak için kullanıldı.
    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("state abbreviation") // JSON -> Nesne dönüşümü için değişken adlarında boşluk varsa java ile uyumu sağlamak için kullanıldı.
    public void setStateabbreviation(String stateabbreviation) {
        this.stateabbreviation = stateabbreviation;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Place{" +
                "placename='" + placename + '\'' +
                ", longitude='" + longitude + '\'' +
                ", state='" + state + '\'' +
                ", stateabbreviation='" + stateabbreviation + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}


/*
önce properties yaz
ardından get    yap
sonra  set      yap (set kısmında iki kelimeden oluşanları '' içine al... @JsonProperty("state abbreviation"))
en son toString metodu kullan ki bunları yazdırabilesin




import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;



   <dependency> <!-- for extracting json as pojo -->
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.10.2</version>
        </dependency>
        

        import com.fasterxml.jackson.annotation.JsonProperty;
 */