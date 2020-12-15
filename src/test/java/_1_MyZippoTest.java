import MyPOJO.MyLocation;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


//BU IMPORTLARI BURAYA BAŞTAN EKLEMEK ZORUNDASIN...(import=ithal-dahil)


public class _1_MyZippoTest {
    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;

    @BeforeClass
    public  void setup(){

        baseURI="http://api.zippopotam.us";
//baseURI static RestAssured un kendi değişkenidir.


        requestSpecification=new RequestSpecBuilder() // istek yollarken  istenenler.given e yazılır.
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON) //request de body i raw-->text(JSON) yapıyorduk.bu kısım orası
                .build();


        responseSpecification =new ResponseSpecBuilder() // gelen sonuçları görmek için then den sonrasına yazılır.
                .expectStatusCode(200)                  // farklı statusCode varsa onun için paket yapacaksın
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();


    }
//URI, İngilizce “Uniform Resourse Identifier
// spec:: şartlar
//military spec:: herşarta dayanıklı demek








    @Test
    public void test()
    {
        given()                 // başlangıc ayarları
                .when()        // aksiyon kısmı
                .then()       // test kısmı
                ;
    }

 //Checking the Response Status Code
    @Test
    public void statusCodeTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
// get kısmı işlevsel ve syntax olarak postman dekinin Aynısı oldu.
// Alınlık kısmına yazılanlar buraya yazılır.

                .then()
                .log().all() // butun dönen sonuclari göruntuler
                .statusCode(200)

//Neden then den sonra
// çünku işlemden sonra bana getir.
//bu kısım postman deki Test kısmını temsil eder.
//statusCode kısmı assertion oluyor.
//statusCode(201) yap faili gör.
//Validatable Response::Doğrulanabilir Yanıt

        ;

    }


//Logging Request and Response Details
    @Test
    public void logTest()
    {
        given()
                .log().all()
//gönderilenlerin logu başta olacagı için given de

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
              //  .log().all()
                .log().body()
// gelen degerlerin logu sonra oldugu için then e yazıldı


        ;
    }



    //Checking the Response Content Type
    @Test
    public void contentTypeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .assertThat()   // bu ne işe yarıyor.
                .log().body()  // sadece body kısmını görüntüledik
                .contentType(ContentType.JSON)

//contentType: içeriğin şekli,tipi demek
// Ne oldugunu checked... XML yaz ve gör.
        ;

    }



    //Checking the Response Body
    @Test
    public void bodyJsonPathTest(){

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()                                        // bu kısmı sadece içini görmek için yazdık.
                .body("country", equalTo("United States"))
                .statusCode(200)

                ;

//hamcrest sayesinde içeridekini dışarı almadan Assert yapıyor.
//Bunun 4 farklı Assert yöntemi var.
//TESTLERDE hata yoksa cıktı vermez.
//country ilk { ' den sonra geldiği için body e direk yazdık.

/*
Hamcrest Matchers, gerçekleştirmek istediğiniz beklentileri veya kontrolleri veya doğrulamaları ifade etmenize olanak tanır
otomatik testlerinizde okunabilir bir dilde.

 equalTo(X) - used to check whether an actual element value is equal to a pre-specified expected element value
● hasItem("value") - used to see whether a collection of elements contains a specific pre-specified item value
● hasSize(3) - used to verify the actual number of elements in a collection
● not(equalTo(X)) - inverts any given matcher that exists in the Hamcrest library
 */
    }

    @Test
    public  void checkStateInResponseBody(){

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()                                            // bu kısmı sadece içini görmek için yazdık.
                .body("places[0].state",equalTo("California"))
                .statusCode(200)


        ;

// [] dizi içinde bişeyi arıyoruz demektir.
// body deki places dizisinin 0. elemanının içindeki state buna mı eşit

    }

    @Test
    public  void checkPlaceNameInResponse(){

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places[0].'place name'",equalTo("Beverly Hills"))
                .statusCode(200)

                ;

// Responseden alınan key ayrı yazılmış ise ' ' tek tırnak arasında alınır
// bu sayede onun bir butun oldugu ifade edilmiş olur.
// daha fazla sorguyu extra .body ile sorgularsın.

    }

    @Test
    public void bodyArraySizeTest(){

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places",hasSize(1))
                .statusCode(200)

                ;

    }

