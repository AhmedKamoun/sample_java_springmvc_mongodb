package tn.springmvc_mongodb.bl.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ParsingService {

	public Boolean isLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean parseBoolean(String s) {
		if (s == null)
			return false;
		// s == on
		else
			return s.equalsIgnoreCase("on");
	}

	public int parseInt(String s, int default_value) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			// return default value
			return default_value;
		}
	}

	public double parseDouble(String s, double default_value) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			// return default value
			return default_value;
		}
	}

	public Date parseDate(String s, Date default_value) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return df.parse(s);
		} catch (Exception e) {
			// return default value
			return default_value;
		}

	}

	public float roundDecimalPart(float number, int precision) {
		if (precision > 0) {
			double prec = Math.pow(10, precision);
			return (float) (Math.round((number * prec)) / prec);
		} else
			return number;

	}
}
