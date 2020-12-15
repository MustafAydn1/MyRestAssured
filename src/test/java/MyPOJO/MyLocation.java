package MyPOJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;



public class MyLocation {
    private String postcode;
    private String country;
    private String countryabbreviation;
    private List<MyPLace> places;


    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }


    public String getCountryabbreviation() {
        return countryabbreviation;
    }

    public List<MyPLace> getPlaces() {
        return places;
    }

    @JsonProperty("post code") // JSON -> Nesne dönüşümü için değişken adlarında boşluk varsa java ile uyumu sağlamak için kullanıldı.
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("country abbreviation") // JSON -> Nesne dönüşümü için değişken adlarında boşluk  varsa java ile uyumu sağlamak için kullanıldı.
    public void setCountryabbreviation(String countryabbreviation) {
        this.countryabbreviation = countryabbreviation;
    }

    public void setPlaces(List<MyPLace> places) {
        this.places = places;
    }


    @Override
    public String toString() {
        return "Location{" +
                "postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", countryabbreviation='" + countryabbreviation + '\'' +
                ", places=" + places +
                '}';


    }


}
/*
TUM BUNLARI NEDEN YAPTIK::
then de istenenler object formatında geliyordu.
Bunları ayrı ayrı alıp kullanabilmek için object i List e dönüşturdük.

TÜM BUNLARI NASIL YAPTIK::
{
    "post code": "01000",
    "country": "Turkey",              --->>> BU KISMA  MyLocation dedik.class a fields yazdık. get ve set ve toString yaptık...(1)
    "country abbreviation": "TR",
    "places": [
        {
            "place name": "Dervişler Köyü",
            "longitude": "37.4987",
            "state": "Adana",            ---->>> BU KISMA  MyPlace dedik.class a fields yazdık. get ve set ve toString yaptık...(2)
            "state abbreviation": "1",
            "latitude": "36.9748"
        }
Belirli fieldslere sahıp olan classı böyle oluştururuz ki, Sonrada o fieldsleri kullanırız.
MyPlace nin elemanlarını bir List olarak, MyLocation a attık.

ismi 2 kelimeden oluşan Propertyleri dogru çekebilmek için,Set metodlarının başına;
 @JsonProperty("country abbreviation")  ekledik.
 Bunun calışması içinde pom.xml extradan depency ekledik.

 Burada toString Metodunu neden yazdık?
 Bu olmadan prıntln olmuyor..





 */