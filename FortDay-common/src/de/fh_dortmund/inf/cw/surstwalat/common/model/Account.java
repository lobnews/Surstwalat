package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Stephan Klimek
 *
 * User account
 */
@Table(name="Account")
@Entity
@NamedQueries({
	@NamedQuery(name="Account.getById", query="SELECT a FROM Account a WHERE a.id = :id"),
	@NamedQuery(name="Account.getInLobby", query="SELECT a FROM Account a WHERE a.inLobby = true"),
})
public class Account implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="username")
    private String username;
	@Column(name="email")
    private String email;
	@Column(name="password")
    private String password;
	@Column(name="inLobby")
    private boolean inLobby;

    /**
     * Default Constructor
     */
    public Account() {
    	inLobby = false;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + (inLobby ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (inLobby != other.inLobby)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isInLobby() {
		return inLobby;
	}

	public void setInLobby(boolean inLobby) {
		this.inLobby = inLobby;
	}
	
}
