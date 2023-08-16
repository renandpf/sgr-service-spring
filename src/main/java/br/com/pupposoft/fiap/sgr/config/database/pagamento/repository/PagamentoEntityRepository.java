package br.com.pupposoft.fiap.sgr.config.database.pagamento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;

@Repository
public interface PagamentoEntityRepository extends JpaRepository<PagamentoEntity, Long>{

	Optional<PagamentoEntity> findByIdentificadorPagamentoExterno(String identificadorPagamento);

}
