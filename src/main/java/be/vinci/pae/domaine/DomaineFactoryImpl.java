package be.vinci.pae.domaine;

public class DomaineFactoryImpl implements DomaineFactory {

	@Override
	public UserDTO getUserDTO() {
		return new UserImpl();
	}

	@Override
	public Adress getAdress() {
		return new AdressImpl();
	}

	@Override
	public FurnitureDTO getFurnitureDTO() {
		return new FurnitureImpl();
	}

}
