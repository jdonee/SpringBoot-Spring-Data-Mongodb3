/**
 * 
 */
package org.jdonee.cooking.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.Id;

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

	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s']", id, firstName, lastName);
	}

}
