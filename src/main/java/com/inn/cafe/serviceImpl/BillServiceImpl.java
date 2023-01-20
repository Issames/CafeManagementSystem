package com.inn.cafe.serviceImpl;

import java.io.FileOutputStream;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Bill;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.BillDao;
import com.inn.cafe.service.BillService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BillServiceImpl implements BillService{
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	BillDao billDao;

	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		log.info("Inside");
		try {
			String fileName;
			if(validateRequestMap(requestMap)) {
				if(requestMap.containsKey("isGenerate") && !(Boolean)requestMap.get("isGenerat")) {
					fileName = (String) requestMap.get("uuid");
				}else{
					fileName = CafeUtils.getUUID();
					requestMap.put("uuid", fileName);
					insertBill(requestMap);
				}
				
				String data = "Name : " + requestMap.get("name") + "\n"+
							 "Contact Number :" + requestMap.get("contactNumber") + "\n" +
							 "Email :"  + requestMap.get("email") + "\n" + 
							 " Payment Method :" + requestMap.get("paymentMethod");
				Document document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION + "//" + fileName + ".pdf"));
				document.open();
				setRectangleInPdf(document);
				
				Paragraph chunk = new Paragraph("Cafe Management System", getFont("Header"));
				
			}
				return CafeUtils.getResponseEntity("Required Data not found", HttpStatus.BAD_REQUEST);

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Font getFont(String type) {
		log.info("Inside getFont");
		switch(type) {
		case "Header" : 
			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 0, BaseColor.BLACK);
			headerFont.setStyle(Font.BOLD);
			return headerFont;
		case "Data":
			Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11 , BaseColor.BLACK);
			dataFont.setStyle(Font.BOLD);
			return dataFont;
	
		default:
			return new Font();
		}
}

	private void setRectangleInPdf(Document document)throws DocumentException {
		log.info("Inside setRectangleInPdf");
		Rectangle rect = new Rectangle(577,825,18,15);
		rect.enableBorderSide(1);
		rect.enableBorderSide(2);
		rect.enableBorderSide(4);
		rect.enableBorderSide(8);
		
		rect.setBackgroundColor(BaseColor.BLACK);
		document.add(rect);
		
	}

	private void insertBill(Map<String, Object> requestMap) {
		try {
			Bill bill = new Bill();
			bill.setUuid((String) requestMap.get("uuid"));
			bill.setName((String) requestMap.get("name"));
			bill.setEmail((String) requestMap.get("email"));
			bill.setContactNumber((String) requestMap.get("contactNumber"));
			bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
			bill.setTotal(Integer.parseInt((String)requestMap.get("totalAmount")));
			bill.setProductDetail((String) requestMap.get("productDetails"));
			bill.setCreatedBy(jwtFilter.getCurrentUser());
			billDao.save(bill);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean validateRequestMap(Map<String, Object> requestMap) {
		return requestMap.containsKey("name") && 
				requestMap.containsKey("contactNumber") &&
				requestMap.containsKey("email") &&
				requestMap.containsKey("paymentMethod") &&
				requestMap.containsKey("productDetails") &&
				requestMap.containsKey("totalAmount");
	}

}
