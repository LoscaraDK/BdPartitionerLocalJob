package br.com.estudo.contasbancariasjob.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.estudo.contasbancariasjob.dominio.Conta;
@Configuration
public class JdbcContasBancariasWriterConfig {

	@StepScope
	@Bean
	public JdbcBatchItemWriter<Conta> jdbcContasBancariasWriter(@Qualifier("businessDatasource") DataSource dataSource){
	
		return new JdbcBatchItemWriterBuilder<Conta>()
				.dataSource(dataSource)
				.sql("INSERT INTO CONTA(tipo,limite,cliente_id) VALUES (?,?,?)")
				.itemPreparedStatementSetter(preparedStatementSetter())
				.build();
		
	}

	private ItemPreparedStatementSetter<Conta> preparedStatementSetter() {
		
		return new ItemPreparedStatementSetter<Conta>() {

			@Override
			public void setValues(Conta conta, PreparedStatement ps) throws SQLException {
				ps.setString(1, conta.getTipo().name());
				ps.setDouble(2, conta.getLimite());
				ps.setString(3, conta.getClienteId());
			}
			
		};
	}
}
