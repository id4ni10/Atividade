/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Danilo
 */
@Entity
@Table(name = "pedidos_produtos")
public class PedidosProduto {

    private int quantidade;
    private double valor;

    public void setQuantidade(int qtd_pro) {
        this.quantidade = qtd_pro;
    }

    public void setTotal(double d) {
        this.valor = d;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValor() {
        return valor;
    }

    @Embeddable
    public static class PK implements Serializable {

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "pedido_id")
        private Pedido pedido;
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "produto_id")
        private Produto produto;

        public Pedido getPedido() {
            return pedido;
        }

        public void setPedido(Pedido pedido) {
            this.pedido = pedido;
        }

        public Produto getProduto() {
            return produto;
        }

        public void setProduto(Produto produto) {
            this.produto = produto;
        }

        @Override
        public String toString() {
            return "PK{" + ", produto=" + produto + '}';
        }
    }
    @Id
    private PK pk = new PK();

    public PK getPk() {
        return pk;
    }

    public void setPk(PK pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "PedidosProduto{" + "quantidade=" + quantidade + ", valor=" + valor + ", pk=" + pk + '}';
    }
}
