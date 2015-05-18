package com.michal;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.spi.CalendarDataProvider;

import org.springframework.context.expression.MapAccessor;
import org.springframework.stereotype.Service;

@Service("exampleService")
public class ServiceImpl implements IService {
	Map<UUID, Calendar> mapaUzytkownikow = new HashMap<UUID, Calendar>();

	@Override
	public Uzytkownik getLogin(String l, String h) {
		Uzytkownik uzyt = new Uzytkownik();
		uzyt.setLogin(l);
		uzyt.setHaslo(h);

		Calendar cal = Calendar.getInstance();

		mapaUzytkownikow.put(uzyt.getZeton(), cal);

		return uzyt;

	}

	public Boolean sprawdzZeton(UUID zeton) {

		Calendar calTeraz = Calendar.getInstance();

		int minuty = calTeraz.get(Calendar.MINUTE);
		int sekundy = calTeraz.get(Calendar.SECOND);

		Calendar czasLogowania = mapaUzytkownikow.get(zeton);

		if ((czasLogowania.get(Calendar.MINUTE) + 1) > minuty
				|| (czasLogowania.get(Calendar.SECOND)) >= sekundy)
			return true;
		else
			return false;
	}

	@Override
	public String wypiszCos(UUID zeton) {
		if (sprawdzZeton(zeton) == true)
			return "zalogowano";
		else
			return "uplynal czas waznosci tokena zaloguj sie ponownie";
	}

}