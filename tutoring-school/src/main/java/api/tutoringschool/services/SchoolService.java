package api.tutoringschool.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.tutoringschool.dtos.school.SchoolDTO;
import api.tutoringschool.model.School;
import api.tutoringschool.repositories.SchoolRepository;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository repository;

    public School createSchool(SchoolDTO schoolDTO) {
        School newSchool = new School();
        BeanUtils.copyProperties(schoolDTO, newSchool);
        return repository.save(newSchool);
    }

    public List<School> getAllSchools() {
        return repository.findAll();
    }

    public Optional<School> getSchoolById(UUID id) {
        return repository.findById(id);
    }

    public School updateSchool(UUID id, SchoolDTO schoolData) throws Exception {
        Optional<School> foundedSchool = repository.findById(id);
        if (foundedSchool.isEmpty())
            throw new Exception("School not found.");

        School updatedSchool = foundedSchool.get();
        BeanUtils.copyProperties(schoolData, updatedSchool);

        return repository.save(updatedSchool);
    }

    public void deleteSchool(UUID id) throws Exception {
        Optional<School> foundedSchool = repository.findById(id);

        if (foundedSchool.isEmpty())
            throw new Exception("School not found.");

        repository.delete(foundedSchool.get());
    }
}
