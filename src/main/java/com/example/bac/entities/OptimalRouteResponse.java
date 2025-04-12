package com.example.bac.entities;

import java.util.List;

public class OptimalRouteResponse {
    private Long camionId;
    private String camionNom;
    private List<Long> ordreCoursesIds;
    private double distanceTotale;

    // Constructeur, getters et setters
    public OptimalRouteResponse() {}

    public OptimalRouteResponse(Long camionId, String camionNom, List<Long> ordreCoursesIds, double distanceTotale) {
        this.camionId = camionId;
        this.camionNom = camionNom;
        this.ordreCoursesIds = ordreCoursesIds;
        this.distanceTotale = distanceTotale;
    }

    public Long getCamionId() {
        return camionId;
    }

    public void setCamionId(Long camionId) {
        this.camionId = camionId;
    }

    public String getCamionNom() {
        return camionNom;
    }

    public void setCamionNom(String camionNom) {
        this.camionNom = camionNom;
    }

    public List<Long> getOrdreCoursesIds() {
        return ordreCoursesIds;
    }

    public void setOrdreCoursesIds(List<Long> ordreCoursesIds) {
        this.ordreCoursesIds = ordreCoursesIds;
    }

    public double getDistanceTotale() {
        return distanceTotale;
    }

    public void setDistanceTotale(double distanceTotale) {
        this.distanceTotale = distanceTotale;
    }
}
