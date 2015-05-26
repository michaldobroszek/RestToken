package com.michal;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/log")
@Produces("application/json")
public interface IService {

	@GET
	@Path("/{login}/{password}")
	public User getLogin(@PathParam("login") String login,
			@PathParam("password") String password);

	@GET
	@Path("/logIn/{token}")
	public String writeSomething(@PathParam("token") UUID token);

}