import java.math.BigDecimal;
import java.time.LocalDate;

public class Komponenta {
    private String popis;
    private Integer dodaciDoba;
    private Boolean jeNova;
    private Integer stav;
    private BigDecimal cena;
    private LocalDate datum;

    public Komponenta(String popis, Integer dodaciDoba, Boolean jeNova, Integer stav, BigDecimal cena, LocalDate datum) {
        this.popis = popis;
        this.dodaciDoba = dodaciDoba;
        this.jeNova = jeNova;
        this.stav = stav;
        this.cena = cena;
        this.datum = datum;
    }

    public String getPopis() {
        return popis;
    }

    public Integer getDodaciDoba() {
        return dodaciDoba;
    }

    public Boolean getJeNova() {
        return jeNova;
    }

    public Integer getStav() {
        return stav;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void setDodaciDoba(Integer dodaciDoba) {
        this.dodaciDoba = dodaciDoba;
    }

    public void setJeNova(Boolean jeNova) {
        this.jeNova = jeNova;
    }

    public void setStav(Integer stav) {
        this.stav = stav;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}
