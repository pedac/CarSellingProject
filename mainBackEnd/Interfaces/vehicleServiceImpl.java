package com.carApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carApp.Dao.vehicleServiceRepository;
import com.carApp.entity.Vehicle;

@Service
public class vehicleServiceImpl implements vehicleService {

	@Autowired
	vehicleServiceRepository vehicleRepo;

	List<Vehicle> list;

	@Override
	public Vehicle addVehicle(Vehicle vehicle) {

		this.vehicleRepo.save(vehicle);

		return this.vehicleRepo.save(vehicle);
	}

	@Override
	public List<Vehicle> getAllVehicle() {
		list = new ArrayList<Vehicle>();
		list = this.vehicleRepo.getVehicleByStatus(0);

		return list;
	}

	@Override
	public Optional<Vehicle> getSingleVehicle(long vehicleId, long userId) {
		Optional<Vehicle> vehicle = this.vehicleRepo.findById(vehicleId, userId);
		return vehicle;
	}

	@Override
	public Vehicle updateVehicle(long advertiseId) {
		Vehicle vehicle = this.vehicleRepo.updatePriceById(advertiseId);
		
		return vehicle;
	}
	
	
	

	@Override
	public void deleteVehicle(long vehicleId) {
		this.vehicleRepo.deleteById(vehicleId);

	}

	// returns list of the Vehicle by options
	@Override
	public Optional<Vehicle> getVehicleByBrands(String brand) {
		Optional<Vehicle> listByBrand = this.vehicleRepo.getVehicleByBrand(brand);
		return listByBrand;
	}

	@Override
	public Optional<Vehicle> getVehicleByfuel(String fuel) {
		Optional<Vehicle> listByfuel = this.vehicleRepo.getVehicleByfuel(fuel);
		return listByfuel;
	}

	@Override
	public Optional<Vehicle> getVehicleByPassingYear(int passingYear) {
		Optional<Vehicle> listBypassingYear = this.vehicleRepo.getVehicleByYear(passingYear);
		return listBypassingYear;
	}

	
	// Vehicle list to show only uploader
	@Override
	public List<Vehicle> getVehicleByUserId(long userId) {
	    List<Vehicle> Vlist=new ArrayList<Vehicle>();
	    Vlist=this.vehicleRepo.getVehicleByuserId(userId);
		return Vlist;
	}

	@Override
	public Vehicle getVehicle(long advertiseId) {
		Vehicle vehicle=this.vehicleRepo.getById(advertiseId);
		return vehicle;
	}

	@Override
	public List<Vehicle> getVehicleByStatus(int i) {
		  List<Vehicle> Vlist=new ArrayList<Vehicle>();
		    Vlist=this.vehicleRepo.getVehicleByStatus(i);
			return Vlist;	
			
	}

	@Override
	public List<Vehicle> getVehicleByBrand(String brand) {
		list = new ArrayList<Vehicle>();
		list = this.vehicleRepo.getVehicleBrand(brand);

		return list;
	}

	@Override
	public List<Vehicle> getVehicleByFuelType(String fuelType) {
		list = new ArrayList<Vehicle>();
		list = this.vehicleRepo.getVehicleFuelType(fuelType);

		return list;
	}

}
