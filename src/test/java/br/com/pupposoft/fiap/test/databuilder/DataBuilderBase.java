package br.com.pupposoft.fiap.test.databuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class DataBuilderBase {
	private static final Random random = new Random();
	private static final int AMOUNT_STRING_VALUES = 200;
	private static int index = 0;
	
	protected static String[] randomStringValues = null;
	private static void generateStringValues(final int size) {
		randomStringValues = new String[size];
		int length = 10;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    
	    for (int i = 0; i < randomStringValues.length; i++) {
	    	final String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
			randomStringValues[i] = generatedString;
		}
	}
	static {
		if(randomStringValues == null) {
			generateStringValues(AMOUNT_STRING_VALUES);
		}
	}
	
	public static String getRandomString() {
		final String next = randomStringValues[index++];
		
		if(index >= randomStringValues.length - 1) {
			index = 0;
		}
		
		return next;
	}
	
	public static int getRandomInteger() {
	    return random.ints(0, 5000)
	      .findFirst()
	      .getAsInt();
	}
	
	public static double getRandomDouble() {
	    return random.doubles(0, 5000)
	  	      .findFirst()
	  	      .getAsDouble();
	}
	
	public static long getRandomLong() {
	    return random.longs(0, 5000)
	  	      .findFirst()
	  	      .getAsLong();
	}
	
	public static LocalDate getRandomLocalDate(final LocalDate startInclusive, final LocalDate endExclusive) {
	    long startEpochDay = startInclusive.toEpochDay();
	    long endEpochDay = endExclusive.toEpochDay();
	    long randomDay = ThreadLocalRandom
	      .current()
	      .nextLong(startEpochDay, endEpochDay);

	    return LocalDate.ofEpochDay(randomDay);
	}
	
	public static LocalDate getRandomLocalDate() {
	    int hundredYears = 100 * 365;
	    return LocalDate.ofEpochDay(ThreadLocalRandom
	      .current().nextInt(-hundredYears, hundredYears));
	}

	public static LocalTime getRandomLocalTime(final LocalTime startTime, final LocalTime endTime) {
	    int startSeconds = startTime.toSecondOfDay();
	    int endSeconds = endTime.toSecondOfDay();
	    int randomTime = ThreadLocalRandom
	      .current()
	      .nextInt(startSeconds, endSeconds);

	    return LocalTime.ofSecondOfDay(randomTime);
	}
	
	public static LocalTime getRandomLocalTime() {
	    return getRandomLocalTime(LocalTime.MIN, LocalTime.MAX);
	}
	
	public static LocalDateTime getRandomLocalDateTime() {
		final LocalDate date = getRandomLocalDate();
		final LocalTime time = getRandomLocalTime();
		
		return LocalDateTime.of(date, time);
	}
	
	public LocalDateTime getRandomLocalDateTime(final LocalDateTime startInclusive, final LocalDateTime endExclusive) {
		final LocalDate date = getRandomLocalDate(startInclusive.toLocalDate(), endExclusive.toLocalDate());
		final LocalTime time = getRandomLocalTime(startInclusive.toLocalTime(), endExclusive.toLocalTime());
		
		return LocalDateTime.of(date, time);
	}
	
	public static boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}
}
