package com.apress.progwt.client.json;

/**
 * Empty Interface. Classes that implement this interface should be sure
 * to add serialization hooks in JSONSerializer.java
 * 
 * We could make this interface have serialize() & deserialize() methods,
 * but if these objects go to the server they will explode because the
 * JSON classes are not in gwt-server.jar
 * 
 * @author Jeff Dwyer
 * 
 */
public interface JSONSerializable {

}
