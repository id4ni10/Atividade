/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import exeptions.MyEJBExeption;
import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import jee.api.GenericRest;
import jee.dao.GenericModel;
import models.PedidoDao;
import models.ProdutoDao;
import models.dto.Produto;
import models.dto.Pedido;
import models.dto.PedidosProduto;
import utils.Result;
import utils.transform.Exclude;
import utils.transform.Render;

/**
 *
 * @author itakenami
 */
@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
public class Pedidos extends MyGenericRest<Pedido> {

    @EJB
    PedidoDao dao;

    @Override
    public GenericModel getModel() {
        return dao;
    }

    @Override
    public Pedido getDtoToSave(MultivaluedMap<String, String> form) {

        Pedido pedido = new Pedido();

        String val = form.getFirst("pedido.qtd_itens");

        int qtd_itens = Integer.parseInt(val);
        pedido.setCPF(form.getFirst("pedido.cpf"));
        pedido.setSolicitante(form.getFirst("pedido.solicitante"));
        pedido.setEndereco((form.getFirst("pedido.endereco")));

        //pedido.setTotal(Double.parseDouble(form.getFirst("pedido.total")));

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        try {
            pedido.setData_pedido(sdf.parse(form.getFirst("pedido.data_pedido")));
        } catch (ParseException ex) {
        }

        Set<PedidosProduto> lista_itens = new LinkedHashSet<PedidosProduto>();


        int contador = 0;
        double totalPedido = 0;
        for (int x = 0; x < qtd_itens; x++) {
            PedidosProduto item = new PedidosProduto();
            String var = form.getFirst("pedido.produto.id[" + contador + "]");
            Long pro_id = Long.parseLong(var);

            int qtd_pro = Integer.parseInt(form.getFirst("pedido.produto.quantidade[" + contador + "]"));
            Produto pro = dao.findProdutoById(pro_id);
            item.getPk().setProduto(pro);
            item.getPk().setPedido(pedido);
            item.setQuantidade(qtd_pro);

            double totalItem = pro.getValor() * qtd_pro;
            item.setTotal(totalItem);
            totalPedido += totalItem;

            lista_itens.add(item);
            contador++;
        }

        pedido.setTotal(totalPedido);

        pedido.setPedidosProduto(lista_itens);
        System.out.println(pedido.getCPF());

        return pedido;
    }

    @Override
    public void setDtoToSave(Pedido dto, MultivaluedMap<String, String> form) {

        dto.setCPF(form.getFirst("pedido.cpf"));
        dto.setSolicitante(form.getFirst("pedido.solicitante"));
        dto.setEndereco((form.getFirst("pedido.endereco")));
        int qtd_itens = Integer.parseInt(form.getFirst("pedido.qtd_itens"));
        //dto.setTotal(Double.parseDouble(form.getFirst("pedido.total")));
        System.out.println(qtd_itens);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        try {
            dto.setData_pedido(sdf.parse(form.getFirst("pedido.data_pedido")));
        } catch (ParseException ex) {
        }

        Set<PedidosProduto> lista_itens = new LinkedHashSet<PedidosProduto>();

        int contador = 0;
        double totalPedido = 0;
        for (int x = 0; x < qtd_itens; x++) {
            PedidosProduto item = new PedidosProduto();
            Long pro_id = new Long(form.getFirst("pedido.produto.id[" + contador + "]"));
            int qtd_pro = Integer.parseInt(form.getFirst("pedido.produto.quantidade[" + contador + "]"));
            Produto pro = dao.findProdutoById(pro_id);
            item.getPk().setProduto(pro);
            item.getPk().setPedido(dto);
            item.setQuantidade(qtd_pro);

            double totalItem = pro.getValor() * qtd_pro;
            item.setTotal(totalItem);
            totalPedido += totalItem;

            lista_itens.add(item);
            contador++;
        }
//        Long pro_id = new Long(form.getFirst("pedido.produto.id[0]"));
//        int qtd_pro = Integer.parseInt(form.getFirst("pedido.produto.quantidade[0]"));
//        String nome = form.getFirst("pedido.produto.nome[0]");
//        String descricao = form.getFirst("pedido.descricao.nome[0]");
//        double valor = Double.parseDouble(form.getFirst("pedido.produto.valor[0]"));
//        Produto pro = dao.findProdutoById(pro_id);
//        pro.setDescricao(descricao);
//        pro.setNome(nome);
//        item.setQuantidade(qtd_pro);
//        item.setTotal(pro.getValor() * qtd_pro);
//        item.getPk().setProduto(pro);
//        item.getPk().setPedido(dto);
//        lista_itens.add(item);
        dto.setTotal(totalPedido);
        dto.setPedidosProduto(lista_itens);
    }

    @Path("delete/{id}")
    @GET
    public Response deletar(@PathParam(value = "id") final long id) {
        try {
            dao.deletar(id);
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
            Pedido p = dao.findById(id);

            p.setCPF(form.getFirst("pedido.CPF"));

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            try {
                p.setData_pedido(sdf.parse(form.getFirst("pedido.data_pedido")));
            } catch (ParseException ex) {
            }

            p.setEndereco(form.getFirst("pedido.endereco"));
            p.setSolicitante(form.getFirst("pedido.solicitante"));
            p.setTotal(Double.parseDouble(form.getFirst("pedido.total")));
            //p.setProdutos(null);
//            p.setNome(form.getFirst("produto.nome"));
//            p.setDescricao(form.getFirst("produto.descricao"));
//            p.setQuantidade(Integer.parseInt(form.getFirst("produto.quantidade")));
//            p.setValor(Double.parseDouble(form.getFirst("produto.valor")));
            System.out.println("preupdate");

            System.out.println("\nChaves :" + form.keySet() + "\n");

            System.out.println("Values :" + form.values() + "\n");

            for (String chave : form.keySet()) {
                System.out.println("chave: " + chave + " valor: " + form.getFirst(chave));
            }

            return Response.ok(Render.JSON(Result.OK("Alterado"))).type("application/json").header("Access-Control-Allow-Origin", "*").build();//Response.ok(Render.JSON(Result.OK(getModel().delete(getModel().findById(id))))).type("application/json").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.ok(Render.JSON(Result.OK("Erro ao Alterar: " + ex.getMessage()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @Path("/")
    @GET
    public Response listAll() {

        List<Pedido> found = dao.findAll();

        Exclude e = new Exclude().exclude("pedido");

        try {
            return Response.ok(Render.JSON(Result.OK(found), e)).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception ex) {
            return Response.ok(Render.JSON(Result.SYSERROR(ex.getMessage()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }

    }

    @Path("/{id}")
    @GET
    public Response findId(@PathParam("id") long id) {
        Pedido p = dao.findById(id);
        Exclude e = new Exclude().exclude("pedido");

        try {
            return Response.ok(Render.JSON(Result.OK(p), e)).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception ex) {
            return Response.ok(Render.JSON(Result.SYSERROR(ex.getMessage()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @Path("/")
    @POST
    public Response save(MultivaluedMap<String, String> form) {

        Pedido obj = getDtoToSave(form);

        try {
            System.out.println(obj);
            getModel().save(obj);
            Exclude e = new Exclude().exclude("pedido");
            return Response.ok(Render.JSON(Result.OK(obj), e)).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        } catch (MyEJBExeption ex) {
            System.out.println(ex.getMymessage());
            return Response.ok(Render.JSON(Result.SYSERROR(ex.getMessage()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return Response.ok(Render.JSON(Result.SYSERROR(ex.getMessage()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }
    }
}
