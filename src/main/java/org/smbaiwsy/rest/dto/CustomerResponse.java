package org.smbaiwsy.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

	/**
	 * Unique refId
	 **/

	private String refId;

	/**
	 * Customer email
	 **/
	// @ApiModelProperty(value = "LicenseGroup name.", required = true, dataType =
	// "String", example = "licenseGroup 1", position = 1)
	private String email;

	/**
	 * Customer address
	 **/
	// @ApiModelProperty(value = "number of licenses in group.", required = true,
	// dataType = "Integer", example = "12", position = 2)
	private String address;

}
