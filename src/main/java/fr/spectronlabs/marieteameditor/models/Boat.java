package fr.spectronlabs.marieteameditor.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Boat {

  private String id;
  private String name;
  private double length;
  private double width;
  private double speed;
  private Optional<String> imageUrl;
  private List<String> equipments;

  public Boat(String id, String name, double length, double width, double speed, String imageUrl, List<String> equipments) {
    this.id = id;
    this.name = name;
    this.length = length;
    this.width = width;
    this.speed = speed;
    this.imageUrl = Optional.ofNullable(String.valueOf(imageUrl));
    this.equipments = (equipments != null) ? new ArrayList<>(equipments) : new ArrayList<>();
  }

  public Boat(String id, String name, double length, double width, double speed) {
    this(id, name, length, width, speed, null, null);
  }

  @Override
  public String toString() {
    return "Boat{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", length=" + length +
            ", width=" + width +
            ", speed=" + speed +
            ", imageUrl=" + imageUrl.orElse("No Image") +
            ", equipments=" + equipments +
            '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    if (length <= 0) {
      throw new IllegalArgumentException("Length must be positive.");
    }
    this.length = length;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    if (width <= 0) {
      throw new IllegalArgumentException("Width must be positive.");
    }
    this.width = width;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    if (speed < 0) {
      throw new IllegalArgumentException("Speed cannot be negative.");
    }
    this.speed = speed;
  }

  public Optional<String> getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = Optional.ofNullable(imageUrl);
  }

  public List<String> getEquipments() {
    return new ArrayList<>(equipments);
  }

  public void setEquipments(List<String> equipments) {
    this.equipments = (equipments != null) ? new ArrayList<>(equipments) : new ArrayList<>();
  }

  public void addEquipment(String equipment) {
    if (equipment != null && !equipment.isBlank()) {
      equipments.add(equipment);
    }
  }

  public boolean removeEquipment(String equipment) {
    return equipments.remove(equipment);
  }

  public String getFormattedEquipments() {
    return String.join(", ", equipments);
  }
}