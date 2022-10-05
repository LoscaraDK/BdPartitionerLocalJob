package br.com.estudo.contasbancariasjob.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import br.com.estudo.contasbancariasjob.dominio.Cliente;

@Configuration
public class ContasBancariasClienteReaderConfig {
	
//	@Bean
//	public JdbcCursorItemReader<Cliente> contasBancariasClienteReader(@Qualifier("businessDatasource") DataSource dataSource){
//		return new JdbcCursorItemReaderBuilder<Cliente>()
//					.name("contasBancariasClienteReader")
//					.dataSource(dataSource)
//					.sql("select * from cliente")
//					.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
//					.build();
//	}
	
	@Bean
	public JdbcPagingItemReader<Cliente> contasBancariasClienteReader(@Qualifier("businessDatasource") DataSource dataSource,
			PagingQueryProvider queryProvider){
		return new JdbcPagingItemReaderBuilder<Cliente>()
					.name("contasBancariasClienteReader")
					.dataSource(dataSource)
					.queryProvider(queryProvider)
					.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
					.build();
	}
	
	@Bean
	public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("businessDatasource") DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("select *");
		queryProvider.setFromClause("from cliente");
		queryProvider.setSortKey("email");
		return queryProvider;
	}
}
