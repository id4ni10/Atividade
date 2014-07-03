/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import exeptions.MyEJBExeption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jee.dao.GenericModel;
import models.dto.Produto;

/**
 *
 * @author itakenami
 */
@Stateless
public class ProdutoDao extends GenericModel<Produto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Class<Produto> getEntityClass() {
        return Produto.class;
    }

    @Override
    public List<Produto> find(String query) {

        if (!query.equals("")) {
            Query q = em.createQuery("select p from models.dto.Produto p where p.nome like :name");
            q.setParameter("name", "%" + query + "%");
            return q.getResultList();
        } else {
            return em.createQuery("select p from models.dto.Produto p").getResultList();
        }
    }

    public void deletear(long id) throws Exception {
        try {
            Produto p = findById(id);
            em.remove(p);
        } catch (Exception ex) {
            throw new Exception("Erro ao deletar: " + ex.getMessage());
        }
    }

    public void baixarEstoque(Produto pro, int quantidade) throws Exception {

        System.out.println("QUantidade produto" + pro.getQuantidade() + " quantidade baixa" + quantidade);
        if (pro.getQuantidade() >= quantidade) {
            pro.setQuantidade(pro.getQuantidade() - quantidade);
            System.out.println("Baixar estoque");
            super.update(pro);
        } else {

            System.out.println("EJB Exeption");
            throw new MyEJBExeption("Quantidade do produto " + pro.getNome() + " é insuficiente em estoque!");
        }
    }

    public Collection<Produto> getDisponiveis() {
        List<Produto> produtos = this.findAll();
        List<Produto> disponiveis = new ArrayList<Produto>();

        for (Produto produto : produtos) {
            if (produto.getQuantidade() > 0) {
                disponiveis.add(produto);
            }
        }
        return disponiveis;
    }
}
