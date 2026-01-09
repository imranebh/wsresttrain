package org.sid.wsrest;


import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "train")
public class Train {
    private Long id;
    private String nom;
    private String villeDepart;
    private String villeArrivee;
    private String heureDepart;

    public Train() {
    }

    public Train(Long id, String nom, String villeDepart, String villeArrivee, String heureDepart) {
        this.id = id;
        this.nom = nom;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
        this.heureDepart = heureDepart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }
}
