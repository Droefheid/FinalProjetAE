package be.vinci.pae.utils;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.DomaineFactoryImpl;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.domaine.furniture.FurnitureUCCImpl;
import be.vinci.pae.domaine.option.OptionUCC;
import be.vinci.pae.domaine.option.OptionUCCImpl;
import be.vinci.pae.domaine.user.UserUCC;
import be.vinci.pae.domaine.user.UserUCCImpl;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.DalServicesImpl;
import be.vinci.pae.services.FurnitureDAO;
import be.vinci.pae.services.FurnitureDAOImpl;
import be.vinci.pae.services.OptionDAO;
import be.vinci.pae.services.OptionDAOImpl;
import be.vinci.pae.services.TypeDAO;
import be.vinci.pae.services.TypeDAOImpl;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.services.UserDAOImpl;
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
    bind(FurnitureUCCImpl.class).to(FurnitureUCC.class).in(Singleton.class);
    bind(FurnitureDAOImpl.class).to(FurnitureDAO.class).in(Singleton.class);
    bind(TypeDAOImpl.class).to(TypeDAO.class).in(Singleton.class);
  }

}
