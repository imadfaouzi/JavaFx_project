package ma.emsi.firstfx.logic.dao;

import ma.emsi.firstfx.logic.entities.*;
import java.util.List;

public interface FilmDao {
    Film insert(Film film);

    void update(Film film);

    void deleteById(Integer id);

    Film findById(Integer id);
    List<Film> findAll();

}
