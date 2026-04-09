package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public Page<SaleReportMinDTO> getReport(
			@RequestParam(required = false) String minDate,
			@RequestParam(required = false) String maxDate,
			@RequestParam(required = false) String sellerName,
			Pageable pageable)
	{
		return service.getSalesReport(minDate, maxDate, sellerName, pageable);
	}

	@GetMapping(value = "/summary")
	public List<SaleSummaryMinDTO> getSummary(
			@RequestParam(required = false) String minDate,
			@RequestParam(required = false) String maxDate)
	{
		return service.getSaleSummary(minDate, maxDate);
	}
}
