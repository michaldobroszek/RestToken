package com.michal;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service("exampleService")
public class ServiceImpl implements IService {
	Map<UUID, Calendar> userMap = new HashMap<UUID, Calendar>();

	@Override
	public User getLogin(String l, String h) {
		User user = new User();
		user.setLogin(l);
		user.setPassword(h);

		Calendar calendar = Calendar.getInstance();

		userMap.put(user.getToken(), calendar);

		return user;

	}

	public Boolean checkToken(UUID token) {

		Calendar calendarNow = Calendar.getInstance();

		int minutes = calendarNow.get(Calendar.MINUTE);
		int seconds = calendarNow.get(Calendar.SECOND);

		Calendar timeLogin = userMap.get(token);

		if ((timeLogin.get(Calendar.MINUTE) + 1) > minutes
				|| (timeLogin.get(Calendar.SECOND)) >= seconds)
			return true;
		else
			return false;
	}

	@Override
	public String writeSomething(UUID token) {
		if (checkToken(token) == true)
			return "logged";
		else
			return "ups token is invalid";
	}

}