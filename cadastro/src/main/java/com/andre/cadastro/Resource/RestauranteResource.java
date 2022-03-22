package com.andre.cadastro.Resource;

import com.andre.cadastro.Exception.ConstraintViolationResponse;
import com.andre.cadastro.Prato.AdicionarPratoDTO;
import com.andre.cadastro.Prato.AtualizarPratoDTO;
import com.andre.cadastro.Prato.Prato;
import com.andre.cadastro.Prato.PratoDTO;
import com.andre.cadastro.Prato.PratoMapper;
import com.andre.cadastro.Restaurante.AdicionarRestauranteDTO;
import com.andre.cadastro.Restaurante.AtualizarRestauranteDTO;
import com.andre.cadastro.Restaurante.Restaurante;
import com.andre.cadastro.Restaurante.RestauranteDTO;
import com.andre.cadastro.Restaurante.RestauranteMapper;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name="Restaurantes")
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl="http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
@SecurityRequirements(value = {@SecurityRequirement(name = "ifood_oauth", scopes = {})})
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @Inject
    PratoMapper pratoMapper;

    @Inject
    @Channel("restaurantes-adicionados")
    Emitter<String> emitter;

    @POST
    @Transactional
    @APIResponse(responseCode = "201", description = " Caso o restaurante seja cadastrado com sucesso")
    @APIResponse(responseCode = "400",content = @Content(schema = @Schema(allOf= ConstraintViolationResponse.class)))
    public Response adicionar(@Valid AdicionarRestauranteDTO dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.persist();

        Jsonb create = JsonbBuilder.create();
        String json = create.toJson(restaurante);
        emitter.send(json);

        return Response.status(Status.CREATED).build();
    }

    @GET
    @Counted(
	name = "Quantidade buscas Restaurante")
    @SimplyTimed(
	name = "Tempo simples de busca")
    @Timed(
	name = "Tempo completo de busca")
    public List<RestauranteDTO> buscar() {
        Stream<Restaurante> restaurantes = Restaurante.streamAll();
        return restaurantes.map(r ->
                restauranteMapper.toRestauranteDTO(r)).collect(Collectors.toList());
    }
    

    
    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar (@PathParam("id") Long id, AtualizarRestauranteDTO dto) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
    	if(restauranteOp.isEmpty()) {
    		throw new NotFoundException();
    	}
    	Restaurante restaurante = restauranteOp.get();
    	restauranteMapper.toRestaurante(dto, restaurante);
    	restaurante.persist();
    }
    
    @DELETE
    @Path("{id}")
    @Transactional
    public void deletar (@PathParam("id") Long id) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        //validar se tem prato cadastrado e avisar que precisa apagar os pratos antes do restaurante
    	restauranteOp.ifPresentOrElse(Restaurante::delete, () ->{
    		throw new NotFoundException();
    	});
    }
    
    //Pratos
    
    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name="Pratos")
    public List<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
       Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
       if(restauranteOp.isEmpty()) {
    	   throw new NotFoundException("Restaurante não existe");
       }
        Stream<Prato> pratos = Prato.stream("restaurante", restauranteOp.get());
        return pratos.map(p -> pratoMapper.toPratoDTO(p)).collect(Collectors.toList());
    }
    
    @POST
    @Path("{idRestaurante}/pratos")
    @Transactional
    @Tag(name="Pratos")
    public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, AdicionarPratoDTO dto) {
    	   Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
           if(restauranteOp.isEmpty()) {
        	   throw new NotFoundException("Restaurante não existe");
           }
    	Prato prato = pratoMapper.toPrato(dto);
        prato.restaurante = restauranteOp.get();
    	prato.persist();
    	return Response.status(Status.CREATED).build();
    }
    
    @PUT
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name="Pratos")
    public void atualizar (@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id,
                           AtualizarPratoDTO dto) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    	if(restauranteOp.isEmpty()) {
    		throw new NotFoundException("Restaurante não existe");
    	}
    	Optional<Prato> pratoOp = Prato.findByIdOptional(id);
    	if(pratoOp.isEmpty()) {
    		throw new NotFoundException("Prato não existe");
    	}
    	Prato prato = pratoOp.get();
    	pratoMapper.toPrato(dto, prato);
        prato.persist();
    }
    
    @DELETE
    @Transactional
    @Path("{idRestaurante}/pratos/{id}")
    @Tag(name="Pratos")
    public void deletar (@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    	if(restauranteOp.isEmpty()) {
    		throw new NotFoundException("Restaurante não existe");
    	}
    	Optional<Prato> pratoOp = Prato.findByIdOptional(id);
    	pratoOp.ifPresentOrElse(Prato::delete, () ->{
    		throw new NotFoundException();
    	});
    }
}