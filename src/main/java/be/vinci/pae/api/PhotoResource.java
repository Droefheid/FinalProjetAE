package be.vinci.pae.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.ContainerRequest;
import com.fasterxml.jackson.databind.JsonNode;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.filters.AuthorizeBoss;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.api.utils.ResponseMaker;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.domaine.photo.PhotoDTO;
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;
import be.vinci.pae.domaine.photo.PhotoFurnitureUCC;
import be.vinci.pae.domaine.photo.PhotoUCC;
import be.vinci.pae.domaine.photo.PhotoVisitDTO;
import be.vinci.pae.domaine.visit.VisitDTO;
import be.vinci.pae.domaine.visit.VisitUCC;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/photos")
public class PhotoResource {

  @Inject
  private VisitUCC visitUCC;

  @Inject
  private FurnitureUCC furnitureUCC;

  @Inject
  private PhotoUCC photoUCC;

  @Inject
  private PhotoFurnitureUCC photoFurnitureUCC;

  @Inject
  private DomaineFactory domaineFactory;


  /**
   * save in a folder the photo given for a furniture. Must be Authorize.
   * 
   * @param file the photo to save.
   * @param fileDisposition information about the photo.
   * @return Response.ok if everything is going fine.
   */
  @POST
  @Path("/uploadPhotoFurniture")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Authorize
  public Response uploadOnePhotoFurniture(@Context ContainerRequest request,
      @FormDataParam("photo0") InputStream file,
      @FormDataParam("photo0") FormDataContentDisposition fileDisposition) {
    // Check about the furniture to link.
    int furnitureId = Integer.valueOf(request.getHeaderString("furnitureId"));
    if (furnitureId < 1) {
      throw new PresentationException("Furniture id cannot be under 1", Status.BAD_REQUEST);
    }
    FurnitureDTO furniture = this.furnitureUCC.findById(furnitureId);
    if (furniture == null) {
      throw new PresentationException("Furniture doesn't exist", Status.BAD_REQUEST);
    }

    // System.out.println("InputStream: "+file+"\nFormDataContentDisposition: "+fileDisposition);

    String uploadedFileLocation = Config.getProperty("PhotosPath") + fileDisposition.getFileName();
    // System.out.println("URL : " + System.getProperty("user.dir") + uploadedFileLocation);

    // save it.
    writeToFile(file, System.getProperty("user.dir") + uploadedFileLocation);

    // For the return.
    List<String> paths = new ArrayList<>();
    List<PhotoDTO> photos = new ArrayList<>();
    List<PhotoFurnitureDTO> photosFurniture = new ArrayList<>();
    photos.add(createPhotoDTOWith(uploadedFileLocation, fileDisposition.getFileName()));
    photosFurniture.add(createFullFillPhotoFurniture(-1, furniture.getFurnitureId(), false, false));
    List<PhotoDTO> allPhotos = this.photoUCC.addMultiplePhotosForFurniture(photos, photosFurniture);
    for (PhotoDTO photoDTO : allPhotos) {
      paths.add(encodeFileToBase64Binary(photoDTO.getPicture()));
    }

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("photos", paths);
  }

