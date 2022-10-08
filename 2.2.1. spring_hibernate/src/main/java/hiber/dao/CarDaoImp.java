package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CarDaoImp implements CarDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> listCars() {
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Car WHERE model=:model AND series=:series");
        query.setParameter("model", model);
        query.setParameter("series", series);
        Car car = (Car) query.getSingleResult();
        Query query2 = sessionFactory.getCurrentSession().createQuery("From User WHERE car=:car");
        query2.setParameter("car", car);
        User user = (User) query2.getSingleResult();
        return user;
    }

}
