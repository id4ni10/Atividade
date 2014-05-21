/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jee.dao.GenericModel;
import models.dto.Cotacao;

/**
 *
 * @author itakenami
 */
@Stateless
public class CotacaoDao extends GenericModel<Cotacao> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Class<Cotacao> getEntityClass() {
        return Cotacao.class;
    }
    
}
