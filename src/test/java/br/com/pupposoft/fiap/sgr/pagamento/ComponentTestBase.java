package br.com.pupposoft.fiap.sgr.pagamento;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.repository.ClienteEntityRepository;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.repository.ProdutoEntityRepository;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.repository.PagamentoEntityRepository;
import br.com.pupposoft.fiap.sgr.config.database.pedido.repository.ItemEntityRepository;
import br.com.pupposoft.fiap.sgr.config.database.pedido.repository.PedidoEntityRepository;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.web.PagamentoApiController;

public abstract class ComponentTestBase {

	@Autowired
	protected PagamentoEntityRepository pagamentoEntityRepository;
	
	@Autowired
	protected ClienteEntityRepository clienteEntityRepository;
	
	@Autowired
	protected ProdutoEntityRepository produtoEntityRepository;
	
	@Autowired
	protected PedidoEntityRepository pedidoEntityRepository;
	
	@Autowired
	protected ItemEntityRepository itemEntityRepository;
	
	@Autowired
	protected PagamentoApiController pagamentoApiController;

	protected void cleanAllDatabase() {
		//TODO: implementar
	}
	
}
