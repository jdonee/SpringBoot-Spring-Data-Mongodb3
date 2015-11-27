/**
 * 
 */
package org.jdonee.cooking.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdonee.cooking.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.ColumnDef.SortDirection;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jdonee
 *
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private MongoOperations operation;

	@Override
	public DataSet<Customer> findPageByDatatablesCriterias(DatatablesCriterias criterias) {
		log.debug("全部请求参数列表：" + criterias.toString());
		Query query = new Query();
		String search = criterias.getSearch();
		// 搜索过滤
		if (StringUtils.isNotBlank(search)) {
			log.debug("搜索情况：" + criterias.getSearch());
			val criteria = new Criteria();
			criteria.orOperator(Criteria.where("firstName").regex(search), Criteria.where("lastName").regex(search));
			query.addCriteria(criteria);
		}
		// 统计
		long total = operation.count(query, Customer.class);
		log.debug("排序情况：" + criterias.getSortedColumnDefs());
		// 排序
		List<ColumnDef> sorts = criterias.getSortedColumnDefs();
		sorts.stream().filter(s -> s.isSortable()).forEachOrdered(s -> {
			query.with(
					new Sort(s.getSortDirection() == SortDirection.ASC ? Direction.ASC : Direction.DESC, s.getName()));
		});
		// 取值限制
		query.skip(criterias.getStart()).limit(criterias.getLength());
		List<Customer> datas = operation.find(query, Customer.class);
		DataSet<Customer> dataset = new DataSet<Customer>(datas, total, criterias.getLength().longValue());
		log.debug("最后显示结果：" + criterias.toString());
		return dataset;
	}

}
