/**
 * 
 */
package org.jdonee.cooking.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jdonee
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Customer {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	
	@JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分", timezone = "GMT+08:00")
	private Date created;
	
	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s',created='%s']", id, firstName, lastName,created);
	}

}
