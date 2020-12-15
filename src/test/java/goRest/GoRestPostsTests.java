package goRest;

import goRest.model.postData;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class GoRestPostsTests {

    int Id;

    @Test()
    public  void getPostsList(){

        List<postData> postsList=
                given()
                        .when()
                        .get("https://gorest.co.in/public-api/posts")

                        .then()
                        // .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("code",equalTo(200))
                        .body("data",not(empty()))
                        .extract().jsonPath().getList("data",postData.class)

                ;
        for(postData post: postsList)
            System.out.println(post);

        System.out.println(postsList);
        System.out.println(postsList.get(3));
    }

    @Test
    public void createPost(){

        Id=

                given()
                        .header("Authorization","Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                        .contentType(ContentType.JSON)
                        .body("{\"user_id\":\"111\",\"title\":\" Vesvese\" ,\"body\":\"insi Vesvese\"}")
                        .when()
                        .post("https://gorest.co.in/public-api/posts")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("code",equalTo(201))
                        .extract().jsonPath().getInt("data.id")

        ;
        System.out.println(Id);

    }


    @Test(dependsOnMethods ="createPost" )
    public void getPostById(){

        given()
                .pathParam("id",Id) //hangi id yı nereye yazdıgına dikkat et.DIŞTAKİNİ İÇE ATIYORSUN

                .when()
                .get("https://gorest.co.in/public-api/posts/{id}")

                .then()
                .log().body()
                .statusCode(200)
                .body("data.id",equalTo(Id))

        ;
        System.out.println(Id);
    }

    @Test(dependsOnMethods = "createPost")
    public  void updateTheSamePostById(){

        String body="cinni ve insi vesvese";
        given()
                .header("Authorization","Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .contentType(ContentType.JSON)
                .body("{\"body\":\""+ body +"\"}")
                .pathParam("id",Id)
                .when()
                .put("https://gorest.co.in/public-api/posts/{id}")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("code",equalTo(200))
                .body("data.body",equalTo(body))
        ;

    }

    @Test(dependsOnMethods = "createPost",priority = 1)
    public void deleteTheSamePostById(){

        given()
                .header("Authorization","Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .pathParam("id",Id)

                .when()
                .delete("https://gorest.co.in/public-api/posts/{id}")

                .then()
                .statusCode(200)
                .body("code",equalTo(204))
        ;

    }


    @Test(dependsOnMethods ="deleteTheSamePostById" )
    public void deletePostNegativeTest(){

        given()
                .header("Authorization","Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .pathParam("id",Id)

                .when()
                .delete("https://gorest.co.in/public-api/posts/{id}")

                .then()
                .statusCode(200)
                .body("code",equalTo(404))
        ;

    }



}

