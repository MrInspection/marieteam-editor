package fr.spectronlabs.marieteameditor.services;

import java.util.List;

import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.models.Boat;

/**
 * Service gérant la logique métier liée aux bateaux.
 * Fait le lien entre le contrôleur et la couche d'accès aux données.
 */
public class BoatService {
  private final BoatQuery boatQuery;

  public BoatService(BoatQuery boatQuery) {
    this.boatQuery = boatQuery;
  }

  /**
   * Récupère tous les bateaux via la couche d'accès aux données.
   *
   * @return Liste des bateaux
   */
  public List<Boat> fetchAllBoats() {
    return boatQuery.getAllBoats();
  }
}
