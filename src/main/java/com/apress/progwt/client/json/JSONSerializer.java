package com.apress.progwt.client.json;

import com.apress.progwt.client.domain.ProcessType;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONValue;

/**
 * JSON serializer. No magic here. Classes that implement JSONSerializable
 * must add custom logic here to perform their
 * serialization/deserialization.
 * 
 * @author Jeff Dwyer
 * 
 */
public class JSONSerializer {

    /**
     * 
     * @param <T>
     * @param object
     * @param clazz
     * @return
     * @throws JSONException
     */
    public static <T> T deserialize(JSONValue object, Class<T> clazz)
            throws JSONException {
        JSONWrapper jsw = new JSONWrapper(object);

        if (clazz == ProcessType.class) {

            ProcessType rtn = new ProcessType();

            rtn.setId(jsw.getLong("id"));
            rtn.setName(jsw.getString("name"));
            rtn.setUseByDefault(jsw.getBoolean("useByDefault"));
            rtn.setStatus_order(jsw.getInt("status_order"));
            rtn.setPercentage(jsw.getBoolean("percentage"));
            rtn.setDated(jsw.getBoolean("dated"));
            return (T) rtn;
        }

        throw new UnsupportedOperationException(
                "No deserializer for class " + clazz);
    }

    /**
     * 
     * @param obj
     * @return
     */
    public static String serialize(JSONSerializable obj) {
        JSONWrapper jsonObject = new JSONWrapper();

        if (obj instanceof ProcessType) {
            ProcessType processType = (ProcessType) obj;
            jsonObject.put("id", processType.getId());
            jsonObject.put("name", processType.getName());
            jsonObject.put("useByDefault", processType.isUseByDefault());
            jsonObject.put("status_order", processType.getStatus_order());
            jsonObject.put("percentage", processType.isPercentage());
            jsonObject.put("dated", processType.isDated());
        }

        return jsonObject.getObject().toString();
    }
}
