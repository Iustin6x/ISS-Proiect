package Repository;

import Model.User;

import java.sql.SQLException;

public interface IUserRepository{
    public String login(User user);

    User findOne(String id) throws SQLException;

    Iterable<User> findAll();

    User save(User entity) throws SQLException;

    User delete(String id) throws SQLException;

    User update(User entity);
}

