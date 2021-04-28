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
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
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
  private FurnitureUCC furnitureUCC;

  @Inject
  private PhotoUCC photoUCC;

  @Inject
  private PhotoFurnitureUCC photoFurnitureUCC;

  @Inject
  private DomaineFactory domaineFactory;


  /**
   * save in a folder the photo given. Must be Authorize.
   * 
   * @param file the photo to save.
   * @param fileDisposition information about the photo.
   * @return Response.ok if everything is going fine.
   */
  @POST
  @Path("/uploadPhoto")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Authorize
  public Response uploadOnePhoto(@FormDataParam("photo0") InputStream file,
      @FormDataParam("photo0") FormDataContentDisposition fileDisposition) {
    System.out.println("Coucou1");
    System.out.println("InputStream: " + file + "\nFormDataContentDisposition: " + fileDisposition);

    String uploadedFileLocation = "C:\\Ecole Vinci\\projet-ae-groupe-05/"
        + "src/main/resources/photos/" + fileDisposition.getFileName();
    System.out.println(uploadedFileLocation);

    // save it
    writeToFile(file, uploadedFileLocation);


    // Test for return
    String encodstring = encodeFileToBase64Binary(uploadedFileLocation);
    // System.out.println(encodstring.substring(0, 200));

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("furniture", encodstring);
  }

  /**
   * save in a folder all the photo in the FormDataMultipart given. Must be Authorize.
   * 
   * @param request header with the token.
   * @param multiPart the FormDataMultipart with photo inside.
   * @return Response.ok if everything is going fine.
   */
  @POST
  @Path("/uploadPhotos")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Authorize
  public Response uploadMultiplePhotos(@Context ContainerRequest request,
      final FormDataMultiPart multiPart) {
    // Check about the furniture to link.
    int furnitureId = Integer.valueOf(request.getHeaderString("furnitureId"));
    if (furnitureId < 1) {
      throw new PresentationException("Furntirure id cannot be under 1", Status.BAD_REQUEST);
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
        String uploadedFileLocation =
            Config.getProperty("ServerPath") + Config.getProperty("PhotosPath") + fileName;
        // System.out.println("URL : " + uploadedFileLocation);

        // Save it.
        writeToFile(formDataBodyPart.getValueAs(InputStream.class), uploadedFileLocation);
        photos.add(createFullFillPhoto(uploadedFileLocation, fileName));
        photosFurniture.add(createFullFillPhotoFurniture(furniture.getFurnitureId()));

        // Test for return.
        // paths.add(encodeFileToBase64Binary(uploadedFileLocation));
      }
    }

    List<PhotoDTO> allPhotos = this.photoUCC.addMultiple(photos, photosFurniture);
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
  public Response deletePhoto(@Context ContainerRequest request, @PathParam("id") int id) {
    // Check about the furniture linked.
    int furnitureId = Integer.valueOf(request.getHeaderString("furnitureId"));
    if (furnitureId < 1) {
      throw new PresentationException("Furntirure id cannot be under 1", Status.BAD_REQUEST);
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



  /******************** Public static's Methods ********************/

  /**
   * Return a Image into a Base64 at the location given.
   * 
   * @param uploadedFileLocation the path to locate the file.
   * @return a Base64 Image.
   */
  public static String encodeFileToBase64Binary(String uploadedFileLocation) {
    File file = new File(uploadedFileLocation);

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

  private PhotoFurnitureDTO createFullFillPhotoFurniture(int furnitureId) {
    PhotoFurnitureDTO photoFurniture = domaineFactory.getPhotoFurnitureDTO();

    photoFurniture.setVisible(false);
    photoFurniture.setFavourite(false);
    photoFurniture.setFurnitureId(furnitureId);

    return photoFurniture;
  }



  /******************** Private's Methods ********************/

  private PhotoDTO createFullFillPhoto(String picture, String name) {
    PhotoDTO photo = domaineFactory.getPhotoDTO();

    photo.setPicture(picture);
    photo.setName(name);

    return photo;
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
