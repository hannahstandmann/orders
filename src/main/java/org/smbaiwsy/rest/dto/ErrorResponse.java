package org.smbaiwsy.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

//import de.jcim.fdk.vo.ErrorCode;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error response object returned in case of controller exceptions
 * Class contains error code and message(description).
 * 
 * @author anamattuzzi-stojanovic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(value = Include.NON_EMPTY)
//@ApiModel(description = "Model that represents error response from REST server")
public class ErrorResponse {

	//@ApiModelProperty(value = "Status code to be returned in case of exceptions", required = true, dataType = "String", example = "SERVER_ERROR", position = 0)
    private String code;
    
	//@ApiModelProperty(value = "Description to be returned in case of exceptions", required = true, dataType = "String", example = "java.lang.RuntimeException occured on line 39", position = 1)
	private String message;
}