  /**
   * save all the photos from FormDataMultipart given for a furniture. Must be Authorize.
   * 
   * @param request header with the token.
   * @param multiPart the FormDataMultipart with photo inside.
   * @return Response.ok if everything is going fine.
   */
  @POST
  @Path("/uploadPhotosFurniture")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Authorize
  public Response uploadMultiplePhotosFurniture(@Context ContainerRequest request,
      final FormDataMultiPart multiPart) {
    // Check about the furniture to link.
    int furnitureId = Integer.valueOf(request.getHeaderString("furnitureId"));
    if (furnitureId < 1) {
      throw new PresentationException("Furniture id cannot be under 1", Status.BAD_REQUEST);
    }
    FurnitureDTO furniture = this.furnitureUCC.findById(furnitureId);
    if (furniture == null) {
      throw new PresentationException("Furniture doesn't exist", Status.BAD_REQUEST);
    }

    // Save all photo include.
    Map<String, List<FormDataBodyPart>> fields = multiPart.getFields();
    List<String> paths = new ArrayList<>();
    List<PhotoDTO> photos = new ArrayList<>();
    List<PhotoFurnitureDTO> photosFurniture = new ArrayList<>();
    for (String keyField : fields.keySet()) {
      List<FormDataBodyPart> values = fields.get(keyField);
      for (FormDataBodyPart formDataBodyPart : values) {
        // System.out.println(keyField + " == " + formDataBodyPart.getName());
        // System.out.println(formDataBodyPart.getHeaders());
        // for (String formDataBodyPart2 : formDataBodyPart.getHeaders().keySet()) {
        // System.out.println(formDataBodyPart2);
        // System.out.println(formDataBodyPart.getHeaders().get(formDataBodyPart2));
        // }
        // System.out.println(formDataBodyPart.getHeaders().get("Content-Disposition"));
        String fileName = getFilenameOfImageFrom(formDataBodyPart);
        // System.out.println("Name : " + fileName);
        String uploadedFileLocation = Config.getProperty("PhotosPath") + fileName;
        // System.out.println("URL : " + System.getProperty("user.dir") + uploadedFileLocation);

        // Save it.
        writeToFile(formDataBodyPart.getValueAs(InputStream.class),
            System.getProperty("user.dir") + uploadedFileLocation);
        photos.add(createPhotoDTOWith(uploadedFileLocation, fileName));
        photosFurniture
            .add(createFullFillPhotoFurniture(-1, furniture.getFurnitureId(), false, false));

        // Test for return.
        // paths.add(encodeFileToBase64Binary(uploadedFileLocation));
      }
    }

    List<PhotoDTO> allPhotos = this.photoUCC.addMultiplePhotosForFurniture(photos, photosFurniture);
    for (PhotoDTO photoDTO : allPhotos) {
      paths.add(encodeFileToBase64Binary(photoDTO.getPicture()));
    }

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("photos", paths);
  }

  /**
   * save all the photo from FormDataMultipart given for a visit. Must be Authorize.
   * 
   * @param request header with the token.
   * @param multiPart the FormDataMultipart with photo inside.
   * @return Response.ok if everything is going fine.
   */
  @POST
  @Path("/uploadPhotosVisit")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Authorize
  public Response uploadMultiplePhotosVisit(@Context ContainerRequest request,
      final FormDataMultiPart multiPart) {
    // Check about the furniture to link.
    int visitId = Integer.valueOf(request.getHeaderString("visitId"));
    if (visitId < 1) {
      throw new PresentationException("Visit id cannot be under 1", Status.BAD_REQUEST);
    }
    VisitDTO visit = this.visitUCC.getVisit(visitId);
    if (visit == null) {
      throw new PresentationException("Visit doesn't exist", Status.BAD_REQUEST);
    }

    // Save all photo include.
    Map<String, List<FormDataBodyPart>> fields = multiPart.getFields();
    List<String> paths = new ArrayList<>();
    List<PhotoDTO> photos = new ArrayList<>();
    List<PhotoVisitDTO> photosVisit = new ArrayList<>();
    for (String keyField : fields.keySet()) {
      List<FormDataBodyPart> values = fields.get(keyField);
      for (FormDataBodyPart formDataBodyPart : values) {
        String fileName = getFilenameOfImageFrom(formDataBodyPart);
        String uploadedFileLocation = Config.getProperty("PhotosPath") + fileName;

        // Save it.
        writeToFile(formDataBodyPart.getValueAs(InputStream.class),
            System.getProperty("user.dir") + uploadedFileLocation);
        photos.add(createPhotoDTOWith(uploadedFileLocation, fileName));
        photosVisit.add(createAPhotoVisitWith(visit.getId()));
      }
    }

    List<PhotoDTO> allPhotos =
        this.photoUCC.addMultiplePhotosForVisit(photos, photosVisit, visit.getId());
    for (PhotoDTO photoDTO : allPhotos) {
      paths.add(encodeFileToBase64Binary(photoDTO.getPicture()));
    }

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("photos", paths);
  }

