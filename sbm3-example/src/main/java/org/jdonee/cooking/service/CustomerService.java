/**
 * 
 */
package org.jdonee.cooking.service;

import org.jdonee.cooking.entity.Customer;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

/**
 * @author Jdonee
 *
 */
public interface CustomerService {
	
	/**
	 * 根据DataTable请求参数查找数据
	 * @param criterias
	 * @return
	 */
	 DataSet<Customer> findPageByDatatablesCriterias(DatatablesCriterias criterias);

}
