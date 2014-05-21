/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import jee.api.GenericRest;
import jee.dao.GenericModel;
import models.CotacaoDao;
import models.dto.Produto;
import models.dto.Cotacao;

/**
 *
 * @author itakenami
 */
@Path("/cotacoes")
@Produces(MediaType.APPLICATION_JSON)
public class Cotacoes extends GenericRest<Cotacao> {

    @EJB
    CotacaoDao dao;

    @Override
    public GenericModel getModel() {
        return dao;
    }

    @Override
    public Cotacao getDtoToSave(MultivaluedMap<String, String> form) {

        Cotacao cotacao = new Cotacao();
        cotacao.setCPF(form.getFirst("cotacao.CPF"));
        cotacao.setSolicitante(form.getFirst("cotacao.solicitante"));

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");


        try {
            cotacao.setData_solicitacao(sdf.parse(form.getFirst("cotacao.data_solicitacao")));
            //cotacao.setData_solicitacao(form.getFirst("cotacao.data_solicitacao"));
        } catch (ParseException ex) {
        }

        Set<Produto> produtos = new LinkedHashSet<Produto>();
        Set<String> lista = form.keySet();
        for (String param : lista) {
            if (param.indexOf("[") > -1) {
                String val_id = form.getFirst(param);
                Produto a = new Produto();
                a.setId(new Long(val_id));
                produtos.add(a);
            }
        }

        cotacao.setProdutos(produtos);

        return cotacao;


    }

    @Override
    public void setDtoToSave(Cotacao dto, MultivaluedMap<String, String> form) {

        dto.setCPF(form.getFirst("cotacao.CPF"));
        dto.setSolicitante(form.getFirst("cotacao.solicitante"));

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");


        try {
            dto.setData_solicitacao(sdf.parse(form.getFirst("cotacao.data_solicitacao")));
            //dto.setData_solicitacao(form.getFirst("cotacao.data_solicitacao"));
        } catch (ParseException ex) {
        }

        Set<Produto> produtos = new LinkedHashSet<Produto>();
        Set<String> lista = form.keySet();

        for (String param : lista) {
            if (param.indexOf("[") > -1) {
                String val_id = form.getFirst(param);
                Produto a = new Produto();
                a.setId(new Long(val_id));
                produtos.add(a);
            }
        }

        dto.setProdutos(produtos);
    }
}
