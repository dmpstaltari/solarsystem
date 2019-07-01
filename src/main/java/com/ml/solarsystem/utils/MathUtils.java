package com.ml.solarsystem.utils;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.util.CollectionUtils;

public final class MathUtils {

	private static final double ZERO = 0.0;
	private static final double THRESHOLD = 0.00001;

	private MathUtils(){}


	public static boolean aligned(List<Point2D.Double> points) {
		if(!CollectionUtils.isEmpty(points)) {

			int n = points.size(); 

			if(n <= 2)
				return Boolean.TRUE;

			double distanceAllPoints =  IntStream.range(0, n-2).
					mapToObj(i -> (points.get(i).distance(points.get(i+1)) + points.get(i+1).distance(points.get(i+2))) + points.get(i).distance(points.get(i+2)))
					.reduce(0d, Double::sum);

			return distanceAllPoints%2 < THRESHOLD ;
		}
		return Boolean.FALSE;
	}

	public static double getPerimeter(List<Point2D.Double> points) {

		if(!CollectionUtils.isEmpty(points)) {
			return IntStream.range(0, points.size()-1).
					mapToObj(i -> points.get(i).distance(points.get(i+1)))
					.reduce(points.get(0).distance(points.get(points.size() - 1)), Double::sum);
		}
		return ZERO;
	}

	public static boolean containsPoint(List<Point2D.Double> points, double x, double y) {
		final double x1 = points.get(0).getX();
		final double x2 = points.get(1).getX();
		final double x3 = points.get(2).getX();

		final double y1 = points.get(0).getY();
		final double y2 = points.get(1).getY();
		final double y3 = points.get(2).getY();

		final double d = ((y2-y3) * (x1-x3) + (x3-x2) * (y1-y3));
		final double a = ((y2-y3) * (x-x3) + (x3-x2) * (y-y3)) / d;
		final double b = ((y3-y1) * (x-x3) + (x1-x3) * (y-y3)) / d;
		final double c = 1 - a - b;

		return 0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1;
	}
}
