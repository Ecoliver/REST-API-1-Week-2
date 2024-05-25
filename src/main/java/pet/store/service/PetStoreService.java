package pet.store.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {

		PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId(), petStoreData);
		setPetStoreFields(petStoreData, petStore);

		petStore = petStoreDao.save(petStore);
		return new PetStoreData(petStore);

	}

	public PetStore findOrCreatePetStore(Long petStoreId, PetStoreData petStoreData) {
		if (petStoreId == null) {
			return new PetStore();
		}
		return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("PetStore not found"));

	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + "does not exist."));
	}

	private void setPetStoreFields(PetStoreData petStoreData, PetStore petStore) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long storeId) {
		PetStore petStore = findPetStoreById(storeId);
		return new PetStoreData(petStore);
	}

	public PetStore createPetStore(PetStoreData petStoreData) {

		if (petStoreData == null) {
			throw new IllegalArgumentException("Pet store data cannot be null");
		}

		PetStore petStore = new PetStore();
		setPetStoreFields(petStoreData, petStore);
		petStoreDao.save(petStore);

		return petStore;
	}

}
