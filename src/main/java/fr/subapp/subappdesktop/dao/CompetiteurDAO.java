package fr.subapp.subappdesktop.dao;

import fr.subapp.subappdesktop.model.Competiteur;
import fr.subapp.subappdesktop.utils.Categorie;
import fr.subapp.subappdesktop.utils.Sexe;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service

public class CompetiteurDAO {


    public List<Competiteur> findAll() {
        List<Competiteur> competiteurs = new ArrayList<>();
        competiteurs.add(new Competiteur("DUPONT", "Jean", Sexe.MASCULIN, LocalDate.now(), Categorie.SENIOR, "Club 1", 123456));
        competiteurs.add(new Competiteur("DURAND", "Marie", Sexe.FEMININ, LocalDate.now(), Categorie.JUNIOR, "Club 2", 15151));
        competiteurs.add(new Competiteur("MARTIN", "Paul", Sexe.MASCULIN, LocalDate.now(), Categorie.MASTER, "Club 3", 851));
        competiteurs.add(new Competiteur("DUPOND", "Pierre", Sexe.MASCULIN, LocalDate.now(), Categorie.SENIOR, "Club 2", 244));
        competiteurs.add(new Competiteur("DUPONT", "Jeanne", Sexe.FEMININ, LocalDate.now(), Categorie.MINIME, "Club 5", 413));
        competiteurs.add(new Competiteur("DURAND", "Pierre", Sexe.MASCULIN, LocalDate.now(), Categorie.SENIOR, "Club 1", 123456));
        competiteurs.add(new Competiteur("MARTIN", "Marie", Sexe.FEMININ, LocalDate.now(), Categorie.MINIME, "Club 2", 15151));
        competiteurs.add(new Competiteur("DUPOND", "Paul", Sexe.MASCULIN, LocalDate.now(), Categorie.MASTER, "Club 3", 851));
        competiteurs.add(new Competiteur("DUPONT", "Pierre", Sexe.MASCULIN, LocalDate.now(), Categorie.SENIOR, "Club 2", 244));
        competiteurs.add(new Competiteur("DUPONT", "Marie", Sexe.FEMININ, LocalDate.now(), Categorie.MINIME, "Club 5", 413));
        competiteurs.add(new Competiteur("DURAND", "Jean", Sexe.MASCULIN, LocalDate.now(), Categorie.CADET, "Club 1", 123456));
        competiteurs.add(new Competiteur("MARTIN", "Marie", Sexe.FEMININ, LocalDate.now(), Categorie.CADET, "Club 2", 15151));
        competiteurs.add(new Competiteur("DUPOND", "Paul", Sexe.MASCULIN, LocalDate.now(), Categorie.JUNIOR, "Club 3", 851));
        competiteurs.add(new Competiteur("DUPOND", "Pierre", Sexe.MASCULIN, LocalDate.now(), Categorie.SENIOR, "Club 2", 244));
        competiteurs.add(new Competiteur("DUPONT", "Marie", Sexe.FEMININ, LocalDate.now(), Categorie.MINIME, "Club 5", 413));
        competiteurs.add(new Competiteur("DUPONT", "Marie", Sexe.FEMININ, LocalDate.now(), Categorie.MASTER, "Club 5", 413));
        competiteurs.add(new Competiteur("DURAND", "Jean", Sexe.MASCULIN, LocalDate.now(), Categorie.SENIOR, "Club 1", 123456));
        competiteurs.add(new Competiteur("MARTIN", "Marie", Sexe.FEMININ, LocalDate.now(), Categorie.MINIME, "Club 2", 15151));
        competiteurs.add(new Competiteur("DUPOND", "Paul", Sexe.MASCULIN, LocalDate.now(), Categorie.MASTER, "Club 3", 851));

        return competiteurs;
    }
}
