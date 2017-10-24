/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.u2t01.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jessica Lizbeth
 */
@Entity
@Table(name = "campaign")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campaign.findAll", query = "SELECT c FROM Campaign c")
    , @NamedQuery(name = "Campaign.findByCampaignid", query = "SELECT c FROM Campaign c WHERE c.campaignid = :campaignid")
    , @NamedQuery(name = "Campaign.findByStartdate", query = "SELECT c FROM Campaign c WHERE c.startdate = :startdate")
    , @NamedQuery(name = "Campaign.findByEnddate", query = "SELECT c FROM Campaign c WHERE c.enddate = :enddate")
    , @NamedQuery(name = "Campaign.findByMessage", query = "SELECT c FROM Campaign c WHERE c.message = :message")
    , @NamedQuery(name = "Campaign.findByEmail", query = "SELECT c FROM Campaign c WHERE c.email = :email")
    , @NamedQuery(name = "Campaign.findByCellphone", query = "SELECT c FROM Campaign c WHERE c.cellphone = :cellphone")
    , @NamedQuery(name = "Campaign.findByFrequency", query = "SELECT c FROM Campaign c WHERE c.frequency = :frequency")})
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "campaignid")
    private Integer campaignid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "email")
    private boolean email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cellphone")
    private boolean cellphone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "frequency")
    @Temporal(TemporalType.TIME)
    private Date frequency;

    public Campaign() {
    }

    public Campaign(Integer campaignid) {
        this.campaignid = campaignid;
    }

    public Campaign(Integer campaignid, Date startdate, Date enddate, String message, boolean email, boolean cellphone, Date frequency) {
        this.campaignid = campaignid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.message = message;
        this.email = email;
        this.cellphone = cellphone;
        this.frequency = frequency;
    }

    public Integer getCampaignid() {
        return campaignid;
    }

    public void setCampaignid(Integer campaignid) {
        this.campaignid = campaignid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean getCellphone() {
        return cellphone;
    }

    public void setCellphone(boolean cellphone) {
        this.cellphone = cellphone;
    }

    public Date getFrequency() {
        return frequency;
    }

    public void setFrequency(Date frequency) {
        this.frequency = frequency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (campaignid != null ? campaignid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campaign)) {
            return false;
        }
        Campaign other = (Campaign) object;
        if ((this.campaignid == null && other.campaignid != null) || (this.campaignid != null && !this.campaignid.equals(other.campaignid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.edu.ittepic.u2t01.entities.Campaign[ campaignid=" + campaignid + " ]";
    }
    
}
