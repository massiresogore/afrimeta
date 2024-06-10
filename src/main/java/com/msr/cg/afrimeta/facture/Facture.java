package com.msr.cg.afrimeta.facture;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
@Entity
@Table(name = "facture")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facture_id")
    Long factureId;

    @Column(name = "facture_date")
    @CreationTimestamp
    LocalDate  factureDate;

    @Column(name = "total_hors_taxe")
    double totalHorsTaxe;

    @Column(name = "total_tout_taxe_comprise")
    double totalToutTaxeComprise;

    @Column(name = "total_tva")
    double totalTva;

    public Facture() {
    }

    public Facture(LocalDate factureDate, double totalHorsTaxe, double totalToutTaxeComprise, double totalTva) {
        this.factureDate = factureDate;
        this.totalHorsTaxe = totalHorsTaxe;
        this.totalToutTaxeComprise = totalToutTaxeComprise;
        this.totalTva = totalTva;
    }

    public Facture(Long factureId, LocalDate factureDate, double totalHorsTaxe, double totalToutTaxeComprise, double totalTva) {
        this(factureDate, totalHorsTaxe, totalToutTaxeComprise, totalTva);
        this.factureId = factureId;
    }

    public Long getFactureId() {
        return factureId;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }

    public LocalDate getFactureDate() {
        return factureDate;
    }

    public void setFactureDate(LocalDate factureDate) {
        this.factureDate = factureDate;
    }

    public double getTotalHorsTaxe() {
        return totalHorsTaxe;
    }

    public void setTotalHorsTaxe(double totalHorsTaxe) {
        this.totalHorsTaxe = totalHorsTaxe;
    }

    public double getTotalToutTaxeComprise() {
        return totalToutTaxeComprise;
    }

    public void setTotalToutTaxeComprise(double totalToutTaxeComprise) {
        this.totalToutTaxeComprise = totalToutTaxeComprise;
    }

    public double getTotalTva() {
        return totalTva;
    }

    public void setTotalTva(double totalTva) {
        this.totalTva = totalTva;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "factureId=" + factureId +
                ", factureDate=" + factureDate +
                ", totalHorsTaxe=" + totalHorsTaxe +
                ", totalToutTaxeComprise=" + totalToutTaxeComprise +
                ", totalTva=" + totalTva +
                '}';
    }
}
