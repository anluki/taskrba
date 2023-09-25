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
			Map<String, Osoba> map = new HashMap<String, Osoba>();
			Osoba osoba = new Osoba();
			
			osoba = osobaRepository.getOsobaByOib(oib);
			
			if(osoba.getOib().isEmpty()) {
				creditCardLogger.logWarn("getOsobaByOib no data found for OIB = " + oib, getClass());
			}
			
			map.put("osoba", osoba);
			response.setResult(map);
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


	public Osoba saveOsoba(OsobaRequest osobaRequest) {
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
			
		} catch (Exception e) {
			creditCardLogger.logError("saveOsoba error=" + e.toString(), getClass());
		}
		creditCardLogger.logInfo("END saveOsoba=" + osoba.toString(), getClass());
		return osoba;
	}


	public ServiceResultResponse generateCard(String oib) {
		ServiceResultResponse response = new ServiceResultResponse();		
		try {
			Osoba osoba = new Osoba();
			osoba = osobaRepository.getOsobaForCardByOib(oib, Constants.NEMA_KARTICU);
			if (!osoba.equals(null)) {
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				String fileName = osoba.getOib() + "_" + timestamp.toString() + "." + Constants.FILE_EXTENSION_CSV;
				String dirName = osoba.getOib();
				String content = CSVMapper.convertToCSV(osoba, true);
				
				System.out.println(content);
				
				FileManager fileManager = new FileManager(fileName, dirName, content);
				fileManager.saveFile();	
			}
			
			
		} catch (Exception e) {
			creditCardLogger.logError("generateCard error = " + e.toString(), getClass());
		}		
		return response;
	}


	public ServiceResultResponse deleteOsoba(String oib) {

		try {
			osobaRepository.delete(osobaRepository.getOsobaByOib(oib));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

}
