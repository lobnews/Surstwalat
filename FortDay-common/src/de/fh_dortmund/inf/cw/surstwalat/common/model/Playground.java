package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="Playground")
@Entity
@NamedQueries({
    @NamedQuery(name="Playground.getById", query="SELECT p FROM Playground p WHERE p.id = :id"),
    @NamedQuery(name="Playground.getByGameId", query="SELECT p FROM Playground p WHERE p.gameId = :gameId")

})
public class Playground implements Serializable
{
    
    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;
    @JoinColumn
    private List<Field> fields;
    
    @JoinColumn
    private int gameId;
    
    public int getGameId()
    {
        return gameId;
    }
    public void setGameId(int gameId)
    {
        this.gameId = gameId;
    }
    
    public List<Field> getFields()
    {
        return fields;
    }
    public void setFields(List<Field> fields)
    {
        this.fields = fields;
    }
    public int getId()
    {
        return id;
    }
    
    

}
