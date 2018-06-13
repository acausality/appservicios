package api.grupo.appservicios;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;
import api.grupo.appservicios.model.mapper.ProfessionalMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppServiciosApplicationTests {
	
	@Test
	public void mapstruct() {
		Professional source = new Professional();
		source.setId(1);
		source.setIdentityType("dni");
		source.setIdentityNumber("123456");
		source.setName("nametest");
		source.setSurname("surnametest");
		source.setAddress("addresstest");
		source.setPhoneNumber("123 - 123");
		source.setEmail("test@email.com");
		
		ProfessionalDTO destination = ProfessionalMapper.INSTANCE.professionalToDTO(source);
		
		Assert.assertEquals(source.getId(), destination.getId());
		Assert.assertEquals(source.getIdentityType(), destination.getIdentityType());
		Assert.assertEquals(source.getIdentityNumber(), destination.getIdentityNumber());
		Assert.assertEquals(source.getName(), destination.getName());
		Assert.assertEquals(source.getSurname(), destination.getSurname());
		Assert.assertEquals(source.getAddress(), destination.getAddress());
		Assert.assertEquals(source.getPhoneNumber(), destination.getPhoneNumber());
		Assert.assertEquals(source.getEmail(), destination.getEmail());
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	//testear conexion a base de datos
	public void testDb() {
		
	}
}
