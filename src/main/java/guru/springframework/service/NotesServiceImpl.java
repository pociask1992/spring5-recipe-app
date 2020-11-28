package guru.springframework.service;

import guru.springframework.model.Notes;
import guru.springframework.repository.NotesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;

    public NotesServiceImpl(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public Optional<Notes> findById(Long notesID) {
        return notesRepository.findById(notesID);
    }
}
