package ws;

import model.Users;
import util.HibernateUtil;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@WebService(endpointInterface = "ws.UserService")
public class UserServiceImpl implements UserService {

	// Read Method.
	@Override
	public Users read(String username) {
		Transaction transaction = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Users where username=:username_param");
			query.setParameter("username_param", username);
			List<Users> userList = query.list();
			if (userList.isEmpty()) {
				return null;
			} else {
                return userList.get(0);
            }
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return null;
	}

	// Create New User.
	@Override
	public void createUser(String username, String email, String password, Date birthday, Short sex) {
		Transaction transaction = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			// Check If Username Already In Database.
			if (read(username) != null) {
				System.out.println("Username was taken.");
			} else {
				Users user = new Users();
				user.setUsername(username);
				user.setEmail(email);
				user.setBirthday(birthday);
				user.setPassword(password);
				user.setSex(sex);
				session.save(user);
				session.getTransaction().commit();
				System.out.println("User created!");
			}
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	// Delete User
	@Override
	public void deleteUser(String username) {
		Transaction transaction = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("delete Users where username = :username_param");
			query.setParameter("username_param", username);
			int result = query.executeUpdate();
			session.getTransaction().commit();
			System.out.println((result == 1 ? "User Deleted Succesfully." : "Cant Find User."));
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	// Update User
	@Override
	public void updateUser(String username, String email) {
		Transaction transaction = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("update Users set email = :email_param where username = :username_param ");
			query.setParameter("email_param", email);
			query.setParameter("username_param", username);
			int result = query.executeUpdate();
			session.getTransaction().commit();
			System.out.println((result == 0 ? "Error" : "User updated"));
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

}
