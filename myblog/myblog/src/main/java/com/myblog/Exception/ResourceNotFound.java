package com.myblog.Exception;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String msg)
    {
      super(msg);
    }
}
