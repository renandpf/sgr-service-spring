package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.repository.ProdutoEntityRepository;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception.ErrorToAccessRepositoryException;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.gateway.ProdutoGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProdutoMySqlRepository implements ProdutoGateway {

	@Autowired
	private ProdutoEntityRepository produtoEntityRepository;

	@Override
	public Optional<ProdutoDto> obterPorId(Long produtoId) {
		try {
			log.trace("Start produtoId={}", produtoId);
			Optional<ProdutoEntity> produtoEntityOp = produtoEntityRepository.findById(produtoId);

			Optional<ProdutoDto> produtoOp = 
					produtoEntityOp.isEmpty() ? 
							Optional.empty(): Optional.of(mapEntityToDto(produtoEntityOp.get()));


			log.trace("End produtoOp={}", produtoOp);
			return produtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}	
	}

	@Override
	public List<ProdutoDto> obterPorCategoria(Categoria categoria) {
        try {
            log.trace("Start categoria={}", categoria);
            List<ProdutoEntity> produtosEntities = produtoEntityRepository.findByCategoriaId(categoria.ordinal());
            List<ProdutoDto> produtosDto = produtosEntities.stream().map(this::mapEntityToDto).toList();
            log.trace("End produtosDto={}", produtosDto);
            return produtosDto;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessRepositoryException();
        }
	}

	@Override
	public Long criar(ProdutoDto produtoDto) {
        try {
            log.trace("Start produtoDto={}", produtoDto);
            ProdutoEntity produtoSavedEntity = produtoEntityRepository.save(mapDtoToEntity(produtoDto));
            Long idProdutoCreated = produtoSavedEntity.getId();
            log.trace("End idProdutoCreated={}", idProdutoCreated);
            return idProdutoCreated;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessRepositoryException();
        }
	}

	@Override
	public void alterar(ProdutoDto produtoDto) {
        try {
            log.trace("Start produtoDto={}", produtoDto);
            produtoEntityRepository.save(mapDtoToEntity(produtoDto));
            log.trace("End");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessRepositoryException();
        }
	}

	@Override
	public void excluir(Long produtoId) {
        try {
            log.trace("Start id={}", produtoId);
            produtoEntityRepository.deleteById(produtoId);
            log.trace("End");
        }
        catch (Exception e) {
            //TODO: este if deve ser removido assim que o usecase de exclusão verificar a associação de pedido x produto
//            if(e.code === 'ER_ROW_IS_REFERENCED_2'){//TODO: verificar qual a exceção de contraint
//                throw new ExclusaoProdutoAssociadoPedidoException();
//            }
            log.error(e.getMessage(), e);
            throw new ErrorToAccessRepositoryException();
        }
	}
	
	private ProdutoDto mapEntityToDto(ProdutoEntity entity) {
		return ProdutoDto.builder()
				.id(entity.getId())
				.nome(entity.getNome())
				.descricao(entity.getDescricao())
				.valor(entity.getValor())
				.categoriaId( entity.getCategoriaId())
				.imagem(entity.getImagem())
				.build();
	}
	
	private ProdutoEntity mapDtoToEntity(ProdutoDto dto) {
		return ProdutoEntity.builder()
				.id(dto.getId())
				.nome(dto.getNome())
				.descricao(dto.getDescricao())
				.valor(dto.getValor())
				.categoriaId( dto.getCategoriaId())
				.imagem(dto.getImagem())
				.itens(new ArrayList<>())
				.build();
	}	
}
