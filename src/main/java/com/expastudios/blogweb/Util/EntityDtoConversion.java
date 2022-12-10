/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.Util;

import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;



@Getter
@Setter
@RequiredArgsConstructor
public class EntityDtoConversion {
	
	@Autowired private static final ModelMapper modelMapper = new ModelMapper ( );
	
	private static final String DtoPath = "com.expastudios.blogweb.model";
	
	private static final String EntityPath = "com.expastudios.blogweb.entity";
	
	public static < T > Object ConvertToEntity ( T dto )
	throws
	ClassNotFoundException {
		
		String EntityClass = EntityPath + dto
		                                    .getClass ( )
		                                    .getName ( )
		                                    .substring ( LastIndex ( dto ) )
		                                    .replace ( "DTO", "" );
		
		/* DTO to Entity Mapping */
		return modelMapper.map ( dto, Class.forName ( EntityClass ) );
	}
	
	public static < T > Object ConvertToDTO ( T entity )
	throws
	ClassNotFoundException {
		
		String DtoClass = DtoPath + entity
		                              .getClass ( )
		                              .getName ( )
		                              .substring ( LastIndex ( entity ) ) + "DTO";
		System.out.println ( "dto: " + DtoClass );
		
		/* Entity To DTO Mapping */
		return modelMapper.map ( entity, Class.forName ( DtoClass ) );
	}
	
	private static int LastIndex ( Object entity ) {
		
		return entity
		         .getClass ( )
		         .getName ( )
		         .lastIndexOf ( '.' );
	}
	
	
	/* Capitalize First Character of given String */
	
	/*String capitalize ( String field ) {
		
		return field
		         .substring ( 0, 1 )
		         .toUpperCase ( new Locale ( "en" ) ) + field.substring ( 1 );
	}*/
}
