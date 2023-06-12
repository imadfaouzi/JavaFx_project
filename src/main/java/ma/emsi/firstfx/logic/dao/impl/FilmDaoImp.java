package ma.emsi.firstfx.logic.dao.impl;

import ma.emsi.firstfx.logic.dao.FilmDao;
import ma.emsi.firstfx.logic.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDaoImp implements FilmDao {

    private Connection conn = DB.getConnection();

    @Override
    public Film insert(Film film) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO film (titre, category, min_age, max_age, registrationDate) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, film.getTitre());
            ps.setString(2, film.getCategory());
            ps.setInt(3, film.getMin_age());
            ps.setInt(4, film.getMax_age());
            ps.setTimestamp(5, new Timestamp(film.getRegistrationDate().getTime()));

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    film.setId(id);
                }

                rs.close();
            } else {
                System.out.println("No rows affected.");
                return null; // Return null in case of error
            }
        } catch (SQLException e) {
            System.err.println("Error inserting film: " + e.getMessage());
            return null; // Return null in case of error
        } finally {
            DB.closeStatement(ps);
        }

        return film;
    }

    @Override
    public void update(Film film) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "UPDATE film SET titre = ?,category= ?,min_age= ?,max_age= ?,registrationDate= ? WHERE Id = ?");

            ps.setString(1, film.getTitre());
            ps.setString(2, film.getCategory());
            ps.setInt(3, film.getMin_age());
            ps.setInt(4, film.getMax_age());
            ps.setDate(5, new Date(film.getRegistrationDate().getTime()));
            ps.setInt(6, film.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'un film");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM film WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un film");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Film findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT f.*  FROM film f WHERE f.id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Film film = instantiateFilm(rs);

                return film;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème ...");
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Film> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM film ");
            rs = ps.executeQuery();

            List<Film> list = new ArrayList<>();

            while (rs.next()) {
                Film film = instantiateFilm(rs);
                list.add(film);
            }

            return list;
        } catch (SQLException e) {
            System.err.println("problème ...");
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }



    private Film instantiateFilm(ResultSet rs) throws SQLException {
        Film film = new Film();

        film.setId(rs.getInt("Id"));
        film.setTitre(rs.getString("titre"));
        film.setCategory(rs.getString("category"));
        film.setMax_age(rs.getInt("max_age"));
        film.setMin_age(rs.getInt("min_age"));
        film.setRegistrationDate(new java.util.Date(rs.getTimestamp("RegistrationDate").getTime()));

        return film;
    }


}
