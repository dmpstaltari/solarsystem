package com.ml.solarsystem.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class Weather {

	public enum Condition {
		RAIN("lluvia"),
		INTENSE_RAIN("lluvia-intensa"),
		OPTIMUM("optimo"),
		NORMAL("normal"),
		DROUGHT("sequia");

		private final String name;

		Condition(String name) {
			this.name = name;
		}

		@JsonValue
		@Override
		public String toString() {
			return name;
		}
	}

	@JsonProperty("clima")
	private Condition condition;

	@JsonProperty("dia")
	private int day;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private double intensity;

	public Weather() {
	}

	public Weather(int day, Condition condition,  double intensity) {
		this.day = day;
		this.condition = condition;
		this.intensity = intensity;
	}

	public Condition getCondition() {
		return condition;
	}

	public Weather setCondition(Condition condition) {
		this.condition = condition;
		return this;
	}

	public int getDay() {
		return day;
	}

	public Weather setDay(int day) {
		this.day = day;
		return this;
	}

	public double getIntensity() {
		return intensity;
	}

	public Weather setIntensity(double intensity) {
		this.intensity = intensity;
		return this;
	}

	@Override
	public String toString() {
		return "Weather{" +
				"day=" + day +
				", condition=" + condition +
				", intensity=" + intensity +
				"}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Weather weather = (Weather) o;
		return day == weather.day;
	}

	@Override
	public int hashCode() {
		return Objects.hash(day);
	}
}
