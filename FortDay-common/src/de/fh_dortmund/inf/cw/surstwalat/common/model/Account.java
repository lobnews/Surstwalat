package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Stephan Klimek
 *
 * User account
 */
@Table(name = "Account")
@Entity
@NamedQueries({
    @NamedQuery(name = "Account.getById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.getByName", query = "SELECT a FROM Account a WHERE a.name = :name"),
    @NamedQuery(name = "Account.getInLobby", query = "SELECT a FROM Account a WHERE a.inLobby = true")
})
public class Account implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "inLobby")
    private boolean inLobby;
    
    @ManyToMany(mappedBy = "humanUsersInGame")
    private List<Game> games;

    /**
     * Default constructor
     */
    public Account() {
        inLobby = false;
        if (games == null) {
            games = new ArrayList<>();
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Is user in lobby
     */
    public boolean isInLobby() {
        return inLobby;
    }

    /**
     * @param inLobby
     */
    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }

    /**
     * @return List of games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * @param games List of Games
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.email);
        hash = 19 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }
}
