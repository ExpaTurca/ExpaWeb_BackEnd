/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/
package com.expastudios.blogweb.Util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@RequiredArgsConstructor
public class EntityDtoConversion {

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final String DtoPath = "com.expastudios.blogweb.entity.DTOs";

    private static final String EntityPath = "com.expastudios.blogweb.entity";

    public static <T> Object ConvertToEntity(T dto)
            throws
            ClassNotFoundException {

        /* DTO to Entity Mapping */
        return modelMapper.map(dto, Class.forName(GetEntityPath(dto)));
    }

    public static <T> Object ConvertToDTO(T entity)
            throws ClassNotFoundException {

        /* Entity To DTO Mapping */
        return modelMapper.map(entity, Class.forName(GetDtoPath(entity)));
    }

    private static int LastIndex(Object entity) {
		
		return entity
		         .getClass ( )
		         .getName ( )
		         .lastIndexOf ( '.' );
	}
	
	public static <T> Set < T > ConvertListToDto ( Set<T> SetEntity ) {

        return SetEntity
                .stream()
                .peek(map -> {
                    try {
                        modelMapper.map(map, Class.forName(GetDtoPath(map)));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }
	
	public static < T > Set < T > ConvertListToEntity ( Set < T > SetDto ) {
		
		return SetDto
		         .stream ( )
		         .peek ( map -> {
			         try {
                         modelMapper.map(map, Class.forName(GetEntityPath(map)));
			         } catch ( ClassNotFoundException e ) {
				         throw new RuntimeException ( e );
			         }
		         } )
		         .collect ( Collectors.toSet ( ) );
	}
	
	private static <T> String GetEntityPath(T dto) {
		
		return EntityPath + dto
		                      .getClass ( )
		                      .getName ( )
		                      .substring ( LastIndex ( dto ) )
		                      .replace ( "DTO", "" );
	}
	
	private static < T > String GetDtoPath ( T entity ) {
		
		return DtoPath + entity
		                   .getClass ( )
		                   .getName ( )
		                   .substring ( LastIndex ( entity ) ) + "DTO";
	}
	/* Capitalize First Character of given String */
	
	/*String capitalize ( String field ) {
		
		return field
		         .substring ( 0, 1 )
		         .toUpperCase ( new Locale ( "en" ) ) + field.substring ( 1 );
	}*/
}