  /**
   * delete the photo and the photo_furniture with the id given.
   * 
   * @param request header with the token and the furniture id attached to the photo.
   * @param id the id of the photo.
   * @return Response.ok if delete is did with the delete photo.
   */
  @DELETE
  @Path("/{id}")
  @AuthorizeBoss
  public Response delete(@Context ContainerRequest request, @PathParam("id") int id) {
    // Check about the furniture linked.
    int furnitureId = Integer.valueOf(request.getHeaderString("furnitureId"));
    if (furnitureId < 1) {
      throw new PresentationException("Furniture id cannot be under 1", Status.BAD_REQUEST);
    }
    FurnitureDTO furniture = this.furnitureUCC.findById(furnitureId);
    if (furniture == null) {
      throw new PresentationException("Furniture doesn't exist", Status.BAD_REQUEST);
    }

    // Check about the photoId.
    if (id < 1) {
      throw new PresentationException("Photo id cannot be under 1", Status.BAD_REQUEST);
    }
    PhotoDTO photoDTO = this.photoUCC.findById(id);
    if (photoDTO == null) {
      throw new PresentationException("Photo doesn't exist", Status.BAD_REQUEST);
    }
    PhotoFurnitureDTO photoFurnitureDTO = this.photoFurnitureUCC.findById(id);
    if (photoFurnitureDTO == null) {
      throw new PresentationException("Photo Furniture doesn't exist", Status.BAD_REQUEST);
    }

    this.photoUCC.delete(id);

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("photo", photoDTO);
  }

  /**
   * update the photo_furniture with the value of favorite.
   * 
   * @param json object containing all necessary information about the photo_furniture.
   * @return a response.ok with the updated photo_furniture.
   */
  @PUT
  @Path("favorite")
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeBoss
  public Response updateFavorite(JsonNode json) {
    if (json.get("photoId") == null || json.get("photoId").asText().equals("")) {
      throw new PresentationException("Photo id needed", Status.BAD_REQUEST);
    }
    if (json.get("furnitureId") == null || json.get("furnitureId").asText().equals("")) {
      throw new PresentationException("Furniture id needed", Status.BAD_REQUEST);
    }
    if (json.get("isFavorite") == null || json.get("isFavorite").asText().equals("")) {
      throw new PresentationException("Is favourite needed", Status.BAD_REQUEST);
    }

    // Loop for unFavourite all photo of this furniture if new favorite.
    int furnitureId = json.get("furnitureId").asInt();
    boolean isFavorite = json.get("isFavorite").asBoolean();
    if (isFavorite) {
      this.photoFurnitureUCC.removeFavouriteFormFurniture(furnitureId);
    }


    int photoId = json.get("photoId").asInt();
    PhotoFurnitureDTO photoFurniture =
        createFullFillPhotoFurniture(photoId, furnitureId, false, isFavorite);
    photoFurniture = photoFurnitureUCC.updateFavorite(photoFurniture);
    if (photoFurniture == null) {
      throw new PresentationException("Photo_furniture doesn't update", Status.BAD_REQUEST);
    }

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("photoFurniture", photoFurniture);
  }

  /**
   * update the photo_furniture with the value of visibility.
   * 
   * @param json object containing all necessary information about the photo_furniture.
   * @return a response.ok with the updated photo_furniture.
   */
  @PUT
  @Path("visible")
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeBoss
  public Response updateVisibility(JsonNode json) {
    if (json.get("photoId") == null || json.get("photoId").asText().equals("")) {
      throw new PresentationException("Photo id needed", Status.BAD_REQUEST);
    }
    if (json.get("furnitureId") == null || json.get("furnitureId").asText().equals("")) {
      throw new PresentationException("Furniture id needed", Status.BAD_REQUEST);
    }

    if (json.get("isVisible") == null || json.get("isVisible").asText().equals("")) {
      throw new PresentationException("Is visible needed", Status.BAD_REQUEST);
    }

    int photoId = json.get("photoId").asInt();
    int furnitureId = json.get("furnitureId").asInt();
    boolean isVisible = json.get("isVisible").asBoolean();
    PhotoFurnitureDTO photoFurniture =
        createFullFillPhotoFurniture(photoId, furnitureId, isVisible, false);
    photoFurniture = photoFurnitureUCC.updateVisibility(photoFurniture);
    if (photoFurniture == null) {
      throw new PresentationException("Photo_furniture doesn't update", Status.BAD_REQUEST);
    }

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("photoFurniture", photoFurniture);
  }



