package be.vinci.pae;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.DomaineFactoryImpl;
import be.vinci.pae.domaine.user.UserUCC;
import be.vinci.pae.domaine.user.UserUCCImpl;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.DalServicesImpl;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MockApplicationBinder extends AbstractBinder {

  private UserDAO userDAO = Mockito.mock(UserDAO.class);
  private DalServices dalServices = Mockito.mock(DalServices.class);

  @Override
  protected void configure() {
    bind(DomaineFactoryImpl.class).to(DomaineFactory.class).in(Singleton.class);
    bind(userDAO).to(UserDAO.class);
    bind(dalServices).to(DalServices.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(DalServicesImpl.class).to(DalBackendServices.class).in(Singleton.class);
  }

}
