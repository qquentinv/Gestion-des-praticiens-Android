package com.example.gestionpraticiensppe;

import java.util.Comparator;

public class departement {
    Integer numDep;

    public departement(Integer numDepartement){
        numDep=numDepartement;
    }

    public Integer getNumDep(){
        return numDep;
    }
    public static Comparator<departement> ComparatorNum = new Comparator<departement>() {
        @Override
        public int compare(departement o1, departement o2) {
            return (int) (o1.getNumDep() - o2.getNumDep());
        }
    };

    @Override
    public String toString(){
        if (numDep < 10)
            return "dep : 0" + numDep;
        else
            return "dep : " + numDep;
    }
}
