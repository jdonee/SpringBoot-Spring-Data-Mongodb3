/**
 * 
 */
package org.jdonee.cooking.web;

import javax.servlet.http.HttpServletRequest;

import org.jdonee.cooking.entity.Customer;
import org.jdonee.cooking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;

/**
 * @author Jdonee
 *
 */
@Controller
@Scope("prototype")
public class IndexController {

	@Autowired
	private CustomerService customerService;

	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	
	@RequestMapping(value = "/clientes")
	public @ResponseBody DatatablesResponse<Customer> data(HttpServletRequest request){
		DatatablesCriterias criterias=DatatablesCriterias.getFromRequest(request);
		DataSet<Customer> dataset=customerService.findPageByDatatablesCriterias(criterias);
	    return DatatablesResponse.build(dataset,criterias);
	}

}
