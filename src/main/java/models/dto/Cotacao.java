/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.dto;

/**
 *
 * @author itakenami
 */
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "cotacoes")
@XmlRootElement(name = "cotacao")
public class Cotacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //private int numero;
    private String CPF;
    private String solicitante;
    //@Temporal(javax.persistence.TemporalType.DATE)
    private Date data_solicitacao;
    //private String data_solicitacao;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cotacoes_produtos", joinColumns = {
        @JoinColumn(name = "cotacao_id")}, inverseJoinColumns = {
        @JoinColumn(name = "produto_id")})
    private Set<Produto> produtos;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public Set<Produto> getCotacoes() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Date getData_solicitacao() {
        return data_solicitacao;
    }

    public void setData_solicitacao(Date data_solicitacao) {
        this.data_solicitacao = data_solicitacao;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }
}
