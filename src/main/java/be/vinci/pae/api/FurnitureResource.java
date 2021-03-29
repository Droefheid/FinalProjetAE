package be.vinci.pae.api;

import com.fasterxml.jackson.databind.JsonNode;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.FurnitureUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/furnitures")
public class FurnitureResource {


  @Inject
  private FurnitureUCC furnitureUcc;

  @Inject
  private DomaineFactory domaineFactory;

  @GET
  @Path("/allFurniture")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response allFurniture(JsonNode json) {
    // TODO
    return null;
  }
}
