package basqar;

import basqar.model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _3_countryTest {

    Cookies cookies;
    private  String randomGenName;
    private  String randomGenCode;
    Country country=new Country(); //create ve update de kullanacagız.
    String  id;

    @BeforeClass
    public  void  login(){

        baseURI="https://test.basqar.techno.study";

        randomGenName= RandomStringUtils.randomAlphabetic(8);
        randomGenCode= RandomStringUtils.randomAlphabetic(4);
//BeforeTest değil BeforeClass oldugu için bu sayfada ilk
//çalısan Test ile oluşacak.Bidaha oluşmayacak.Hep aynı olacak

        country.setName(randomGenName);//
        country.setCode(randomGenCode);//


        Map<String,String > credentials= new HashMap<>();
        credentials.put("username","daulet2030@gmail.com");
        credentials.put("password","TechnoStudy123@");
        credentials.put("rememberMe","true");

        cookies=
        given()
                .body(credentials) //tek tek yazmak yerine Map i buraya yazarız.
                .contentType(ContentType.JSON) // raw-->text formatını belirleriz.


                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response().detailedCookies();

                ;
//Login olurken bı token olusur. biz bunu extract() ile alıp cebe atarız.
//create-update - delete yi aynı token ile yapacagız.
//cookies artık Authorization yerine gececek.
//Zaten postman de login olurken cookies(2) oluşuyor.

        System.out.println(cookies);
    }



    @Test
    public void createCountry() {



        id=
         given()
                 .cookies(cookies) //Authorization  yerine bunu yazıyoruz.
                 .body(country)
                 .contentType(ContentType.JSON)

                .when()
                 .post("/school-service/api/countries")

                .then()
                 .log().body()
                .statusCode(201)
                 .body("name",equalTo(randomGenName))
                 .body("code",equalTo(randomGenCode))
                // .extract().jsonPath().getString("body.id")   // bu da oluyor.
                // .extract().path("id")                       // bu da oluyor
                 .extract().jsonPath().getString("id")

                ;
        System.out.println(id);
    }


    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative(){



                given()
                        .cookies(cookies) //Authorization  yerine bunu yazıyoruz.
                        .body(country)
                        .contentType(ContentType.JSON)

                        .when()
                        .post("/school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(400)
                       .body("message",equalTo("The Country with Name \""+randomGenName +"\" already exists."))
        ;
    }

     @Test(dependsOnMethods = "createCountry")
    public void updateCountry(){


            country.setId(id);
            country.setName(RandomStringUtils.randomAlphabetic(8));
            country.setCode(RandomStringUtils.randomAlphabetic(4));
            given()
                    .cookies(cookies)
                    .body(country)
                    .contentType(ContentType.JSON)

                    .when()

                    .put("/school-service/api/countries")

                    .then()
                    .statusCode(200)
                    .body("name", equalTo(country.getName()))
                    .body("code", equalTo(country.getCode()))

            ;




     }



    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry(){

        given()
                .cookies(cookies)
                .pathParam("id",id)
                .when()

                .delete("/school-service/api/countries/{id}")

                .then()
                .statusCode(200)


        ;



    }


    @Test(dependsOnMethods = "deleteCountry")
    public void negativeDeleteCountry(){

        try {

            given()
                    .cookies(cookies)
                    .pathParam("countryId", id)
                    .when()
                    .delete("/school-service/api/countries/{countryId}")

                    .then()
                    .statusCode(404)

            ;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

    }



}
