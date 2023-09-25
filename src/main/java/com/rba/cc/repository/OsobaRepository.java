package com.rba.cc.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rba.cc.models.Osoba;


@Repository
public interface OsobaRepository extends CrudRepository<Osoba, Integer>  {
	
	@Query(value = "SELECT o.* FROM osoba o WHERE o.oib = :oib", nativeQuery = true)
	Osoba getOsobaByOib(@Param("oib") String oib);

	
	@Query(value = "SELECT (MAX(o.id) + 1)  FROM osoba o", nativeQuery = true)
	Integer nextSeq();


	@Query(value = "SELECT o.* "
			       + "FROM osoba o "
			      + "WHERE o.oib = :oib "
			        + "AND o.status = :status" , nativeQuery = true)
	Osoba getOsobaForCardByOib(@Param("oib") String oib, @Param("status") String status);
	
}
