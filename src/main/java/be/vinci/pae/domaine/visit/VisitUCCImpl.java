package be.vinci.pae.domaine.visit;

import java.util.List;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.PhotoDAO;
import be.vinci.pae.services.PhotoVisitDAO;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.services.VisitDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

public class VisitUCCImpl implements VisitUCC {

  @Inject
  private VisitDAO visitDao;
  @Inject
  private PhotoDAO photoDAO;
  @Inject
  private PhotoVisitDAO photoVisitDAO;
  @Inject
  private UserDAO userDao;
  @Inject
  private DalServices dalservices;



  @Override
  public List<VisitDTO> getAll() {
    dalservices.startTransaction();
    List<VisitDTO> list = visitDao.getAll();
    dalservices.commitTransaction();
    return list;
  }

  @Override
  public List<VisitDTO> getAllNotConfirmed() {
    dalservices.startTransaction();
    List<VisitDTO> list = visitDao.getAllNotConfirmed();
    dalservices.commitTransaction();
    return list;
  }



  @Override
  public VisitDTO introduceVisit(VisitDTO visitDTO, AddressDTO addressDTO, UserDTO userDTO) {
    dalservices.startTransaction();
    int addressId = userDao.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry());
    if (addressId == -1) {
      addressId = userDao.registerAddress(addressDTO);
    }
    VisitDTO visit = visitDTO;
    visit.setAddressId(addressId);

    visit.setUserId(userDTO.getID());

    visit = visitDao.introduceVisit(visit);
    if (visit == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Visit already exists", Status.BAD_REQUEST);
    }
    dalservices.commitTransaction();
    return (VisitDTO) visit;
  }

  @Override
  public VisitDTO getVisit(int id) {
    dalservices.startTransaction();
    VisitDTO visit = visitDao.findById(id);
    if (visit == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Visit doesn't exist", Status.BAD_REQUEST);
    }
    dalservices.commitTransaction();
    return (VisitDTO) visit;
  }

  @Override
  public Object[] getAllInfosOfVisit(int id) {
    dalservices.startTransaction();
    Object[] allLists = new Object[3];
    int i = 0;
    allLists[i++] = visitDao.findById(id);
    if (allLists[0] == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Visit doesn't exist", Status.BAD_REQUEST);
    }
    allLists[i++] = photoDAO.getAllForVisit(id);
    allLists[i++] = photoVisitDAO.getAllForVisit(id);
    dalservices.commitTransaction();
    return allLists;
  }

  @Override
  public void updateConfirmed(VisitDTO visit) {
    dalservices.startTransaction();
    this.visitDao.updateConfirmed(visit);
    dalservices.commitTransaction();

  }

  @Override
  public List<VisitDTO> getAllConfirmed() {
    dalservices.startTransaction();
    List<VisitDTO> list = visitDao.getAllConfirmed();
    dalservices.commitTransaction();
    return list;
  }



}
