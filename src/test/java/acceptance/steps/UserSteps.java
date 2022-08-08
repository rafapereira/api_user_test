package acceptance.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.request.UserRequest;
import domain.response.UpdatedUserResponse;
import domain.response.UserCreationResponse;
import domain.response.UserResponse;
import io.cucumber.java.After;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.net.URI;
import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;

public class UserSteps {

    private Response response;

    private UserResponse user;

    private UserCreationResponse userCreationResponse;

    private UpdatedUserResponse updatedUserResponse;

    private final String BASE_URL = "https://reqres.in/";

    private RequestSpecification request;

    @After
    public void showResponse(){
        if(response.then().extract().asString() != null){
            System.out.println("Valor da resposta: " + response.then().extract().asString());
        }
    }
    @Dado("que utilizem o CRUD de gerenciamento de usuarios")
    public void que_utilizem_o_CRUD_de_gerenciamento_de_usuarios() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "api/users/";
    }


    @Quando("utilizamos o get passando o id de usuario existente {string}")
    public void utilizamos_o_get_passando_o_id_de_usuario_existente(String id) throws URISyntaxException {
        RequestSpecification req = given();
        response = req.when().get(new URI(id));
        user = response.then().extract().as(UserResponse.class);
    }
    @Entao("retorna status 200 com as informacoes do usuario")
    public void retorna_status_200_com_as_informacoes_do_usuario() {
        response.then().statusCode(200);
        Assert.assertTrue(user.getData() != null);
    }


    @Quando("utilizamos o get passando um id de usuario inexistente {string}")
    public void utilizamos_o_get_passando_um_id_de_usuario_inexistente(String id) throws URISyntaxException {
        RequestSpecification req = given();
        response = req.when().get(new URI(id));
        user = response.then().extract().as(UserResponse.class);
    }
    @Entao("retorna status 404 com valor de resposta vazio")
    public void retorna_status_404_com_valor_de_resposta_vazio() {
        response.then().statusCode(404);
        Assert.assertTrue(user.getData() == null);
    }


    @Quando("utilizamos o post para criar um usuario com o nome {string} e funcao {string}")
    public void utilizamos_o_post_para_criar_um_usuario_com_o_nome_e_funcao(String nome, String funcao) throws JsonProcessingException {
        RequestSpecification req = given();

        UserRequest newUser = UserRequest.builder()
                .name(nome)
                .job(funcao)
                .build();

        response = req
                .contentType(ContentType.JSON)
                .body(new ObjectMapper().writeValueAsString(newUser))
                .when()
                .post();

        userCreationResponse = response.then().extract().as(UserCreationResponse.class);
    }
    @Entao("retorna status 201 com o novo id e data de criacao")
    public void retorna_status_201_com_o_novo_id_e_data_de_criacao() {
        response.then().statusCode(201);
        Assert.assertTrue(
                userCreationResponse.getId() != null &&
                        userCreationResponse.getCreatedAt() != null
        );
    }


    @Quando("utilizamos o post para criar um usuario com o nome {string} e funcao {string} com um dos dados faltando")
    public void utilizamos_o_post_para_criar_um_usuario_com_o_nome_e_funcao_com_um_dos_dados_faltando(
            String nome,
            String funcao
    ) throws JsonProcessingException {
        RequestSpecification req = given();

        UserRequest newUser = UserRequest.builder()
                .name(nome)
                .job(funcao)
                .build();

        response = req
                .contentType(ContentType.JSON)
                .body(new ObjectMapper().writeValueAsString(newUser))
                .when()
                .post();

        userCreationResponse = response.then().extract().as(UserCreationResponse.class);
    }
    @Entao("retorna status 201 com valor com usuario faltando dados")
    public void retorna_status_201_com_valor_com_usuário_faltando_dados() {
        response.then().statusCode(201);
        Assert.assertTrue(
                userCreationResponse.getName() == "" ||
                        userCreationResponse.getJob() == ""
                );
    }


    @Quando("utilizamos o put para atualizar o usuario com {string} e funcao {string}")
    public void utilizamos_o_put_para_atualizar_o_usuario_com_e_funcao(
            String nome,
            String funcao
    ) throws JsonProcessingException, URISyntaxException {
        RequestSpecification req = given();

        UserRequest updatedUser = UserRequest.builder()
                .name(nome)
                .job(funcao)
                .build();

        response = req
                .contentType(ContentType.JSON)
                .body(new ObjectMapper().writeValueAsString(updatedUser))
                .when()
                .put(new URI("1"));

        updatedUserResponse = response.then().extract().as(UpdatedUserResponse.class);
    }
    @Entao("retorna status 200 com o usuario com dados atualizados")
    public void retorna_status_200_com_o_usuario_com_dados_atualizados() {
        response.then().statusCode(200);
        Assert.assertTrue(
                updatedUserResponse.getName() != null  &&
                        updatedUserResponse.getJob() != null &&
                        updatedUserResponse.getUpdatedAt() != null
        );
    }


    @Quando("utilizamos o delete para deletar um usuario com o id {string}")
    public void utilizamos_o_delete_para_deletar_um_usuario_com_o_id(String id) throws URISyntaxException {
        RequestSpecification req = given();
        response = req.when().delete(new URI(id));
    }
    @Entao("retorna status 204")
    public void retorna_status_204() {
        response.then().statusCode(204);
    }

}
