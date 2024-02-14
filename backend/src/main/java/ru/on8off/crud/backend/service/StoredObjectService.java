package ru.on8off.crud.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.on8off.crud.backend.repository.StoredObjectRepository;
import ru.on8off.crud.backend.repository.entity.StoredObject;
import ru.on8off.crud.backend.repository.filter.StoredObjectFilter;

@Service
public class StoredObjectService {
    @Autowired
    private StoredObjectRepository storedObjectRepository;

    @Transactional(readOnly = true)
    public Page<StoredObject> getAll(StoredObjectFilter filter, PageRequest pageRequest){
        return storedObjectRepository.findAll(filter, pageRequest);
    }

    @Transactional(readOnly = true)
    public StoredObject get(Integer id){
        return storedObjectRepository.findById(id).orElse(null);
    }

    @Transactional
    public StoredObject save(StoredObject storedObject){
        return storedObjectRepository.save(storedObject);
    }

    @Transactional
    public void delete(Integer id){
        storedObjectRepository.deleteById(id);
    }
}
