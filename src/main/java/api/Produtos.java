package api;

import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import jee.dao.GenericModel;
import models.ProdutoDao;
import models.dto.Produto;
import utils.Result;
import utils.transform.Render;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
public class Produtos extends MyGenericRest<Produto> {

    @EJB
    ProdutoDao dao;

    @Override
    public GenericModel getModel() {
        return dao;
    }

    @Override
    public Produto getDtoToSave(MultivaluedMap<String, String> form) {
        Produto c = new Produto();
        c.setNome(form.getFirst("produto.nome"));
        c.setDescricao(form.getFirst("produto.descricao"));
        c.setQuantidade(Integer.parseInt(form.getFirst("produto.quantidade")));
        c.setValor(Double.parseDouble(form.getFirst("produto.valor")));
        return c;
    }

    @Override
    public void setDtoToSave(Produto dto, MultivaluedMap<String, String> form) {
        dto.setNome(form.getFirst("produto.nome"));
        dto.setDescricao(form.getFirst("produto.descricao"));
        dto.setQuantidade(Integer.parseInt(form.getFirst("produto.quantidade")));
        dto.setValor(Double.parseDouble(form.getFirst("produto.valor")));
    }

    @Path("findName/{nome}")
    @GET
    public Response findName(@PathParam(value = "nome") final String nome) {
        System.out.println(getModel().find(nome).size());
        return Response.ok(Render.JSON(Result.OK(getModel().find(nome)))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }

    @Path("delete/{id}")
    @GET
    public Response deletar(@PathParam(value = "id") final long id) {
        try {
            dao.deletear(id);
            return Response.ok(Render.JSON(Result.OK("Excluido"))).type("application/json").header("Access-Control-Allow-Origin", "*").build();//Response.ok(Render.JSON(Result.OK(getModel().delete(getModel().findById(id))))).type("application/json").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.ok(Render.JSON(Result.OK("Erro ao Excluir: " + ex.getMessage()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @Path("alterar/{id}")
    @POST
    public Response alterar(@PathParam(value = "id") final long id, MultivaluedMap<String, String> form) {
        System.out.println(id);
        try {
            Produto p = dao.findById(id);

            p.setNome(form.getFirst("produto.nome"));
            p.setDescricao(form.getFirst("produto.descricao"));
            p.setQuantidade(Integer.parseInt(form.getFirst("produto.quantidade")));
            p.setValor(Double.parseDouble(form.getFirst("produto.valor")));
            dao.update(p);
            return Response.ok(Render.JSON(Result.OK("Alterado"))).type("application/json").header("Access-Control-Allow-Origin", "*").build();//Response.ok(Render.JSON(Result.OK(getModel().delete(getModel().findById(id))))).type("application/json").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.ok(Render.JSON(Result.OK("Erro ao Alterar: " + ex.getMessage()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @Path("disponiveis")
    @GET
    public Response findDisponiveis() {
        Collection<Produto> produtos = dao.getDisponiveis();

        return Response.ok(Render.JSON(Result.OK(produtos))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }
}
