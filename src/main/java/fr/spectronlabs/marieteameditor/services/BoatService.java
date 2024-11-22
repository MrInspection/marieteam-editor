package fr.spectronlabs.marieteameditor.services;

import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.models.Boat;

import java.util.List;

public class BoatService {
  private final BoatQuery boatQuery;

  public BoatService(BoatQuery boatQuery) {
    this.boatQuery = boatQuery;
  }

  public List<Boat> fetchAllBoats() {
    return boatQuery.getAllBoats();
  }
}
