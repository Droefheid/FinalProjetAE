package be.vinci.pae.services;

import be.vinci.pae.domaine.FurnitureDTO;

public interface FurnitureDAO {

	FurnitureDTO findById(int id);

	FurnitureDTO getAll();
	
	FurnitureDTO add(FurnitureDTO furniture);
	
}
