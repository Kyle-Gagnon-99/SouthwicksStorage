/**
 * 
 */
package com.southwicksstorage.southwicksstorage.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.repositories.StorageItemDao;
import com.southwicksstorage.southwicksstorage.services.StorageItemService;
import com.southwicksstorage.southwicksstorage.services.VendorService;
import com.southwicksstorage.southwicksstorage.unit.BaseUnitTest;
import com.southwicksstorage.southwicksstorage.unit.configuration.UnitTestConstants;

/**
 * @author Kyle Gagnon
 * Created On: 2021-06-15
 *
 */
public class StorageItemServiceTests extends BaseUnitTest {
	
	@InjectMocks
	private StorageItemService storageItemService;
	
	@MockBean
	private StorageItemDao repo;
	
	@MockBean
	private VendorService vendorService;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	VendorEntity syscoVendor = UnitTestConstants.VENDOR_SYSCO_OBJECT;
	StorageItemEntity friesStorageItem = UnitTestConstants.STORAGE_ITEM_FRIES_OBJECT;
	
	Optional<StorageItemEntity> friesOptionalStorageItem = Optional.of(friesStorageItem);
	
	/**
	 * 
	 * Method Being Tested: findById 
	 * Given: A valid id
	 * Result: {@code StorageItemEntity}
	 * Info: When a valid id is given then return the corresponding storage item
	 */
	@Test
	public void givenAValidIdThenReturnStorageItemEntityForFindById() {
		int validId = 1;
		Mockito.when(repo.findById(validId)).thenReturn(friesOptionalStorageItem);
		
		StorageItemEntity resultObject = storageItemService.findById(validId);
		
		assertThat(resultObject).isNotEqualTo(null);
		assertThat(resultObject).isEqualTo(friesStorageItem);
		
	}
	
	@Test
	public void givenAnInvalidIdThenReturnNullForFindById() {
		int invalidId = 100;
		Mockito.when(repo.findById(invalidId)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findById(invalidId);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAValidNameAndVendorIdTheReturnStorageItemEntityForFindByNameAndVendorId() {
		int validVendorId = 5;
		Mockito.when(vendorService.findById(validVendorId)).thenReturn(syscoVendor);
		Mockito.when(repo.findByNameAndVendor(friesStorageItem.getName(), syscoVendor)).thenReturn(friesOptionalStorageItem);
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendorId(friesStorageItem.getName(), validVendorId);
		
		assertThat(resultObject).isEqualTo(friesStorageItem);
	}
	
	@Test
	public void givenAValidNameAndInvalidVendorIdThenReturnNullForFindByNameAndVendorId() {
		int invalidVendorId = UnitTestConstants.INVALID_ID;
		Mockito.when(vendorService.findById(invalidVendorId)).thenReturn(null);
		Mockito.when(repo.findByNameAndVendor(friesStorageItem.getName(), null)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendorId(friesStorageItem.getName(), invalidVendorId);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAnInvalidNameAndAValidVendorIdThenReturnNullForFindByNameAndVendorId() {
		int validId = 5;
		Mockito.when(vendorService.findById(validId)).thenReturn(syscoVendor);
		Mockito.when(repo.findByNameAndVendor(UnitTestConstants.INVALID_STRING, syscoVendor)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendorId(UnitTestConstants.INVALID_STRING, validId);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAnInvalidNameAndVendorIdThenReturnNullForFindByNameAndVendorId() {
		int invalidId = UnitTestConstants.INVALID_ID;
		String invalidName = UnitTestConstants.INVALID_STRING;
		Mockito.when(vendorService.findById(invalidId)).thenReturn(null);
		Mockito.when(repo.findByNameAndVendor(invalidName, null)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendorId(invalidName, invalidId);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAValidNameAndVendorNameThenReturnStorageItemEntityForFindByNameAndVendorName() {
		String validVendor = syscoVendor.getVendorName();
		Mockito.when(vendorService.findByVendorName(validVendor)).thenReturn(syscoVendor);
		Mockito.when(repo.findByNameAndVendor(friesStorageItem.getName(), syscoVendor)).thenReturn(friesOptionalStorageItem);
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendorName(friesStorageItem.getName(), syscoVendor.getVendorName());
		
		assertThat(resultObject).isEqualTo(friesStorageItem);
	}
	
	@Test
	public void givenAValidNameAndInvalidVendorNameThenReturnNullForFindByNameAndVendorName() {
		String invalidVendor = UnitTestConstants.INVALID_STRING;
		Mockito.when(vendorService.findByVendorName(invalidVendor)).thenReturn(null);
		Mockito.when(repo.findByNameAndVendor(friesStorageItem.getName(), null)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendorName(friesStorageItem.getName(), invalidVendor);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAnInvalidNameAndValidVendorNameThenReturnNullForFindByNameAndVendorName() {
		String invalidName = UnitTestConstants.INVALID_STRING;
		Mockito.when(vendorService.findByVendorName(syscoVendor.getVendorName())).thenReturn(syscoVendor);
		Mockito.when(repo.findByNameAndVendor(invalidName, syscoVendor)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendorName(invalidName, syscoVendor.getVendorName());
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAnInvalidNameAndVendorNameThenReturnNullForFindByNameAndVendorName() {
		String invalidName = UnitTestConstants.INVALID_STRING;
		Mockito.when(vendorService.findByVendorName(invalidName)).thenReturn(null);
		Mockito.when(repo.findByNameAndVendor(invalidName, null)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendorName(invalidName, invalidName);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAValidNameAndVendorThenReturnStorageItemEntityForFindByNameAndVendor() {
		Mockito.when(repo.findByNameAndVendor(friesStorageItem.getName(), syscoVendor)).thenReturn(friesOptionalStorageItem);
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendor(friesStorageItem.getName(), syscoVendor);
		
		assertThat(resultObject).isEqualTo(friesStorageItem);
	}
	
	@Test
	public void givenAnInvalidNameAndValidVendorThenReturnNullForFindByNameAndVendor() {
		String invalidName = UnitTestConstants.INVALID_STRING;
		Mockito.when(repo.findByNameAndVendor(invalidName, syscoVendor)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendor(invalidName, syscoVendor);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAValidNameAndInvalidVendorThenReturnNullForFindByNameAndVendor() {
		Mockito.when(repo.findByNameAndVendor(friesStorageItem.getName(), null)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendor(friesStorageItem.getName(), null);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAnInvalidNameAndVendorThenReturnNullForFindByNameAndVendor() {
		String invalidName = UnitTestConstants.INVALID_STRING;
		VendorEntity invalidVendor = null;
		Mockito.when(repo.findByNameAndVendor(invalidName, invalidVendor)).thenReturn(Optional.empty());
		
		StorageItemEntity resultObject = storageItemService.findByNameAndVendor(invalidName, invalidVendor);
		
		assertThat(resultObject).isEqualTo(null);
	}
	
	@Test
	public void givenAValidAndInvalidStorageItemEntityThenSaveIsCalled() {
		// Valid item
		storageItemService.save(friesStorageItem);
		Mockito.verify(repo, times(1)).saveAndFlush(friesStorageItem);
		
		// Invalid item
		storageItemService.save(null);
		Mockito.verify(repo, times(1)).saveAndFlush(null);
	}
	
	@Test
	public void givenAValidAndINvalidStorageItemEntityThenDeleteIsCalled() {
		// Valid item
		storageItemService.delete(friesStorageItem);
		Mockito.verify(repo, times(1)).delete(friesStorageItem);
		
		// Invalid item
		storageItemService.delete(null);
		Mockito.verify(repo, times(1)).delete(null);
	}

}
