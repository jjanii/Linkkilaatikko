/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Logi;

/**
 *
 * @author Jani
 */
public interface LogiRepository extends JpaRepository<Logi, Long> {

    public List<Logi> findAllByOrderByIdDesc();

}
