package br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.ports.ProdutoRepositoryGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExcluirProdutoUseCaseImpl implements ExcluirProdutoUseCase {

	@Autowired
	private ProdutoRepositoryGateway produtoRepositoryGateway;
	
	@Override
	public void excluir(Long id) {
        log.trace("Start id={}", id);
        this.verificaSeProdutoEstaAssociadoItem(id);
        this.produtoRepositoryGateway.excluir(id);
        log.trace("End");
	}

    //TODO: este método deveria chamar o service de Pedido
	private void verificaSeProdutoEstaAssociadoItem(Long id) {
        // const existePedido = await this.produtoRepositoryGateway.existePedidoByProdutoId(id);
        // if (existePedido) {
        //     this.logger.warn("Product is associated with at least 1 order");
        //     throw new ExclusaoProdutoAssociadoPedidoException();
        // }
    }
	
}
