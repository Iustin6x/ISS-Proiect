package Service;

import MVC.events.ChangeEventType;
import MVC.events.Observable;
import MVC.events.Observer;
import MVC.events.SpectacolChangeEvent;
import Model.Spectacol;
import Repository.SpectacolRepository;
import Repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpectacolService implements Observable<SpectacolChangeEvent> {
    SpectacolRepository spectacolRepository;

    public SpectacolService() throws SQLException {
        this.spectacolRepository = new SpectacolRepository();
    }

    public Spectacol findOne(String id) throws SQLException{
        return spectacolRepository.findOne(id);
    }

    public Iterable<Spectacol> printAll()
    {
        return spectacolRepository.findAll();
    }
    public Iterable<Spectacol> printAll_disponibile()
    {
        return spectacolRepository.findAll_disponibile();
    }

    public Spectacol save( Spectacol entity) throws SQLException
    {
        Spectacol spec= spectacolRepository.save(entity);
        if(spec == null) {
            notifyObservers(new SpectacolChangeEvent(ChangeEventType.ADD,spec));
        }
        return spec;
    }

    public Spectacol delete(String id) throws SQLException
    {
        Spectacol spec= spectacolRepository.delete(id);
        if(spec != null) {
            notifyObservers(new SpectacolChangeEvent(ChangeEventType.ADD,spec));
        }
        return spec;
    }

    public Spectacol update( Spectacol entity)
    {
        Spectacol spec= spectacolRepository.update(entity);
        if(spec == null) {
            notifyObservers(new SpectacolChangeEvent(ChangeEventType.ADD,spec));
        }
        return spec;

    }

    private List<Observer<SpectacolChangeEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<SpectacolChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<SpectacolChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(SpectacolChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }


}
