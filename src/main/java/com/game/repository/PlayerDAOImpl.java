package com.game.repository;

import com.game.entity.Player;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class PlayerDAOImpl implements PlayerDAO {

    @Autowired

    LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Override
    public List<Player> getAllPlayers() {
        List<Player> allPlayers = entityManagerFactory.getNativeEntityManagerFactory().createEntityManager().createQuery("from Player", Player.class).getResultList();
        return allPlayers;
    }

    @Override
    public Player getPlayer(long id) {
        //Player player = entityManagerFactory.getNativeEntityManagerFactory().createEntityManager().createQuery("from Player p where id = p.id", Player.class).getSingleResult();
        Player player = entityManagerFactory.getNativeEntityManagerFactory().createEntityManager().find(Player.class, id);
        return player;
    }
}
