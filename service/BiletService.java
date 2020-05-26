package Service;

import Model.Bilet;
import Repository.BiletRepository;

import java.sql.SQLException;

public class BiletService {
    BiletRepository repo;

    public BiletService() throws SQLException {
        this.repo = new BiletRepository();
    }


    public Bilet findByNR_Spectacol(int rand,int nr,String spectacol)
    {
        return repo.findByNR_Spectacol(rand,nr,spectacol);
    }
    public Bilet findOne(String id) throws SQLException
    {
        return repo.findOne(id);
    }

    public Iterable<Bilet> findAll()
    {
        return repo.findAll();
    }

    public Bilet save(Bilet entity) throws SQLException
    {
        return repo.save(entity);
    }

    public Bilet delete(String id) throws SQLException
    {
        return repo.delete(id);
    }

    public Bilet update(Bilet entity)
    {
        return repo.update(entity);
    }
}
