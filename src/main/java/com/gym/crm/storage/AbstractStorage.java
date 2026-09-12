package com.gym.crm.storage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractStorage<T> {
    protected final Map<Long, T> storage = new ConcurrentHashMap<>();
    protected final AtomicLong idSequence = new AtomicLong(1);
    public Long generateId(){
        return idSequence.getAndIncrement();
    }
    public T findById(Long id){
        return storage.get(id);
    }
    public List<T> findAll(){
        return List.copyOf(storage.values());
    }
    public void put(Long id, T entity){
        storage.put(id, entity);
        idSequence.updateAndGet(current -> Math.max(current, id + 1));
    }
    public Map<Long, T> getStorage(){
        return storage;
    }

}
