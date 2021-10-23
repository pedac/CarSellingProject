
package com.carApp.service;

import java.util.List;
import java.util.Optional;

import com.carApp.entity.Vehicle;

public interface vehicleService {
	
	public Vehicle addVehicle(Vehicle vehicle);
	
	public List<Vehicle> getAllVehicle(); 
	
	public void deleteVehicle(long vehicleId);
	
	public Optional<Vehicle> getSingleVehicle(long vehicleId, long userId);
	
	public Vehicle updateVehicle(long advertiseId); 
	
	public Optional<Vehicle> getVehicleByBrands(String brand);

     public Optional<Vehicle> getVehicleByfuel(String fuel);
	
	public Optional<Vehicle> getVehicleByPassingYear(int passingYear);
	
	
	public List<Vehicle> getVehicleByUserId(long userId);
	
	public Vehicle getVehicle(long advertiseId);

	public List<Vehicle> getVehicleByStatus(int i);

	public List<Vehicle> getVehicleByBrand(String brand);

	public List<Vehicle> getVehicleByFuelType(String fuelType); 
	
	
	

}
