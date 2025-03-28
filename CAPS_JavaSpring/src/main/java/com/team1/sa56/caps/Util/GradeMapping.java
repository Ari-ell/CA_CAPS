package com.team1.sa56.caps.Util;

import java.util.TreeMap;

public class GradeMapping {
    // calculate the grade for a given score
	public static Grade calcGrade(Integer score) {
		
		TreeMap<Integer, Grade> grades = new TreeMap<Integer, Grade>();
		
        grades.put(0, new Grade(0.0, "F"));
		grades.put(40, new Grade(1.0, "D"));
		grades.put(45, new Grade(1.5, "D+"));
		grades.put(50, new Grade(2.0, "C"));
		grades.put(55, new Grade(2.5, "C+"));
		grades.put(60, new Grade(3.0, "B-"));
		grades.put(65, new Grade(3.5, "B"));
		grades.put(70, new Grade(4.0, "B+"));
		grades.put(75, new Grade(4.5, "A-"));
		grades.put(80, new Grade(5.0, "A"));
		grades.put(85, new Grade(5.0, "A+"));

        return grades.floorEntry(score).getValue();
	}
}