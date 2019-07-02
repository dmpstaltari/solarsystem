package com.ml.solarsystem.models;

import java.awt.geom.Point2D;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
	
	@Id
	@NotBlank
	private String name;

	@NotNull
	@PositiveOrZero
	private double distance;

	@NotNull
	@PositiveOrZero
	private double speed;

	@NotNull
	private double originX;
	
	@NotNull
	private double originY;

	@NotNull
	private Rotation rotation;
	
	@Transient
	private double originAngle; 
	
	public Planet(String name, double distance, double speed, double originX, double originY, Rotation rotation) {
		this.name = name;
		this.distance = distance;
		this.originX = originX;
		this.originY = originY;
		this.speed = speed;
		this.rotation = rotation;
		this.originAngle =  this.calculateOriginAngle();
	}
	
	public double calculateOriginAngle() {
		this.originAngle =  Math.atan2(this.originY, this.originX);
		return this.originAngle;
	}

	public Point2D.Double calculePosition(int day) {
    	final double angle = this.getOriginAngle() + Math.toRadians(this.getSpeed() * day) * this.getRotation().getValue() ;
        final double r = this.getDistance();

        final double x1 =  r * Math.cos(angle);
        final double y1 =  r * Math.sin(angle);
        
        return new Point2D.Double(x1, y1);
	}
	
}
