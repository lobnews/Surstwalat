package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "Field")
@NamedQueries(
{
  @NamedQuery(name = "Field.getById", query = "SELECT f FROM Field f WHERE f.id = :id"),
  @NamedQuery(name = "Field.getByItemId", query = "SELECT f FROM Field f WHERE f.item.id = :itemId")
})
@Entity
public class Field implements Serializable
{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Playground playground;

    @JoinColumn
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Item item;
    
    @OneToOne(mappedBy = "field")
    private Token token;

    @Column
    private boolean isToxic = false;

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public Token getToken()
    {
        return token;
    }

    public void setToken(Token token)
    {
        this.token = token;
    }

    public boolean isToxic()
    {
        return isToxic;
    }

    public void setToxic(boolean isToxic)
    {
        this.isToxic = isToxic;
    }

    public int getId()
    {
        return id;
    }
    
    public Playground getPlayground() {
    	return this.playground;
    }
    
    public void setPlayground(Playground playground) {
    	this.playground = playground;
    }

}
