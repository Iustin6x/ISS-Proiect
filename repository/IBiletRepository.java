package Repository;

import Model.Bilet;
import Model.User;

import java.sql.SQLException;

public interface IBiletRepository {
    public Iterable<Bilet> findBySpectacol(String spectacol);
    public Bilet findByNR_Spectacol(int rand,int nr,String spectacol);

    Bilet findOne(String id) throws SQLException;

    Iterable<Bilet> findAll();

    Bilet save(Bilet entity) throws SQLException;

    Bilet delete(String id) throws SQLException;

    Bilet update(Bilet entity);
}
