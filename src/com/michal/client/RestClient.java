package com.michal.client;

import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.michal.User;

public class RestClient {

	public static void main(String[] args) throws Exception {
		String wybor = "y";
		Scanner scanner = new Scanner(System.in);

		DefaultHttpClient httpClient = new DefaultHttpClient();

		System.out.println("Enter Login: ");
		String login = scanner.nextLine();
		System.out.println("Enter Password: ");
		String password = scanner.nextLine();
		try {

			HttpGet getRequest = new HttpGet(
					"http://localhost:8080/RestToken/log/" + login + "/"
							+ password);

			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : "
						+ statusCode);
			}

			HttpEntity httpEntity = response.getEntity();
			String apiOutput = EntityUtils.toString(httpEntity);

			System.out.println(apiOutput);

			ObjectMapper objectMapper = new ObjectMapper();

			User user = objectMapper.readValue(apiOutput, User.class);
			System.out.println(user.getLogin());
			System.out.println(user.getPassword());
			System.out.println(user.getToken());

			System.out.println("Sprawdz czy jestes zalogowany?");
			wybor = scanner.nextLine();

			if (wybor.equals("t")) {
				getRequest = new HttpGet(
						"http://localhost:8080/RestToken/log/logIn/"
								+ user.getToken());
				getRequest.addHeader("accept", "application/json");

				response = httpClient.execute(getRequest);

				httpEntity = response.getEntity();
				String apiOutput2 = EntityUtils.toString(httpEntity);
				System.out.println(apiOutput2);

			}

		}

		finally {
			// Important: Close the connect
			httpClient.getConnectionManager().shutdown();

			scanner.close();
		}

	}

}
