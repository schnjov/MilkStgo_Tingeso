package cl.usach.tingeso.sistemamilkstgo.Repositories;

import cl.usach.tingeso.sistemamilkstgo.Entities.PlanillaPagosEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.QuincenaEntity;
import cl.usach.tingeso.sistemamilkstgo.Services.PlanillaPagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


public interface PlanillaPagosRepository extends JpaRepository<PlanillaPagosEntity, Long> {
    public PlanillaPagosEntity getTopByQuincena(QuincenaEntity quincena);

}
