package Repository;

import Model.Spectacol;
import Model.User;

import java.sql.SQLException;

public interface ISpectacolRepository {
    Spectacol findOne(String id) throws SQLException;

    Iterable<Spectacol> findAll();

    Spectacol save( Spectacol entity) throws SQLException;

    Spectacol delete(String id) throws SQLException;

    Spectacol update( Spectacol entity);

    Iterable<Spectacol> findAll_disponibile();
}
