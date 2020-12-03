package guru.springframework.service;

import guru.springframework.model.Notes;

import java.util.Optional;

public interface NotesService {
    Notes save(Notes notes);
    Optional<Notes> findById(Long notesID);
}
