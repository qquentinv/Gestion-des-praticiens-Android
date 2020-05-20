package com.example.gestionpraticiensppe;

public class Praticien {
    String matriculePra;
    String nomPra;
    String prenomPra;
    String adressePra;
    String villePra;
    String cpPra;
    public Praticien(String matricule, String nom, String prenom, String adresse, String
            ville, String cp)
    {
        matriculePra= matricule;
        nomPra=nom;
        prenomPra=prenom;
        adressePra= adresse;
        villePra=ville;
        cpPra= cp;
    }
    public String getMatriculePra(){
        return matriculePra;
    }
    public String getNomPra() {
        return nomPra;
    }
    public String getPrenomPra() {
        return prenomPra;
    }
    public String getAdressePra() {
        return adressePra;
    }
    public String getVillePra() {
        return villePra;
    }
    public String getCpPra() {
        return cpPra;
    }
    @Override
    public String toString() {
        return matriculePra+ " " + nomPra+ " " + prenomPra;
    }

}