//for  Lots of testing
    @Test
    public  void combining(){

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places",hasSize(1))
                .body("places[0].'state abbreviation'",equalTo("CA"))
                .body("country",equalTo("United States"))
                .statusCode(200)

        ;
    }


// for parameters testing

    @Test
    public  void pathParamTest()
    {
       String country="us";
       String zipCode="90210";


        given()
               .pathParam("country",country)  //String yazılmadan önce ("country",us) idi. Bunu Stringe aldık.
               .pathParam("zipCode",zipCode) // String yazılmadan önce ("zipCode",90210) idi. Bunu Stringe aldık.
                .log().uri()                   //Çalışmadan önce oluşturulan URL yı verir.

                .when()
                .get("http://api.zippopotam.us"+"/{country}/{zipCode}")

                .then()
                .log().body()
                .statusCode(200)

                ;
//Parameters wrote given,because in setup process.
//like postman's parameters
//what is the different use .pathParam and .Param :: ı think
//if you use/need append with / you have to use .pathParam
//if you use/need append with ? you have to use .param

    }

    @Test
   public  void queryParamTest(){

        int sayfa=10;

        given()
                .param("page",sayfa)  // bu parameter ...?page=10 şeklide yerini alır
                .log().uri()

                .when()
                .get("https://gorest.co.in/public-api/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(sayfa))

                ;
// .param olduguna dikkat et.
// bu sayede ? ve sonrasını parametre ile nasıl yazılacagını ögrenmiş olduk.
// https://gorest.co.in/public-api/users?page=10  şeklinde oldu.
/*
ilk { body nin
ikinci { meta nın
üçüncü { pagination  un

pagination un içinde de key olarak page var onun value sini alacaz.
 */

    }
    @Test
    public  void  multipleQueryParamTest(){

        for (int page =1; page <=10 ; page++) {

        given()
                .param("page",page)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public-api/users")

                .then()
               // .log().body()                                  //görerek kontrol ettik
                .body("meta.pagination.page",equalTo(page))   // görmeden sadece kodla kontrol

        ;

        }
 //for döngusu ile ilk 10 sayfayı getirdik ve
// gercekten ilk 10  sayfa mı diye geldikten sonra kontrol ettik.
//All the query have to be in for loop

    }

    @Test
    public  void combiningWithBaseUri(){

        given()
                .log().uri()

                .when()
                .get("/us/90210")
                .then()
                .log().body()
                .body("places",hasSize(1))
                .body("places[0].'state abbreviation'",equalTo("CA"))
                .body("country",equalTo("United States"))
                .statusCode(200)

        ;
//EGER baş kısmında http varsa varolanı calıştırır... http yoksa,
// OTOMATIK olarak direk BaseURI yi alır..
// like envoriment on postman

    }
    @Test
    public  void combiningWithBaseUriAndResponseAndrequestSpecification(){

        given()
                .spec(requestSpecification)

                .when()
                .get("/us/90210")

                .then()
                .log().body()
                .body("places",hasSize(1))
                .body("places[0].'state abbreviation'",equalTo("CA"))
                .body("country",equalTo("United States"))
                .spec(responseSpecification)

        ;
//paketlerin içindeki tum sorguları yapar.
//EGER baş kısmında http varsa varolanı calıştırır... http yoksa,
// OTOMATIK olarak direk BaseURI yi alır..

    }


    @Test
    public  void  extractingJsonPath(){ // dikkat bu POJO değil

        String extractValue=

                given()
                .when()
                .get("/us/90210")

                .then()
                .log().body()
                .extract().path("places[0].'place name'") //üstteki String i silersen burası original haline döner.object
                ;

        System.out.println(extractValue);
        Assert.assertEquals(extractValue,"Beverly Hills");
    }
//query nın tamamı bi stringe assinged . sonunda ; de var. dizi deki bir degeri çekip aldık.
//Hamcrest ile equalTo sayesinde otomatik Assert yapıyorduk.
//Burada degeri Stringe atıp normal Assert yaptık.

    @Test
    public void extractJsonPathList() // Dikkat bu POJO değil.
    {
        List<String >placeNameListe=
        given()
                .when()
                .get("/tr/01000 ") //baseURİ yi BeforeTest e koyduk.So o kısım hep önceden çalışacak,uygulayacak.

                .then()
                .log().body()
                .extract().path("places.'place name'")


//places[0] dan,[0] silinir. böylede
//dizideki butun 'place name' leri alır. BU GARİP DURUM.AKLIM TAM ALMADI
//dönüş tipi String List olur.. Dönuş tipini doğru vermek ÇOK ÖNEMLİ...
// once object iken, object den istediğin type dönusur.
                ;

        System.out.println(placeNameListe);
        Assert.assertTrue(placeNameListe.contains("Çaputçu Köyü"));
        //listin içindeki bi elemanı sadece contains diyerek bulabiliyor muşuz.

    }
    @Test
    public void extractingJsonAsPojo(){  //POJO DA CLASS(lar) oluşturup yapacaksın.
        MyLocation myLocation=

        given()

                .when()
                .get("/us/90210")

                .then()
                .log().body()
                .extract().as(MyLocation.class)

                ;

        System.out.println(myLocation); //Hem MyLocation hemde Places in içini alır.Cunkü...
//bu çıktıyı toString metodu sayesinde alıyorum.Yoruma al,gör.

        System.out.println(myLocation.getPlaces()); //dizinin içindekileri de aldım.

        System.out.println(myLocation.getCountry());
        System.out.println(myLocation.getPlaces().get(0).getLongitude());// Noktalarla ilerliyoruz.

        Assert.assertEquals("Beverly Hills",myLocation.getPlaces().get(0).getPlacename());
        Assert.assertEquals("US",myLocation.getCountryabbreviation());

//MyLocation bi class properties i list ve Stringlerden oluşuyor.
//Bu sayede then() ... object kısmını MyLocation a dönusturebildik.
//object hep böyle dönusturulecek...

    }


















}


