package be.vinci.pae.api;

import java.util.List;
import be.vinci.pae.api.utils.ResponseMaker;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.type.TypeDTO;
import be.vinci.pae.domaine.type.TypeUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/types")
public class TypeResource {

  @Inject
  private TypeUCC typeUCC;

  @Inject
  private DomaineFactory domaineFactory;



  /**
   * get a list of the types.
   * 
   * @return list of the types.
   */
  @GET
  public Response getAll() {

    List<TypeDTO> types = typeUCC.getAll();

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("types", types);
  }

}
