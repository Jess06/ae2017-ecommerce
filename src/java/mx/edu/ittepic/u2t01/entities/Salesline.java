/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.u2t01.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jessica Lizbeth
 */
@Entity
@Table(name = "salesline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salesline.findAll", query = "SELECT s FROM Salesline s")
    , @NamedQuery(name = "Salesline.findBySaleslineid", query = "SELECT s FROM Salesline s WHERE s.saleslineid = :saleslineid")
    , @NamedQuery(name = "Salesline.findByQuantity", query = "SELECT s FROM Salesline s WHERE s.quantity = :quantity")
    , @NamedQuery(name = "Salesline.findBySaleprice", query = "SELECT s FROM Salesline s WHERE s.saleprice = :saleprice")
    , @NamedQuery(name = "Salesline.findByPurchprice", query = "SELECT s FROM Salesline s WHERE s.purchprice = :purchprice")})
public class Salesline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "saleslineid")
    private Integer saleslineid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "saleprice")
    private double saleprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "purchprice")
    private double purchprice;
    @JoinColumn(name = "productid", referencedColumnName = "productid")
    @ManyToOne(fetch=FetchType.LAZY)
    private Product productid;
    @JoinColumn(name = "saleid", referencedColumnName = "saleid")
    @ManyToOne(fetch=FetchType.LAZY)
    private Sale saleid;

    public Salesline() {
    }

    public Salesline(Integer saleslineid) {
        this.saleslineid = saleslineid;
    }

    public Salesline(Integer saleslineid, int quantity, double saleprice, double purchprice) {
        this.saleslineid = saleslineid;
        this.quantity = quantity;
        this.saleprice = saleprice;
        this.purchprice = purchprice;
    }

    public Integer getSaleslineid() {
        return saleslineid;
    }

    public void setSaleslineid(Integer saleslineid) {
        this.saleslineid = saleslineid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public double getPurchprice() {
        return purchprice;
    }

    public void setPurchprice(double purchprice) {
        this.purchprice = purchprice;
    }

    public Product getProductid() {
        return productid;
    }

    public void setProductid(Product productid) {
        this.productid = productid;
    }

    public Sale getSaleid() {
        return saleid;
    }

    public void setSaleid(Sale saleid) {
        this.saleid = saleid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saleslineid != null ? saleslineid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salesline)) {
            return false;
        }
        Salesline other = (Salesline) object;
        if ((this.saleslineid == null && other.saleslineid != null) || (this.saleslineid != null && !this.saleslineid.equals(other.saleslineid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.edu.ittepic.u2t01.entities.Salesline[ saleslineid=" + saleslineid + " ]";
    }
    
}
