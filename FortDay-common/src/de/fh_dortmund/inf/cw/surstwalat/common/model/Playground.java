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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "Playground")
@Entity
@NamedQueries(
{
  @NamedQuery(name = "Playground.getById", query = "SELECT p FROM Playground p WHERE p.id = :id"),
  @NamedQuery(name = "Playground.getByGameId", query = "SELECT p FROM Playground p WHERE p.game.id = :gameId")

})
public class Playground implements Serializable
{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @OneToMany
    private List<PlayField> fields;

    @JoinColumn
    private Game game;

    public Game getGame()
    {
        return game;
    }

    public void setGameId(Game game)
    {
        this.game = game;
    }

    public List<PlayField> getFields()
    {
        return fields;
    }

    public void setFields(List<PlayField> fields)
    {
        this.fields = fields;
    }

    public int getId()
    {
        return id;
    }

    public PlayField getField(int i)
    {
        return fields.get(i % fields.size());
    }

}
