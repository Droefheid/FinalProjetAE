package be.vinci.pae.utils;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import be.vinci.pae.domaine.AdressFactory;
import be.vinci.pae.domaine.AdressFactoryImpl;
import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.domaine.UserFactoryImpl;
import be.vinci.pae.services.DataServiceAdressCollection;
import be.vinci.pae.services.DataServiceAdressCollectionImpl;
import be.vinci.pae.services.DataServiceUserCollection;
import be.vinci.pae.services.DataServiceUserCollectionImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(UserFactoryImpl.class).to(UserFactory.class).in(Singleton.class);
    bind(DataServiceUserCollectionImpl.class).to(DataServiceUserCollection.class)
        .in(Singleton.class);
    bind(AdressFactoryImpl.class).to(AdressFactory.class).in(Singleton.class);
    bind(DataServiceAdressCollectionImpl.class).to(DataServiceAdressCollection.class)
        .in(Singleton.class);
  }

}
