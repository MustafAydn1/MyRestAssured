package goRest;

import goRest.model.User;
import goRest.model.postData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _2_GoRestUserTests {

    int userId;

    @Test()
    public void getUsers() {     // jsonPath() kullanımı.... ile listeye ettık
       List<User> userList =
                given()

                        .when()
                        .get("https://gorest.co.in/public-api/users") // request linkini çalıştırdık

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("code", equalTo(200)) //dönen response body deki code nın degeri kontrol edildi
                        .body("data", not(empty())) // data bölumunun boş olmadıgı kontrol edildi
                        .extract().jsonPath().getList("data", User.class)

//response nın yapısı list e uygun oldugu içi getList i sectık. Alternatifleri de vardı.Bak
//burası ob
// Yukarıdaki sıralama önemsiz.Sadece extract en sona yazılacak
                ;

        for (User us : userList)
            System.out.println(us);

        System.out.println(userList);

    }


    @Test
    public void createUser() {
        userId =
                given()
                        .header("Authorization", "Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\"techno\", \"gender\":\"Male\", \"email\":\"" + getRandomEmail() + "\", \"status\":\"Active\"}")
                        .when()
                        .post("https://gorest.co.in/public-api/users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("code", equalTo(201))
                        .extract().jsonPath().getInt("data.id")
        ;

        System.out.println(userId);

//extra çift tırnak koyup + getRandomEmail() + yaptık...
//postman de Authorization kısmına token giriyorduk.body oluşturulacak kısmı yazıyorduk ve json formatı belirliyorduk.
// bunlar request in setup kısmı oldugu için given a yazılacaklar.
//Herşeyi oluşturuyorum.userId yi de cebime koyup çıkıyorum.get-put-delete için kullanacagım.
    }

    private String getRandomEmail() {

        return RandomStringUtils.randomAlphabetic(8).toLowerCase() + "@gmail.com"; //harika bişey.alyernatifleri var.
    }

//    https://gorest.co.in/public-api/users -> post
//    headera ekle Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb
//    {"name":"{{$randomFullName}}", "gender":"Male", "email":"{{$randomEmail}}", "status":"Active"}
//    body content JSON
//    işlemin sonucnda id yi almıştık
//    genel kontroller


    @Test(dependsOnMethods = "createUser", priority = 1) // önceki çalışmadan bu Test Calışmasın
    public void getUserById() {

        given()
                .pathParam("userId", userId)

                .when()
                .get("https://gorest.co.in/public-api/users/{userId}")

                .then()
                .statusCode(200)
                .body("data.id", equalTo(userId))
                .body("code", equalTo(200))

        ;

        System.out.println(userId);
//userId yi kullanarak. Oluşturulan User ı çagırdık.

    }

    @Test(dependsOnMethods = "createUser", priority = 2)
    public void updateUserById() {

        String newName = "Mehmet Yılmaz";

        given()
                .header("Authorization", "Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + newName + "\"}")  //burada \ sayıları bir tane olmalıdır.Yoksa hata verir.
//bu kısma ne değişcekse sacede onu yazsan yeterli...
                .pathParam("userId", userId)

                .when()
                .put("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("data.name", equalTo(newName))

        ;
    }
//    https://gorest.co.in/public-api/users/{{userId}}   -> put
//    Authorization Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb
//    body-> {"name":"mehmet yılmaz"} JSON
//    normal kontroller ve name kontrolü


    @Test(dependsOnMethods = "createUser", priority = 3) //burası 1 olunca diğerleri 0 oldugundan,en son çalışacak
    public void deleteUserById() {

        given()
                .header("Authorization", "Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .pathParam("userId", userId)

                .when()
                .delete("https://gorest.co.in/public-api/users/{userId}")//tek parantez olmak zorunda


                .then()
                .statusCode(200)
                .body("code", equalTo(204))  //operand:: işlenen


        ;
//DELETE Yİ ÇALIŞTIRINCA:: update and getuserbyid didn't run. BECAUSE::
    }

    @Test(dependsOnMethods = "deleteUserById", priority = 4)
    public void deleteNegativeById() {

        given()
                .header("Authorization", "Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .pathParam("userId", userId)

                .when()
                .delete("https://gorest.co.in/public-api/users/{userId}")

                .then()
                .statusCode(200)
                .body("code", equalTo(404))


        ;

    }
// en ustten calıştırınca oluyor. ama en alttan yada altlardan neden olmuyor.
//sadece negative yi calıştırınca hata veriyor.
// ÇÜNKÜ:

    @Test(enabled = false)
    public void extractListSample() {
        List<User> userList =
                given()
                        .when()
                        .get("https://gorest.co.in/public-api/users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().jsonPath().getList("data", User.class);
        System.out.println(userList);
        System.out.println(userList.get(3));
        System.out.println(userList.get(3).getName());
//extract fonksiyonu bir @Test te sadece birkez kullanılabılıyor.
//Birden fazla extract kullanarak daha fazla veri çıkarak istersen de
// aşagıdaki yöntemi uygulamalısın.

    }


//****************************** DİKKAT AŞAGISI BAMBAŞKA BİŞEY ********************************************************

//**********************************  response.jsonPath()  *********************************************************


    @Test(enabled = false)
    public void responseSample() {
        Response response =                  //restassured e ait olan oldugundan emin ol.
                given()
                        .when()
                        .get("https://gorest.co.in/public-api/users")

                        .then()
                        // .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().response();
//birden fazla extract bilgiye ulaşmak için;
//Response nin tamamı bir Response (restassured) nesnesine (response) dönuşturuldü.
//istenen veriler,UYGUN formatta istenerek çekip alındı...


        List<User> userList = response.jsonPath().getList("data", User.class); //bu kısım POJO olmuş oldu. All Users
//responsem,jsonPath olsun.Bana getList i yap,kimi getList yapayım? data yı yap.
//data yı Neye göre list yapayım.User class a göre çevir ve yap.

        System.out.println(userList);
        System.out.println(userList.size());

        User user2 = response.jsonPath().getObject("data[1]", User.class);//1 indexli 2. sıradaki user ı verdi.
        System.out.println("user2 bilgileri: " + user2);

        String nameOfSecondUser = response.jsonPath().getString("data[1].name");
        System.out.println("nameOfUserSecond: " + nameOfSecondUser);

        //  User emailList=response.jsonPath().getObject("data.email",User.class);
//ÇÜNKÜ LİST OLARAK ALMALIYDIN.baştan yanlış.
        //  List<User> emailList=response.jsonPath().getList("data.email",User.class);
//ÇÜNKÜ lİSTİN TİPİNİ YANLIŞ ALDIN. neden yanlış???
        // System.out.println(emailList);


        System.out.println(response.jsonPath().getList("data.email"));

        List<String> names = response.jsonPath().getList("data.name");
        System.out.println(response.jsonPath().getList("data.name"));
        System.out.println(names);


        int total = response.jsonPath().getInt("meta.pagination.total");
        int code = response.jsonPath().getInt("code");

        System.out.println("total: " + total);
        System.out.println("code: " + code);

    }
}













/*



    private String getRandomEmail(){

        return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"gmail.com";

    }
 */