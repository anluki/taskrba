package com.rba.cc.services;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rba.cc.DTO.OsobaRequest;
import com.rba.cc.Util.Constants;
import com.rba.cc.csvMapper.CSVMapper;
import com.rba.cc.fileManager.FileManager;
import com.rba.cc.logger.CreditCardLogger;
import com.rba.cc.models.Osoba;
import com.rba.cc.repository.OsobaRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class OsobaService {
	
	@Autowired
	OsobaRepository osobaRepository;
		
	@Autowired
	CreditCardLogger creditCardLogger;


	public ServiceResultResponse getOsobaByOib(String oib) {
		creditCardLogger.logInfo("START getOsobaByOib=" + oib, getClass());		
		ServiceResultResponse response = new ServiceResultResponse();
		
		try {
			Osoba osoba = new Osoba();
			
			osoba = osobaRepository.getOsobaByOib(oib);
			
			if(osoba.getOib().isEmpty()) {
				creditCardLogger.logWarn("getOsobaByOib no data found for OIB = " + oib, getClass());
			}
			
			response.setResult(getMapOsoba(osoba));
			response.setSuccessTrue();
			creditCardLogger.logInfo("getOsobaByOib response = " + response.toString(), getClass());
			
		} catch (Exception e) {
			response.setMessage("getOsobaByOib error=" + e.getMessage());
			response.setSuccessFalse();
			creditCardLogger.logError("getOsobaByOib error=" + e.toString(), getClass());
		}
		creditCardLogger.logInfo("END getOsobaByOib=" + oib, getClass());
		return response;
	}


	public ServiceResultResponse saveOsoba(OsobaRequest osobaRequest) {
		ServiceResultResponse response = new ServiceResultResponse();		
		creditCardLogger.logInfo("START saveOsoba=" + osobaRequest.toString(), getClass());
		Osoba osoba = new Osoba();
		try {
			
			if(osobaRequest.getId() != null) {
				osoba.setId(osobaRequest.getId());
			} else {
				osoba.setId(osobaRepository.nextSeq());
			}
							
			osoba.setIme(osobaRequest.getIme());
			osoba.setPrezime(osobaRequest.getPrezime());
			osoba.setOib(osobaRequest.getOib());
			osoba.setStatus(osobaRequest.getStatus());
			osobaRepository.save(osoba);
			creditCardLogger.logInfo("saveOsoba osoba = " + osoba.toString(), getClass());
			
			response.setSuccessTrue();
			response.setResult(getMapOsoba(osoba));
			
		} catch (Exception e) {
			creditCardLogger.logError("saveOsoba error=" + e.toString(), getClass());
			response.setSuccessFalse();
			response.setMessage("saveOsoba error = " + e.getMessage().toString());
		}
		creditCardLogger.logInfo("END saveOsoba=" + osoba.toString(), getClass());
		return response;
	}


	public ServiceResultResponse generateCard(String oib) {
		ServiceResultResponse response = new ServiceResultResponse();
		try {
			Osoba osoba = new Osoba();
			osoba = osobaRepository.getOsobaForCardByOib(oib, Constants.NEMA_KARTICU);
			if (osoba != null) {
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				String fileName = osoba.getOib() + "_" + timestamp.toString() + "." + Constants.FILE_EXTENSION_CSV;
				String dirName = osoba.getOib();
				String content = CSVMapper.convertToCSV(osoba, true);
				
				FileManager fileManager = new FileManager(fileName, dirName, content, creditCardLogger);
				fileManager.saveFile();
				
				osoba.setStatus(Constants.IMA_KARTICU);
				osobaRepository.save(osoba);
				
				response.setSuccessTrue();
				response.setResult(getMapOsoba(osoba));
				
			} else {
				response.setSuccessTrue();
				response.setMessage("osoba već ima karticu ili je u izradi");
			}
			
			
		} catch (Exception e) {
			creditCardLogger.logError("generateCard error = " + e.toString(), getClass());
			response.setSuccessFalse();
			response.setMessage("generateCard error = " + e.getMessage().toString());
		}		
		return response;
	}

	
	public ServiceResultResponse deactivateCard(String oib) {
		ServiceResultResponse response = new ServiceResultResponse();
		try {
			Osoba osoba = new Osoba();
			osoba = osobaRepository.getOsobaForCardByOib(oib, Constants.IMA_KARTICU);
			if (osoba != null) {
								
				String dirName = osoba.getOib();
				
				FileManager fileManager = new FileManager(dirName, creditCardLogger);
				fileManager.setActiveFileToInactive();
				
				osoba.setStatus(Constants.NEMA_KARTICU);
				osobaRepository.save(osoba);
				
				response.setSuccessTrue();
				response.setResult(getMapOsoba(osoba));
				
			} else {
				response.setSuccessTrue();
				response.setMessage("osoba s oibom: " + oib + " ne postoji ili nema ugovorenu karticu");
			}
			
			
		} catch (Exception e) {
			creditCardLogger.logError("generateCard error = " + e.toString(), getClass());
			response.setSuccessFalse();
			response.setMessage("generateCard error = " + e.getMessage().toString());
		}		
		return response;
	}
	
	
	private Map<String, Osoba> getMapOsoba (Osoba o) {		
		Map<String, Osoba> map = new HashMap<String, Osoba>();
		map.put(Constants.OSOBA, o);
		return map;
	}




}
