package com.ml.solarsystem.models;

import java.awt.geom.Point2D;

import org.springframework.data.annotation.Id;

import com.ml.solarsystem.dtos.PlanetDTO;

public class Planet {

	public enum Rotation {
		CLOCKWISE(-1),
		COUNTERCLOCKWISE(1);
		
		private int value;

	    Rotation(int value) {
	        this.value = value;
	    }
	    
	    public int getValue() {
	    	return value;
	    }
	}

	public Planet() {
		
	}
	
	public Planet(String name, double distance, double speed, double originX, double originY, Rotation rotation) {
		this.name = name;
		this.distance = distance;
		this.originX = originX;
		this.originY = originY;
		this.speed = speed;
		this.rotation = rotation;
		this.originAngle =  Math.atan2(this.originY, this.originX);
	}
	
	public Planet(PlanetDTO p){
		this.name = p.getName();
		this.distance = p.getDistance();
		this.originX = p.getOriginX();
		this.originY = p.getOriginY();
		this.speed = p.getSpeed();
		this.rotation = p.getRotation();
		this.originAngle =  Math.atan2(this.originY, this.originX);
	}
	
	@Id
	private String name;

	private double distance;

	private double speed;

	private double originX;
	
	private double originY;

	private Rotation rotation;
	
	private double originAngle; 

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getOriginX() {
		return originX;
	}

	public void setOriginX(double originX) {
		this.originX = originX;
	}

	public double getOriginY() {
		return originY;
	}

	public void setOriginY(double originY) {
		this.originY = originY;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

	public double getOriginAngle() {
		return originAngle;
	}
	
	public void calculateOriginAngle() {
		this.originAngle =  Math.atan2(this.originY, this.originX);
	}

	public Point2D.Double calculePosition(int day) {
    	final double angle = this.getOriginAngle() + Math.toRadians(this.getSpeed() * day) * this.getRotation().getValue() ;
        final double r = this.getDistance();

        final double x1 =  r * Math.cos(angle);
        final double y1 =  r * Math.sin(angle);
        
        return new Point2D.Double(x1, y1);
	}
	
	
	@Override
	public String toString() {
		return "Planet{" +
				"name=" + name + 
				", distance=" + distance +
				", speed=" + speed +
				", originX=" + originX +
				", originY=" + originY +
				", rotation=" + rotation +
				", originAngle=" + originAngle +
				'}';
	}
}
