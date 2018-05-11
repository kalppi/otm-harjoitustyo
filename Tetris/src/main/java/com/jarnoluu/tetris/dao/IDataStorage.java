package com.jarnoluu.tetris.dao;

import java.util.Map;

public interface IDataStorage {
    public boolean saveObject(IDataObject obj);
    public Map<Object, Object> load();
}
