package com.rba.cc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rba.cc.DTO.OsobaRequest;
import com.rba.cc.logger.CreditCardLogger;
import com.rba.cc.services.OsobaService;
import com.rba.cc.services.ServiceResultResponse;

@RestController
@RequestMapping("/osoba")
public class OsobaController {

	@Autowired
	OsobaService osobaService;
	
	@Autowired
	CreditCardLogger creditCardLogger;
	
	@GetMapping("/get-osoba-by-oib/{oib}")
	ServiceResultResponse getOsobaByOib(@PathVariable String oib) {
		
		creditCardLogger.logInfo("getOsobaByOib=" + oib, getClass());
		return osobaService.getOsobaByOib(oib);		
	}
		
	
	@PostMapping("/add-osoba")
    public ServiceResultResponse addOsoba(@RequestBody OsobaRequest osoba) {
		creditCardLogger.logInfo("add-osoba = " + osoba.toString(), getClass());
        return osobaService.saveOsoba(osoba);
    }
	
	@PostMapping("/generate-card")
    public ServiceResultResponse generateCard(@RequestParam(name = "oib")  String oib) {
		creditCardLogger.logInfo("generate-card = " + oib, getClass());
        return osobaService.generateCard(oib);
    }
	
	@PostMapping("/deactivate-card")
    public ServiceResultResponse deactivateCard(@RequestParam(name = "oib")  String oib) {
		creditCardLogger.logInfo("deactivate-card = " + oib, getClass());
        return osobaService.deactivateCard(oib);
    }
	
	
}
