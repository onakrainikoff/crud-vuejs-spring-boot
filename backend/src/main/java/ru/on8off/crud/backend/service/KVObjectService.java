package ru.on8off.crud.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.on8off.crud.backend.repository.KVObjectRepository;
import ru.on8off.crud.backend.repository.entity.KVObject;
import ru.on8off.crud.backend.repository.filter.KVObjectFilter;

@Service
public class KVObjectService {
    @Autowired
    private KVObjectRepository kvObjectRepository;

    @Transactional(readOnly = true)
    public Page<KVObject> getAll(KVObjectFilter filter, PageRequest pageRequest){
        return kvObjectRepository.findAll(filter, pageRequest);
    }

    @Transactional(readOnly = true)
    public KVObject get(Integer id){
        return kvObjectRepository.findById(id).orElse(null);
    }

    @Transactional
    public KVObject save(KVObject kvObject){
        return kvObjectRepository.save(kvObject);
    }

    @Transactional
    public void delete(Integer id){
        kvObjectRepository.deleteById(id);
    }
}
