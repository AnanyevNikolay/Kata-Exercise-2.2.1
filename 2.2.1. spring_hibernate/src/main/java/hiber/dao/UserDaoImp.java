package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private SessionFactory sessionFactory;

   @Autowired
   public void setUserDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

   @Override
   public void add(User user) {
      if (user.getCar() != null) {
         sessionFactory.getCurrentSession().save(user.getCar());
      }
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> getListUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCar(String model, int series) {
      Query query = sessionFactory.getCurrentSession().createQuery("select user from Car car where" +
              " car.model =: model  and car.series =: series");
      query.setParameter("model", model);
      query.setParameter("series", series);
      User user = (User) query.getSingleResult();
      return user;
   }

}
