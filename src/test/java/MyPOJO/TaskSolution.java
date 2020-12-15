package MyPOJO;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TaskSolution {

    @Test
    public  void  Task1(){
        given()
                .when()
                .get("https://httpstat.us/203")

                .then()
                .log().body()
                .statusCode(203)
                .contentType(ContentType.TEXT)
        ;
    }


    @Test
    public void Task2()    {
        given()
                .when()
                .get("https://httpstat.us/418")
                .then()
                .log().body()
                .statusCode(418)
                .contentType(ContentType.TEXT)
                .body( equalTo("418 I'm a teapot"))
        ;
    }

    @Test
    public void Task3(){

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title",equalTo("quis ut nam facilis et officia qui"))

                ;
//Tum bunları code hatasından bagımsız olarak postman de manual test ile görursun.
    }

    @Test
    public  void Task4() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false))

//DİKKAT:: Sayı veya  " " sız bi ifade varsa equalTo nun içine " "  sız yazılır.
// Yanı Strıng haricinde hersey tırnaksız yazılacak.

        ;

    }
        @Test
        public void Task5(){

            given()

                    .when()
                    .get("https://jsonplaceholder.typicode.com/todos")

                    .then()
                    .log().body()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("userId[2]",equalTo(1))
                    .body("title[2]", equalTo("fugiat veniam minus"))
                    .body("id[2]",equalTo(3))
//DİZİ nin başlıgı yok ve tum body diziden olusuyorsa,istenilene erişmek için,
//body nin içine direk key inin indexi yazılır,equalTo ile Assert yapılır.

         ;

        }
    /*
    / Task 6
                * create a request to https://jsonplaceholder.typicode.com/todos/2
                * expect status 200
                * Converting Into POJO
     */
    @Test
    public void Task6() {

       Todos_2 todos2=
      //  String  todos2Title=
        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
               .extract().as(Todos_2.class) // Todos_2 todos2= ...; denilerek toStringle cıktı alabilirsin.
         //       .extract().path("title")

                ;


        System.out.println(todos2);

      //  System.out.println(todos2Title);
      //  Assert.assertTrue(todos2Title.equalsIgnoreCase("quis ut nam facilis et officia qui"));



    }

   /* Task 7
            * create a request to https://jsonplaceholder.typicode.com/todos
            * expect status 200
            * Converting Array Into Array of POJOs
    */

    @Test
    void Task7(){ // void i de kaldırınca Task yazınca kabul edıyor.Neden??

        Todos [] todos=

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos")
                .then()
                //.log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().as(Todos[].class) // 2d Array i neden kabul etmiyor??
                ;

        System.out.println(Arrays.toString(todos));



    }
    /** Task 8
     * create a request to https://jsonplaceholder.typicode.com/todos
     * expect status 200
     * Converting Array Into List of POJOs
     */

    @Test
    public void Task8(){ //bunu ben yapamadım.AMA NEDEN DİREK ArrayList in kendisi olmuyor.???

        List<Todos> listTodos= Arrays.asList(
                given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().as(Todos[].class))

                ;

        System.out.println(listTodos);

    }


    @Test
    public void Task8_1(){

        ArrayList<Todos> todos=

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos")
                .then()
               // .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                //.extract().path("completed")
                //.extract().response().path("id")
                .extract().path("title")
                ;
        System.out.println(todos);
        System.out.println(todos.get(5));
        System.out.println(todos.size());
       // System.out.println(todos.add()); // bu nasıl olabilir ki???



    }


    @Test
    public void Task8b(){ // NEDEN TAMAMINI 2D ARRAY YADA ARRAY LİSTE ATAMIYORUM.

  Todos todos=

                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().as(Todos.class)

                ;
        System.out.println(todos);


    }

    @Test
    public void Task8c(){ //NEDEN HATA VERİYOR. MyZippo nun aynısı

       listTodos listtodos=

                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().as(listTodos.class)

                ;

        System.out.println(Arrays.toString(listtodos.getArray()));

 //     ArrayList<ArrayList<Todos>> arrayListstodos=new ArrayList<>();




    }


    }










