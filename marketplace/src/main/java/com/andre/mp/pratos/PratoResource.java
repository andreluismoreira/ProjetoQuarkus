package com.andre.mp.pratos;

import com.andre.mp.pratos.Prato;
import com.andre.mp.pratos.PratoDTO;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {

    @Inject
    PgPool pgPool;

    @GET
    public Multi<PratoDTO> buscarPratos(){
        return Prato.findAll(pgPool);
    }
}
