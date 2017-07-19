package com.sjony.base;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.sjony.support.ServiceNumberSupport;

/**
 * Created by sjony on 2017/7/15.
 */
public class BaseController {

    public String success(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setSno(ServiceNumberSupport.getServceNum());
        result.setSuccess("1");
        SerializeWriter sw = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(sw);
        serializer.write(result);
        return sw.toString();
    }


    public String error(String error) {
        Result result = new Result();
        result.setData(null);
        result.setSno(ServiceNumberSupport.getServceNum());
        result.setSuccess("1");
        result.setMsg(error);
        SerializeWriter sw = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(sw);
        serializer.write(result);
        return sw.toString();
    }

}
