package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.TokenRepositoryLocal;

@Stateless
public class TokenRepository extends LocationManagementRepository<Token, Integer> implements TokenRepositoryLocal
{

    public TokenRepository()
    {
        super(Token.class);
    }

    @Override
    public Token findByPlayerIdAndTokenNr(int playerId, int tokenNr)
    {
        TypedQuery<Token> query = entityManager.createNamedQuery("Token.getByPlayerIdAndTokenNumber", Token.class);
        query.setParameter("playerId", playerId);
        query.setParameter("nr", tokenNr);
        return query.getSingleResult();
    }

}
