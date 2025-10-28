package com.change.controller;

import com.change.dto.ChangeDTO;
import com.change.remote.ChangeServiceRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/change")
@Produces(MediaType.APPLICATION_JSON)
public class ChangeRest {

	@EJB
	private ChangeServiceRemote changeService;

	@GET
	@Path("/devises")
	public List<String> getDevises() {
		return changeService.getListeDevises();
	}
}