// spec:: şartlar
//military spec:: herşarta dayanıklı demek
/*

Manuel Tester ne yapar? User Interface-DataBase-API kısımlardan  hangilerini / nasıl yapıyor.


Jira da aşagıdaki kısımları kimler yazar? Bi kısmını söylediniz...

Epic      :: Scrum Master Yazar
Task      :: Scrum Master Yazar
User Story:: Scrum Master Yazar
TestCase  ::             ?
Acceptance Criteria ::   ?
Steps::                  ?

Functional Testing ne demek?
Functional Testing de neler yapılır?

End-point nedir?

staging veya uat  ne yapar onu da biraz acıklayabılır mısınız.

Diyelimki romute olarak işe başladık sizce en cok nelerde sorun yasarız.
B2 seviyesinde de dilimiz var.


 işe başlamadan önce sizi en cok ne zorlayacak diye dusunmustunuz?
 işe ilk başladıgınızda  sizi en cok zorlayan ne idi ...














    @Test
    public void extractingJsonAsPojo()
    {
        Location location=
        given()
                .when()
                .get("/us/90210")
                .then()
                .log().body()
                .extract().as(Location.class)
                ;

        System.out.println(location);
        System.out.println(location.getCountry());
        System.out.println(location.getPlaces());
        System.out.println(location.getPlaces().get(0).getState());
    }
 */