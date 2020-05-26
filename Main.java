

import MVC.Start;
import Model.Bilet;
import Model.Spectacol;
import Model.User;
import Repository.UserRepository;
import Service.BiletService;
import Service.SpectacolService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {


        Start.main(args);
        //BiletService serv=new BiletService();
        //serv.save(new Bilet(101,10,"user","test"));
        //serv.findAll().forEach(System.out::println);
        //serv.findBySpectacol("user").forEach(System.out::println);
        //SpectacolService spec=new SpectacolService();
        //spec.save(new Spectacol("ada","safdsa","safa", LocalDateTime.now(),10));

        //UserRepository user=new UserRepository();
        //System.out.println(user.login(new User("user","pass")));
        //save(new User("user","pass","Spectator","ion","ion","@gmail.com"));
        //user.findAll().forEach(System.out::println);
        //System.out.println(user.findOne("user"));

       //SpectacolService spec=new SpectacolService();
       //spec.save(new Spectacol("titlu1","desc", LocalDateTime.now(),10));
       //spec.printAll_disponibile().forEach(System.out::println);
        //spec.findOne("test");
        //spec.delete("titlu12020-05-24T02:58:48.894321300");
        //Spectacol specc=spec.findOne("test");
        //specc.setPretBilet(2134214);
        //spec.update(specc);
       //spec.printAll().forEach(System.out::println);
    }
}