  // ******************** Public static's Methods ********************

  /**
   * Return a Image into a Base64 at the location given.
   * 
   * @param uploadedFileLocation the path to locate the access file.
   * @return a Base64 Image.
   */
  public static String encodeFileToBase64Binary(String uploadedFileLocation) {
    File file = new File(System.getProperty("user.dir") + uploadedFileLocation);

    String encodedfile = null;
    try (FileInputStream fileInputStreamReader = new FileInputStream(file)) {
      byte[] bytes = new byte[(int) file.length()];
      fileInputStreamReader.read(bytes);
      encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
    } catch (FileNotFoundException e) {
      throw new PresentationException("File Not Found.", e, Status.BAD_REQUEST);
    } catch (IOException e) {
      throw new PresentationException("IO Exception.", e, Status.BAD_REQUEST);
    }

    return "data:image/png;base64," + encodedfile;
  }

  /**
   * Transform the url (into Picture) from all the pictures into a Base64 Image.
   * 
   * @param photosList the list who contains all the photo to transform.
   */
  public static void transformAllURLOfThePhotosIntoBase64Image(List<PhotoDTO> photosList) {
    for (PhotoDTO photo : photosList) {
      if (photo != null && photo.getPicture().startsWith("/src")) {
        transformTheURLOfThePhotoIntoBase64Image(photo);
      }
    }
  }

  public static void transformTheURLOfThePhotoIntoBase64Image(PhotoDTO photo) {
    String encodstring = encodeFileToBase64Binary(photo.getPicture());
    photo.setPicture(encodstring);
  }



  // ******************** Private's Methods ********************

  private PhotoDTO createPhotoDTOWith(String picture, String name) {
    PhotoDTO photo = domaineFactory.getPhotoDTO();

    photo.setPicture(picture);
    photo.setName(name);

    return photo;
  }

  private PhotoFurnitureDTO createFullFillPhotoFurniture(int photoId, int furnitureId,
      boolean isVisible, boolean isFavourite) {
    PhotoFurnitureDTO photoFurniture = domaineFactory.getPhotoFurnitureDTO();

    photoFurniture.setPhotoId(photoId);
    photoFurniture.setFurnitureId(furnitureId);
    photoFurniture.setVisible(isVisible);
    photoFurniture.setFavourite(isFavourite);

    return photoFurniture;
  }

  private PhotoVisitDTO createAPhotoVisitWith(int visitId) {
    PhotoVisitDTO photoVisit = domaineFactory.getPhotoVisitDTO();

    photoVisit.setVisitId(visitId);

    return photoVisit;
  }

  /**
   * return the filename of a image from a FormDataBodyPart.
   * 
   * @param formDataBodyPart contains the image.
   * @return the filename.
   */
  private String getFilenameOfImageFrom(FormDataBodyPart formDataBodyPart) {
    String filename = formDataBodyPart.getHeaders().get("Content-Disposition").get(0).split(";")[2];
    return filename.substring(11, filename.length() - 1);
  }

  /**
   * Save uploaded file to the new location.
   * 
   * @param uploadedInputStream the uploaded file.
   * @param uploadedFileLocation the new location.
   */
  private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
    try (OutputStream out = new FileOutputStream(new File(uploadedFileLocation))) {
      int read = 0;
      byte[] bytes = new byte[1024];

      while ((read = uploadedInputStream.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      out.flush();
      out.close(); // Puisque dans try-with-ressource, est ce encore nessesaire?
    } catch (IOException e) {
      throw new PresentationException("IO Exception.", e, Status.BAD_REQUEST);
    }
  }

}
