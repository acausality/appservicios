package api.grupo.appservicios.data.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.grupo.appservicios.model.entity.Professional;

@Repository
public interface ProfessionalDAO extends JpaRepository<Professional, Serializable> {

	public abstract Professional findById(long id);

	public abstract boolean existsByIdentityTypeAndIdentityNumber(String identityType, String identityNumber);

	public abstract boolean existsByEmail(String email);

	public abstract Professional findByEmail(String email);

	public abstract List<Professional> findBySignupDate(LocalDate date);
}
