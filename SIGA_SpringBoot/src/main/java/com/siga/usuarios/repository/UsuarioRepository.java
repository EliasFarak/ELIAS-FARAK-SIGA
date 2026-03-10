package com.siga.usuarios.repository;

import com.siga.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Usuario.
 * Spring Data JPA genera automáticamente las operaciones CRUD
 * (save, findAll, findById, delete) sin necesidad de implementarlas manualmente.
 *
 * @author Elías Farak - Grupo N° 5
 * @version 1.1
 * @since Marzo 2026
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Spring Data JPA provee automáticamente:
    // - save(usuario)        → insertar o actualizar
    // - findAll()            → consultar todos
    // - findById(id)         → consultar por ID
    // - deleteById(id)       → eliminar por ID
    // - existsById(id)       → verificar existencia
}
