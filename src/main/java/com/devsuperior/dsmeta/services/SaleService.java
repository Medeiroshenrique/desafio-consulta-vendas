package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly=true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<SaleSummaryMinDTO> getSaleSummary(String minDateStr, String maxDateStr){
		LocalDate minDate = parseStringToLocalDate(minDateStr);
		LocalDate maxDate = parseStringToLocalDate(maxDateStr);

		LocalDate[] dates = getMinMaxDates(minDate, maxDate);

		return repository.searchSalesSummary(dates[0], dates[1]);
	}


	@Transactional(readOnly = true)
	public Page<SaleReportMinDTO> getSalesReport(
			String minDateStr, String maxDateStr,
			String name, Pageable pageable)
	{
		LocalDate minDate = parseStringToLocalDate(minDateStr);
		LocalDate maxDate = parseStringToLocalDate(maxDateStr);

		LocalDate[] dates = getMinMaxDates(minDate, maxDate);

		if(name == null){
			name = "";
		}

		System.out.println("sellerName = [" + name + "]");
		return repository.searchSalesReport(dates[0], dates[1], name, pageable);
	}

	private LocalDate parseStringToLocalDate(String date) {
		return (date != null) ? LocalDate.parse(date) : null;
		//if and only if the String is NOT null, returns it as LocalDate.
	}


	private LocalDate[] getMinMaxDates(LocalDate minDate, LocalDate maxDate){
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		if(maxDate == null){
			maxDate= today;
		}

		if(minDate == null){
			minDate = today.minusYears(1L);
		}

		return new LocalDate[]{minDate, maxDate};
	}



}
