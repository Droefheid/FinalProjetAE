package be.vinci.pae.utils;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.DomaineFactoryImpl;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.domaine.furniture.FurnitureUCCImpl;
import be.vinci.pae.domaine.option.OptionUCC;
import be.vinci.pae.domaine.option.OptionUCCImpl;
import be.vinci.pae.domaine.photo.PhotoFurnitureUCC;
import be.vinci.pae.domaine.photo.PhotoFurnitureUCCImpl;
import be.vinci.pae.domaine.photo.PhotoUCC;
import be.vinci.pae.domaine.photo.PhotoUCCImpl;
import be.vinci.pae.domaine.type.TypeUCC;
import be.vinci.pae.domaine.type.TypeUCCImpl;
import be.vinci.pae.domaine.user.UserUCC;
import be.vinci.pae.domaine.user.UserUCCImpl;
import be.vinci.pae.domaine.visit.VisitUCC;
import be.vinci.pae.domaine.visit.VisitUCCImpl;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.DalServicesImpl;
import be.vinci.pae.services.FurnitureDAO;
import be.vinci.pae.services.FurnitureDAOImpl;
import be.vinci.pae.services.OptionDAO;
import be.vinci.pae.services.OptionDAOImpl;
import be.vinci.pae.services.PhotoDAO;
import be.vinci.pae.services.PhotoDAOImpl;
import be.vinci.pae.services.PhotoFurnitureDAO;
import be.vinci.pae.services.PhotoFurnitureDAOImpl;
import be.vinci.pae.services.PhotoVisitDAO;
import be.vinci.pae.services.PhotoVisitDAOImpl;
import be.vinci.pae.services.TypeDAO;
import be.vinci.pae.services.TypeDAOImpl;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.services.UserDAOImpl;
import be.vinci.pae.services.VisitDAO;
import be.vinci.pae.services.VisitDAOImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(DomaineFactoryImpl.class).to(DomaineFactory.class).in(Singleton.class);
    bind(DalServicesImpl.class).to(DalBackendServices.class).to(DalServices.class)
        .in(Singleton.class);
    bind(UserDAOImpl.class).to(UserDAO.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(OptionDAOImpl.class).to(OptionDAO.class).in(Singleton.class);
    bind(OptionUCCImpl.class).to(OptionUCC.class).in(Singleton.class);
    bind(FurnitureDAOImpl.class).to(FurnitureDAO.class).in(Singleton.class);
    bind(FurnitureUCCImpl.class).to(FurnitureUCC.class).in(Singleton.class);
    bind(TypeDAOImpl.class).to(TypeDAO.class).in(Singleton.class);
    bind(TypeUCCImpl.class).to(TypeUCC.class).in(Singleton.class);
    bind(VisitDAOImpl.class).to(VisitDAO.class).in(Singleton.class);
    bind(VisitUCCImpl.class).to(VisitUCC.class).in(Singleton.class);
    bind(PhotoDAOImpl.class).to(PhotoDAO.class).in(Singleton.class);
    bind(PhotoUCCImpl.class).to(PhotoUCC.class).in(Singleton.class);
    bind(PhotoFurnitureDAOImpl.class).to(PhotoFurnitureDAO.class).in(Singleton.class);
    bind(PhotoFurnitureUCCImpl.class).to(PhotoFurnitureUCC.class).in(Singleton.class);
    bind(PhotoVisitDAOImpl.class).to(PhotoVisitDAO.class).in(Singleton.class);
  }

}
