package br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.gateway.ProdutoGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ExcluirProdutoUseCaseImpl implements ExcluirProdutoUseCase {

	private ProdutoGateway produtoRepositoryGateway;
	
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
