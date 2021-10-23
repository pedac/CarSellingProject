package com.carApp.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carApp.entity.Vehicle;

@Repository
public interface vehicleServiceRepository extends JpaRepository<Vehicle,Long>{

	@Query("select  u from Vehicle u where u.advertiseId=:advertiseId And u.userId=:userId")
	Optional<Vehicle> findById(@Param("advertiseId") long  advertiseId, @Param("userId") long userId);
	
	@Query("Select u from Vehicle u  where  u.advertiseId=:advertiseId")
	Vehicle updatePriceById(@Param("advertiseId") long  advertiseId);

// queries for filteration
	@Query("Select u from Vehicle u  where u.brand= :brand")
	Optional<Vehicle> getVehicleByBrand(@Param("brand") String brand);
	
	 @Query("Select u from  Vehicle u  where u.fuelType=:fuelType") 
	 Optional<Vehicle> getVehicleByfuel(@Param("fuelType") String fuelType);
	 
	@Query("Select  u from Vehicle u  where u.passingYear=:passingYear")
	Optional<Vehicle> getVehicleByYear(@Param("passingYear") int  passingYear);
	
	@Query("Select  u from Vehicle u  where u.userId=:userId")
	List<Vehicle> getVehicleByuserId(@Param("userId") long  userId);
	
	@Query("Select  u from Vehicle u  where u.Status=:Status")
	List<Vehicle> getVehicleByStatus(@Param("Status") int Status);

	
	
	@Query("Select  u from Vehicle u  where u.Status=:Status")
	List<Vehicle> getVehicleByStatusSold(@Param("Status") int Status);

	
	@Query("Select u from Vehicle u  where u.brand= :brand")
	List<Vehicle> getVehicleBrand(String brand);
	
	
	@Query("Select u from Vehicle u  where u.fuelType= :fuelType")
	List<Vehicle> getVehicleFuelType(String fuelType);
	
	
	
}
