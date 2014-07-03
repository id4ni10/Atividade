/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jee.dao.GenericModel;
import models.dto.Pedido;
import models.dto.PedidosProduto;
import models.dto.Produto;

/**
 *
 * @author itakenami
 */
@Stateless
public class PedidoDao extends GenericModel<Pedido> {

    @PersistenceContext
    private EntityManager em;
    @EJB
    ProdutoDao dao;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Class<Pedido> getEntityClass() {
        return Pedido.class;
    }

    public void deletar(long id) throws Exception {
        try {
            Pedido p = findById(id);
            em.remove(p);
        } catch (Exception ex) {
            throw new Exception("Erro ao deletar: " + ex.getMessage());
        }
    }

    public Produto findProdutoById(long id) {
        return dao.findById(id);
    }

    @Override
    public void save(Pedido obj) throws Exception {
        System.out.println("Salvando o pedido: "+ obj);
        for (PedidosProduto item : obj.getPedidosProduto()) {
            System.out.println("Buscando o produto: "+ item.getPk().getProduto());
            dao.baixarEstoque(item.getPk().getProduto(), item.getQuantidade());
        }
        super.save(obj);
    }
}
