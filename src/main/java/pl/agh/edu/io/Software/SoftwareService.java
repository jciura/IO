package pl.agh.edu.io.Software;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareService {

    private final SoftwareRepository softwareRepository;

    public SoftwareService(SoftwareRepository softwareRepository) {
        this.softwareRepository = softwareRepository;
    }

    public List<Software> getAllSoftware() {
        return softwareRepository.findAll();
    }

    public Software addSoftware(String name) {
        Software software = new Software(name);
        return softwareRepository.save(software);
    }

    public boolean deleteSoftware(int id) {
        if (softwareRepository.existsById(id)) {
            softwareRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
