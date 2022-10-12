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
    public List<Car> getListCars() {
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        User user = new User();
        for (Car car : getListCars()) {
            if (car.getModel().equals(model) && car.getSeries() == series) {
                long x = car.getId();
                Query query = sessionFactory.getCurrentSession().createQuery("from User where id = :paramName");
                query.setParameter("paramName", x);
                user = (User) query.getSingleResult();
            }
        }
        return user;
    }

}
