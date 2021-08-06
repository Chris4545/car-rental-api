package com.cpung.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cpung.beans.Rental;
import com.cpung.data.RentalDAO;


@WebServlet(urlPatterns = "/rentals")
public class RentalServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Rental> rentals = new ArrayList<>();
		try (RentalDAO dao = new RentalDAO()){
			if(req.getParameter("id") != null && req.getParameter("id") != "") {
				rentals.add(dao.getARental(Integer.parseInt(req.getParameter("id"))));
			}else {
				rentals = dao.getAllRentals();
			}

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(rentals);
			resp.getWriter().print(json);
			resp.setContentType("application/json");
			resp.setStatus(200);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		try(RentalDAO dao = new RentalDAO()){
			Rental rental = mapper.readValue(req.getInputStream(), Rental.class);
			rental = dao.addRental(rental);
			String json = mapper.writeValueAsString(rental);
			resp.getWriter().print(json);
			resp.setContentType("application/json");
			resp.setStatus(201);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(RentalDAO dao = new RentalDAO()){
			Integer value = new Integer(req.getReader().readLine());
			dao.removeRental(value); 

				
		}catch(Exception e) {
			e.printStackTrace();
			resp.setStatus(400);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		try(RentalDAO dao = new RentalDAO()) {
			Rental rental = mapper.readValue(req.getInputStream(), Rental.class);
			dao.updateRental(rental);
			String json = mapper.writeValueAsString(rental);
			resp.getWriter().print(json);
			resp.setStatus(201);
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	
}
