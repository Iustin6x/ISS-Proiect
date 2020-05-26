package Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Spectacol")
public class Spectacol extends HasID<String> {
    private String id;
   // @Column(name = "titlu")
    private String titlu;
    //@Column(name = "descriere")
    private String descriere;
   // @Column(name = "dataSiora")
    private LocalDateTime dataSiora;
    //@Column(name = "pretBilet")
    private float pretBilet;

    public Spectacol() {
    }
    public Spectacol(String titlu, String descriere, LocalDateTime dataSiora, float pretBilet) {
        super.setId(titlu+dataSiora);
        this.titlu = titlu;
        this.descriere = descriere;
        this.dataSiora = dataSiora;
        this.pretBilet = pretBilet;
    }
    public Spectacol(String id,String titlu, String descriere, LocalDateTime dataSiora, float pretBilet) {
        super.setId(id);
        this.titlu = titlu;
        this.descriere = descriere;
        this.dataSiora = dataSiora;
        this.pretBilet = pretBilet;
    }
    @Id
    @Column(name = "id")
    public String getId(){
        return super.getId();
    }

    public void setId(String id) {
        super.setId(id);
    }

    @Column(name = "titlu")
    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    @Column(name = "descriere")
    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Column(name = "dataSiora")
    public LocalDateTime getDataSiora() {
        return dataSiora;
    }

    public void setDataSiora(LocalDateTime dataSiora) {
        this.dataSiora = dataSiora;
    }

    @Column(name = "pretBilet")
    public float getPretBilet() {
        return pretBilet;
    }

    public void setPretBilet(float pretBilet) {
        this.pretBilet = pretBilet;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "titlu='" + titlu + '\'' +
                ", descriere='" + descriere + '\'' +
                ", dataSiora='" + dataSiora + '\'' +
                ", pretBilet='" + pretBilet + '\'' +
                '}';
    }
}
