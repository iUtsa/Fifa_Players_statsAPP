package src.java.main;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PlayerManager {

    // Add a new Player
    public void addPlayer(Player player) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Retrieve a Player by ID
    public Player getPlayer(Long id) {
        try (Session session = getSession()) {
            return session.get(Player.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // List all Players
    public List<Player> listPlayers() {
        try (Session session = getSession()) {
            return session.createQuery("from Player", Player.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing Player
    public void updatePlayer(Player player) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a Player
    public void deletePlayer(Long id) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            Player player = session.get(Player.class, id);
            if (player != null) {
                session.delete(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Utility method to obtain Hibernate session
    private Session getSession() {
        return new Configuration().configure().buildSessionFactory().openSession();
    }
}
