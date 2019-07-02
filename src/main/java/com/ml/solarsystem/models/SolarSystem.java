package com.ml.solarsystem.models;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ml.solarsystem.utils.MathUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "solarsystems")
public class SolarSystem {
	@Id
	private String id;
	
	@Min(1)
	private int daysOfYear;
	
	@PositiveOrZero
	private int lastProcessedDay;
	
	private List<Planet> planets = new ArrayList<>();
	
	private Map<Integer, Weather> weather = new HashMap<>();

	public SolarSystem(List<Planet> planets) {
		this.planets = planets;
	}

	public int getLastProcessedDay() {
		return lastProcessedDay;
	}

	public void setLastProcessedDay(int lastLastProcessedDay) {
		this.lastProcessedDay = lastLastProcessedDay;
	}

	public List<Point2D.Double> calculatePlanetsPositions(int day) {
		List<Point2D.Double> positions = new LinkedList<>();
		this.planets.forEach(planet -> positions.add(planet.calculePosition(day)));
		return positions;
	}


	private Weather getWeather(int day) {
		List<Point2D.Double> positions = calculatePlanetsPositions(day);

		final Weather w = new Weather().setDay(day);

		if (MathUtils.aligned(positions)) {

			List<Point2D.Double> positionsWithOrigin =  Arrays.asList(positions.get(0), positions.get(1), new Point2D.Double(0.0, 0.0));

			if (MathUtils.aligned(positionsWithOrigin)) {
				w.setCondition(Weather.Condition.DROUGHT);
			} else {
				w.setCondition(Weather.Condition.OPTIMUM);
			}
		} else {
			if (MathUtils.containsPoint(positions, 0, 0)) {
				w.setCondition(Weather.Condition.RAIN).setIntensity(MathUtils.getPerimeter(positions));
			} else {
				w.setCondition(Weather.Condition.NORMAL);
			}
		}
		return w;
	}

	public Weather getWeatherForDay(int day) {
		if (!this.weather.containsKey(day)) {
			Weather weatherDay = getWeather(day);
			this.weather.put(day, weatherDay);

			if (weatherDay.getCondition() == Weather.Condition.RAIN) {
				int	topLimit = day;
				int bottomLimit = day; 

				Weather actualWeather;

				do {
					topLimit++;
					actualWeather = this.weather.getOrDefault(topLimit, getWeather(topLimit));
					this.weather.put(topLimit, actualWeather);
					weatherDay = weatherDay.getIntensity() > actualWeather.getIntensity() ? weatherDay : actualWeather;
				} while (actualWeather.getCondition() == Weather.Condition.RAIN);

				do {
					bottomLimit--;
					actualWeather = this.weather.getOrDefault(bottomLimit, getWeather(bottomLimit));
					this.weather.put(bottomLimit, actualWeather);
					weatherDay = weatherDay.getIntensity() > actualWeather.getIntensity() ? weatherDay : actualWeather;
				} while (actualWeather.getCondition() == Weather.Condition.RAIN);

				weatherDay.setCondition(Weather.Condition.INTENSE_RAIN);
				this.weather.put(weatherDay.getDay(), weatherDay);
			} else {
				return weatherDay;
			}
		}
		return this.weather.get(day);
	}

}
