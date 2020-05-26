package Model;

import javax.persistence.*;


@Entity
@Table(name = "Bilet")
public class Bilet extends HasID<String> {
   // @Column(name = "rand")
    private int rand;
   // @Column(name = "numar")
    private int numar;
   // @Column(name = "id_spectacol")
    private String spectacol;
   // @Column(name = "id_spectator")
    private String spectator;

    public Bilet() {
    }
    public Bilet(int rand, int numar, String spectacol, String spectator) {
        super.setId(spectacol+rand+numar);
        this.rand = rand;
        this.numar = numar;
        this.spectacol = spectacol;
        this.spectator= spectator;
    }
    public Bilet(String id, int rand, int numar, String spectacol, String spectator) {
        super.setId(id);
        this.rand = rand;
        this.numar = numar;
        this.spectacol = spectacol;
        this.spectator= spectator;
    }

    public int getRand() {
        return rand;
    }

    @Id
    @Column(name = "id")
    public String getId(){
        return super.getId();
    }
    public void setId(String id) {
        super.setId(id);
    }
    public void setRand(int rand) {
        this.rand = rand;
    }
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "numar")
    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    @Column(name = "id_spectacol")
    public String getSpectacol() {
        return spectacol;
    }
    @Column(name = "id_spectator")
    public String getSpectator() {
        return spectator;
    }

    public void setSpectator(String spectator) {
        this.spectator = spectator;
    }

    public void setSpectacol(String spectacol) {
        this.spectacol = spectacol;
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "rand=" + rand +
                ", numar=" + numar +
                ", spectacol='" + spectacol + '\'' +
                ", spectator='" + spectator + '\'' +
                '}';
    }
}
