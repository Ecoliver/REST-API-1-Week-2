package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.entity.PetStore;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	@Autowired
	private PetStoreService storeService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStore createPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating store: {}", petStoreData);
		try {
			return storeService.createPetStore(petStoreData);
		} catch (Exception e) {
			log.error("Error occurred while creating pet store: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create pet store", e);
		}
	}

	@PutMapping("/{storeId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData updatePetStore(@PathVariable Long storeId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(storeId);
		log.info("Updating store {}", petStoreData);
		return storeService.savePetStore(petStoreData);

	}

	@GetMapping("/{storeId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long storeId) {
		log.info("Retrieving pet store {}", storeId);
		return storeService.retrievePetStoreById(storeId);
	}
}
