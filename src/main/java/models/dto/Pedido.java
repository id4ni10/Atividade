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

@Entity(name = "pedidos")
@XmlRootElement(name = "pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //private int numero;
    private String cpf;
    private String solicitante;
    private String endereco;
    //@Temporal(javax.persistence.TemporalType.DATE)
    private Date data_pedido;
    private double total;

    @OneToMany(mappedBy="pk.pedido",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinTable(name = "pedidos_produtos", joinColumns = {
//        @JoinColumn(name = "pedido_id")}, inverseJoinColumns = {
//        @JoinColumn(name = "produto_id")})
    //private Set<Produto> produtos;
    
    private Set<PedidosProduto> itens;

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
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public Set<PedidosProduto> getPedidosProduto() {
        return itens;
    }

    public void setPedidosProduto(Set<PedidosProduto> itens) {
        this.itens = itens;
    }

    public Date getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(Date data_pedido) {
        this.data_pedido = data_pedido;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }
    
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", CPF=" + cpf + ", solicitante=" + solicitante + ", endereco=" + endereco + ", data_pedido=" + data_pedido + ", total=" + total + ", itens=" + itens + '}';
    }
}
