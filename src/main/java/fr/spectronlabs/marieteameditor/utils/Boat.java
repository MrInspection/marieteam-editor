package fr.spectronlabs.marieteameditor.utils;

public class Boat {

  private String id;
  private String name;
  private double length;
  private double width;
  private double speed;
  private String imageUrl;
  private String[] equipments;

  public Boat(String id, String name, double length, double width, double speed, String imageUrl, String[] equipments) {
    this.id = id;
    this.name = name;
    this.length = length;
    this.width = width;
    this.speed = speed;
    this.imageUrl = imageUrl;
    this.equipments = equipments;
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
    this.length = length;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String[] getEquipments() {
    return equipments;
  }

  public void setEquipments(String[] equipments) {
    this.equipments = equipments;
  }
}

 