package gg.wildblood.island_core.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SerializableDateTime {
	private int hour;
	private int minute;
	private int second;

	private int day;
	private int month;
	private int year;

	public SerializableDateTime(int hour, int minute, int second, int day, int month, int year) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public SerializableDateTime(ZonedDateTime zonedDateTime) {
		this.hour = zonedDateTime.getHour();
		this.minute = zonedDateTime.getMinute();
		this.second = zonedDateTime.getSecond();
		this.day = zonedDateTime.getDayOfMonth();
		this.month = zonedDateTime.getMonthValue();
		this.year = zonedDateTime.getYear();
	}

	public ZonedDateTime toZonedDateTime() {
		return ZonedDateTime.of(year, month, day, hour, minute, second, 0, ZoneId.systemDefault());
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
