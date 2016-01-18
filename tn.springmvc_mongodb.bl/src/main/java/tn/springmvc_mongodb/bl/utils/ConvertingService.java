package tn.springmvc_mongodb.bl.utils;

import org.springframework.stereotype.Service;

@Service
public class ConvertingService {

	public String cleanDoubleSpace(String input) {
		return input.replaceAll(" {2,}", " ");
	}

}
