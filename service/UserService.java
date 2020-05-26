package Service;

import Model.User;
import Repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    private UserRepository repo;

    public UserService() throws SQLException {
        this.repo = new UserRepository();
    }

    public String login(User user)
    {
        return repo.login(user);
    }

    User findOne(String id) throws SQLException
    {
        return repo.findOne(id);
    }

    Iterable<User> findAll()
    {
        return repo.findAll();
    }

    public User save(User entity) throws SQLException
    {
        return repo.save(entity);
    }

    public User delete(String id) throws SQLException
    {
        return repo.delete(id);
    }

    public User update(User entity)
    {
        return repo.update(entity);
    }

    public User check(String username,String password)
    {
        User user= repo.findOne(username);

        if(user.getPassword().equals(password)) {
            return user;
        }
        else {
            return null;
        }
    }

}
